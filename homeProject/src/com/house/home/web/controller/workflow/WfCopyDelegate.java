package com.house.home.web.controller.workflow;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.home.entity.workflow.WfProcCopy;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.entity.workflow.WfProcInstCopy;
import com.house.home.service.workflow.WfProcCopyService;
import com.house.home.service.workflow.WfProcInstCopyService;
import com.house.home.service.workflow.WfProcInstService;

@Component
@Transactional
public class WfCopyDelegate implements Serializable, JavaDelegate {

	private static final long serialVersionUID = 1L;
	@Autowired
	private WfProcInstService wfProcInstService;
	@Autowired
	private WfProcCopyService wfProcCopyService; 
	@Autowired
	private WfProcInstCopyService wfProcInstCopyService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(execution.getProcessInstanceId());
		//新增审批拒绝不抄送
		String status = (String) execution.getVariable("PROC_HI_STATUS");
		if(!"approval".equals(status)){
			return;
		}
		
		List<WfProcCopy> wfProcCopyList = wfProcCopyService.findListByWfProcNo(wfProcInst.getWfProcNo(), execution.getCurrentActivityId());
		
		Date copyDate = new Date();
		
		wfProcCopyService.doSaveCopyByGroup(wfProcInst.getNo(), wfProcInst.getWfProcNo(),
					execution.getCurrentActivityId(), UserContextHolder.getUserContext().getCzybh());
		// 记录抄送人
		for (WfProcCopy wfProcCopy : wfProcCopyList) {
			WfProcInstCopy wfProcInstCopy = new WfProcInstCopy();
			
			wfProcInstCopy.setWfProcInstNo(wfProcInst.getNo());
			wfProcInstCopy.setTaskKey(wfProcCopy.getTaskKey());
			wfProcInstCopy.setCopyCzy(wfProcCopy.getCopyCzy());
			wfProcInstCopy.setCopyDate(copyDate);
			wfProcInstCopy.setRcvDate(null);
			wfProcInstCopy.setRcvStatus("0"); // 0.未读
			wfProcInstCopy.setLastUpdate(new Date());
			wfProcInstCopy.setLastUpdatedBy(UserContextHolder.getUserContext().getCzybh());
			wfProcInstCopy.setExpired("F");
			wfProcInstCopy.setActionLog("ADD");
			wfProcInstCopyService.save(wfProcInstCopy);
		}
		wfProcInstService.updateCopyDate(wfProcInst.getNo(),wfProcInst.getStartUserId());
	}
	
}
