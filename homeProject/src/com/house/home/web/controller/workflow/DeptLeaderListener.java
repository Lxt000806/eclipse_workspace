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

/**
 * 获取deptLeaders
 */
@Component
public class DeptLeaderListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WfProcInstService wfProcInstService;
	
	public void notify(DelegateTask delegateTask) {
		
		ExecutionEntity execuEntity = (ExecutionEntity) delegateTask.getExecution();
		String startUserId = execuEntity.getIdentityLinks().get(0).getUserId();
		Map<String, Object> map = execuEntity.getVariables();
		String department ="";
		if(map.containsKey("department")){
			department = map.get("department").toString();
		}
		Czybm czybm = employeeService.get(Czybm.class,startUserId); //申请人用的是操作员编号 
		Employee employee = employeeService.get(Employee.class, czybm.getEmnum());
		employee = employeeService.get(Employee.class, czybm.getEmnum());
		String leaderCode="";
		if(StringUtils.isNotBlank(department)){
			leaderCode= wfProcInstService.getDeptLeader(department);
		} else {
			if(employee != null){
				leaderCode= wfProcInstService.getDeptLeader(employee.getDepartment());
			}
		}
		if(StringUtils.isNotBlank(leaderCode)){
			String czybh = wfProcInstService.getCzybhByEmpNum(leaderCode);
			if(StringUtils.isNotBlank(leaderCode)){
				taskService.setAssignee(delegateTask.getId(),czybh);
			}
		} else {
			taskService.addCandidateGroup(delegateTask.getId(), "Admin");
		}
		
	}

}
