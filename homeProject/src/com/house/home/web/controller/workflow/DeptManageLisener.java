package com.house.home.web.controller.workflow;
import java.util.Date;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.entity.workflow.WfProcTrack;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.workflow.WfProcInstService;


/**
 * 获取部门主管
 */
@Component		
public class DeptManageLisener implements TaskListener {

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
		Map<String, Object> map = execuEntity.getVariables();
		String department ="";
		if(map.containsKey("department")){
			department = map.get("department").toString();
		}
		Czybm czybm = employeeService.get(Czybm.class, startUserId);
		Employee employee = employeeService.get(Employee.class, czybm.getEmnum());
		String leaderCode="";
		if(StringUtils.isNotBlank(department)){
			leaderCode= wfProcInstService.getDeptManager(department);
		}else {
			leaderCode= wfProcInstService.getDeptManager(employee.getDepartment());
		}
		if(StringUtils.isNotBlank(leaderCode)){
			String czybh = wfProcInstService.getCzybhByEmpNum(leaderCode);
			if(StringUtils.isNotBlank(leaderCode)){
				taskService.setAssignee(delegateTask.getId(),czybh);
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
