package com.house.home.web.controller.workflow;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.workflow.WfProcInstService;

@Component
public class WfNextOperatorListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WfProcInstService wfProcInstService;
	
	public void notify(DelegateTask delegateTask) {
		ExecutionEntity execuEntity = (ExecutionEntity) delegateTask.getExecution();
		Map<String, Object> map = execuEntity.getVariables();
		String operator= "";
		if(map.get("NextOperator") !=null ){
			operator = map.get("NextOperator").toString();
		}
		if(StringUtils.isNotBlank(operator)){
			taskService.setAssignee(delegateTask.getId(),operator);
		} else {
			taskService.complete(delegateTask.getId());
		}
	}

}
