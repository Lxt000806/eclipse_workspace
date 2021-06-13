package com.house.home.web.controller.workflow;
import java.util.Arrays;
import java.util.List;
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
 * 获取deptLeaders部门领导/工程部经理或签
 */
@Component
public class DeptLeaderPrjLeaderListener implements TaskListener{
	private static final long serialVersionUID = 1L;

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WfProcInstService wfProcInstService;
	@Autowired
	private TaskService taskService;
	
	
	public void notify(DelegateTask delegateTask) {
		
		ExecutionEntity execuEntity = (ExecutionEntity) delegateTask.getExecution();
		String startUserId = execuEntity.getIdentityLinks().get(0).getUserId();
		Map<String, Object> map = execuEntity.getVariables();
		String department = "";
		if(map.containsKey("department")){
			department = map.get("department").toString();
		}
		
		Czybm czybm = employeeService.get(Czybm.class,startUserId); // 申请人用的是操作员编号 
		Employee employee = employeeService.get(Employee.class, czybm.getEmnum());
		employee = employeeService.get(Employee.class, czybm.getEmnum());
		String leaderCode = "";
		
		// 有选择部门取流程表的部门，没有则取员工表的部门
		if(StringUtils.isNotBlank(department)){
			leaderCode = wfProcInstService.getDeptLeader(department);
		}else {
			if(employee != null){
				leaderCode = wfProcInstService.getDeptLeader(employee.getDepartment());
			}
		}
		
		// 获取工程部经理,有可能配置多个
		List<Map<String, Object>> prjLeaderList = wfProcInstService.getAssigneesByGroupId("EngineerManager");
		if (prjLeaderList != null && prjLeaderList.size() > 0 ) {
			
			// 设置执行人
			String[] candidateUsers = new String[prjLeaderList.size()+1];
			for (int i = 0; i < prjLeaderList.size(); i++) {
				candidateUsers[i] = prjLeaderList.get(i).get("Number").toString();
			}
			
			// 如果有部门领导,添加部门领导到执行人
			if(StringUtils.isNotBlank(leaderCode)){
				candidateUsers[prjLeaderList.size()] = leaderCode;
			}
			
			delegateTask.addCandidateUsers(Arrays.asList(candidateUsers));
			
		} else {
			if(StringUtils.isNotBlank(leaderCode)){
				String czybh = wfProcInstService.getCzybhByEmpNum(leaderCode);
				if(StringUtils.isNotBlank(czybh)){
					delegateTask.addCandidateUser(czybh);
				} else {
					delegateTask.addCandidateGroup("Admin");
				}
			} else {
				delegateTask.addCandidateGroup("Admin");
			}
		}
	}
}

