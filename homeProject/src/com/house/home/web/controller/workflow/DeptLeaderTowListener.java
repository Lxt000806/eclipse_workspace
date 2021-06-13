package com.house.home.web.controller.workflow;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.entity.workflow.WfProcTrack;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.workflow.WfProcInstService;

@Component
@Transactional
public class DeptLeaderTowListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WfProcInstService wfProcInstService;
	
	public void notify(DelegateTask delegateTask) {
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(delegateTask.getProcessInstanceId());
		String deptLeaderTow="";
		ExecutionEntity execuEntity = (ExecutionEntity) delegateTask.getExecution();
		String startUserId = execuEntity.getIdentityLinks().get(0).getUserId();
		Map<String, Object> map = execuEntity.getVariables();
		Employee employee = new Employee();
		Czybm czybm = new Czybm();
		String department= ""; 
		if(wfProcInst != null){
			employee = employeeService.get(Employee.class,wfProcInst.getStartUserId());
			if(StringUtils.isNotBlank(wfProcInst.getDepartment())){
				deptLeaderTow = wfProcInstService.getDeptLeaderTow(wfProcInst.getDepartment());
			}else {
				deptLeaderTow = wfProcInstService.getDeptLeaderTow(employee.getDepartment());
			}
		}else{
			if(map.containsKey("department")){
				department = map.get("department").toString();
			}
			czybm = employeeService.get(Czybm.class,startUserId); //申请人用的是操作员编号 
			employee = employeeService.get(Employee.class, czybm.getEmnum());
			if(StringUtils.isNotBlank(department)){
				deptLeaderTow = wfProcInstService.getDeptLeaderTow(department);
			}else {
				deptLeaderTow = wfProcInstService.getDeptLeaderTow(employee.getDepartment());
			}
		}
		
		if(StringUtils.isNotBlank(deptLeaderTow)){
			String czybh = wfProcInstService.getCzybhByEmpNum(deptLeaderTow);
			if(StringUtils.isNotBlank(czybh)){
				taskService.setAssignee(delegateTask.getId(), czybh);
			}
		}else {
			taskService.complete(delegateTask.getId());
			if(wfProcInst != null){
				WfProcTrack wfProcTrack = new WfProcTrack();
				wfProcTrack.setWfProcInstNo(wfProcInst.getNo());
				wfProcTrack.setOperId("");
				wfProcTrack.setActionType("3");
				wfProcTrack.setRemarks("系统自动审批通过");
				wfProcTrack.setFromActivityId(delegateTask.getId());
				wfProcTrack.setFromActivityDescr(delegateTask.getName());
				wfProcTrack.setLastUpdate(DateUtil.addSecond(new Date(), 30));
				wfProcTrack.setLastUpdatedBy("1");
				wfProcTrack.setExpired("F");
				wfProcTrack.setActionLog("ADD");
				employeeService.save(wfProcTrack);
			}
		}
	}
}
