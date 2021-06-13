package com.house.home.web.controller.oa;

import java.util.Arrays;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.oa.LeaveService;
import com.house.home.service.workflow.ActTaskService;

/**
 * 获取deptLeadersTwo
 */
@Component
@Transactional
public class GetDeptLeadersTwoProcessor implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	LeaveService leaveService;

	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	protected TaskService taskService;
	
	@Autowired
	protected ActTaskService actTaskService;
	
	@Autowired
	private XtcsService xtcsService;
	
	public void notify(DelegateTask delegateTask) {
		String processInstanceId = delegateTask.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (processInstance!=null){
//			Leave leave = leaveService.get(Leave.class, Long.valueOf(processInstance.getBusinessKey()));
//			List<String> list = employeeService.getDeptLeaders(delegateTask.getAssignee());
//			delegateTask.setVariable("boss", list);
			String boss = xtcsService.getQzById("leaderBoss");
			delegateTask.setVariable("boss", Arrays.asList(boss));
		}
	}

}
