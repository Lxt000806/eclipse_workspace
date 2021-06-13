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

import com.house.home.entity.workflow.WfProcInst;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.workflow.WfProcInstService;

@Component
public class WfAdvanceCompleteListener implements TaskListener,ExecutionListener{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WfProcInstService wfProcInstService;
	
	public void notify(DelegateTask delegateTask) {
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(delegateTask.getProcessInstanceId());
		ExecutionEntity execuEntity = (ExecutionEntity) delegateTask.getExecution();
		String startUserId = execuEntity.getIdentityLinks().get(0).getUserId();
		String czybh = "1";
		Map<String, Object> map = execuEntity.getVariables();
		Map<String , Object> dataMap = new HashMap<String, Object>();
		dataMap.put("wfProcInstNo", wfProcInst.getNo());
		dataMap.put("ChgAmount", map.get("Amount"));
		dataMap.put("wfProcNo", wfProcInst.getWfProcNo());
		dataMap.put("confAmount", map.get("ApproveAmount"));
		// 不是审批通过不修改预支单
		if(map.get("PROC_HI_STATUS") == null || !"approval".equals(map.get("PROC_HI_STATUS").toString())){
			return;
		}
		
		if(map.get("PROC_LASTCZY") != null){
			czybh = map.get("PROC_LASTCZY").toString();
		}
		wfProcInstService.doCompExpenseAdvanceTask("", czybh, "", "", "", dataMap);
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
		dataMap.put("confAmount", map.get("ApproveAmount"));

		if(map.get("PROC_HI_STATUS") == null || !"approval".equals(map.get("PROC_HI_STATUS").toString())){
			return;
		}
		
		if(map.get("PROC_LASTCZY") != null){
			czybh = map.get("PROC_LASTCZY").toString();
		}
		
		wfProcInstService.doCompExpenseAdvanceTask("", czybh, "", "", "", dataMap);
	}
}
