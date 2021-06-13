package com.house.home.web.controller.workflow;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.home.entity.workflow.WfProcInst;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.workflow.WfProcInstService;

@Component
@Transactional
public class WfClaimCompleteListener implements TaskListener,ExecutionListener{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WfProcInstService wfProcInstService;

	@Override
	public void notify(DelegateTask delegateTask) {
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(delegateTask.getProcessInstanceId());
		ExecutionEntity execuEntity = (ExecutionEntity) delegateTask.getExecution();
		String startUserId = execuEntity.getIdentityLinks().get(0).getUserId();
		String czybh = "1";
		Map<String, Object> map = execuEntity.getVariables();
		if(map.get("PROC_HI_STATUS") == null || !"approval".equals(map.get("PROC_HI_STATUS").toString())){
			return;
		}
		
		Map<String , Object> dataMap = new HashMap<String, Object>();
		dataMap.put("wfProcInstNo", wfProcInst.getNo());
		dataMap.put("ChgAmount", map.get("Amount"));
		dataMap.put("wfProcNo", wfProcInst.getWfProcNo());
		dataMap.put("confAmount", map.get("DeductionAmount") != null ? map.get("DeductionAmount") : map.get("confAmount"));

		if(map.get("PROC_LASTCZY") != null){
			czybh = map.get("PROC_LASTCZY").toString();
		}
		if(map.get("PROC_HI_STATUS") != null 
				&& "approval".equals(map.get("PROC_HI_STATUS").toString())){
			wfProcInstService.doCompExpenseClaimTask("", czybh, "", "", "", dataMap);
		}
		if("023".equals(wfProcInst.getWfProcNo())){
			wfProcInstService.saveMarketClaimMessage(wfProcInst.getWfProcNo());
		}
	}

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(delegateExecution.getProcessInstanceId());
		String czybh = "1";
		delegateExecution.getVariables();
		Map<String, Object> map = delegateExecution.getVariables();
		if(map.get("PROC_HI_STATUS") == null || !"approval".equals(map.get("PROC_HI_STATUS").toString())){
			return;
		}
		
		Map<String , Object> dataMap = new HashMap<String, Object>();
		dataMap.put("wfProcInstNo", wfProcInst.getNo());
		dataMap.put("ChgAmount", map.get("Amount"));
		dataMap.put("wfProcNo", wfProcInst.getWfProcNo());
		dataMap.put("confAmount", map.get("DeductionAmount") != null?map.get("DeductionAmount") : map.get("confAmount"));

		if(map.get("PROC_LASTCZY") != null){
			czybh = map.get("PROC_LASTCZY").toString();
		}
		if(map.get("PROC_HI_STATUS") != null 
				&& "approval".equals(map.get("PROC_HI_STATUS").toString())){
			wfProcInstService.doCompExpenseClaimTask("", czybh, "", "", "", dataMap);
		}
		if("023".equals(wfProcInst.getWfProcNo())){
			wfProcInstService.saveMarketClaimMessage(wfProcInst.getNo());
		}
	}
	
}
