package com.house.home.web.controller.workflow;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.house.home.entity.workflow.WfProcInst;
import com.house.home.service.workflow.WfProcInstService;

/**
 * 获取deptLeaders
 */
@Component
public class ToApplyManListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TaskService taskService;
	@Autowired
	private WfProcInstService wfProcInstService;
	
	public void notify(DelegateTask delegateTask) {
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(delegateTask.getProcessInstanceId());
		String startUserId= wfProcInst.getStartUserId();
		taskService.setAssignee(delegateTask.getId(),startUserId);
	}

}
