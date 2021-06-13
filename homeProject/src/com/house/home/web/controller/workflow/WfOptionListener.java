package com.house.home.web.controller.workflow;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.oa.LeaveService;
import com.house.home.service.workflow.WfProcInstService;

@Component
@Transactional
public class WfOptionListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TaskService taskService;
	@Autowired
	private WfProcInstService wfProcInstService;
	public void notify(DelegateTask delegateTask) {
		String assignee ="";
			ExecutionEntity execuEntity = (ExecutionEntity) delegateTask.getExecution();
			Object value= taskService.getVariable(delegateTask.getId(), "PROC_OPTION_"+execuEntity.getActivityId());
			WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(delegateTask.getProcessInstanceId());
			if(wfProcInst!= null){
				assignee = wfProcInstService.getOptionAssignee(wfProcInst.getNo(),delegateTask.getTaskDefinitionKey());
			}else{
				if(StringUtils.isNotBlank(value.toString())){
					assignee = value.toString();
				}
			}
			if (StringUtils.isNotBlank(assignee)) {
				taskService.setAssignee(delegateTask.getId(), assignee);
			} else{
				taskService.addCandidateGroup(delegateTask.getId(), "Admin");
			}
	}

}
