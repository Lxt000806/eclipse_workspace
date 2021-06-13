package com.house.home.web.controller.oa;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.home.service.basic.EmployeeService;

@Component
@Transactional
public class getErpCzysProcessor implements TaskListener{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private EmployeeService employeeService;
	
	public void notify(DelegateTask delegateTask) {
		String processInstanceId = delegateTask.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (processInstance!=null){
			List<String> list = employeeService.getErpCzyList();
			delegateTask.setVariable("erpCzys", list);
		}
	}
	
	
	
	
	
}
