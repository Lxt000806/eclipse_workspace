package com.house.home.web.controller.workflow;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.workflow.WfProcInst;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.workflow.WfProcInstService;

@Component
@Transactional
public class UpdateEmpStatusListener implements Serializable, JavaDelegate {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WfProcInstService wfProcInstService;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		WfProcInst wfProcInst = wfProcInstService.getWfProcInstByActProcInstId(delegateExecution.getProcessInstanceId());
		
		String startUserId = wfProcInst.getStartUserId();
		
		Date applyDate = wfProcInst.getStartTime();
		
		String czybh = "1";
		
		Map<String, Object> map = delegateExecution.getVariables();
		Map<String , Object> dataMap = new HashMap<String, Object>();
		
		String leaveDateStr = map.get("LeaveDate").toString();
		Date leaveDate = DateUtil.parse(leaveDateStr,"yyyy-MM-dd");
		
		dataMap.put("wfProcInstNo", wfProcInst.getNo());
		dataMap.put("wfProcNo", wfProcInst.getWfProcNo());
		
		// 不是审批通过 不做修改员工信息
		if(map.get("PROC_HI_STATUS") == null || !"approval".equals(map.get("PROC_HI_STATUS").toString())){
			return;
		}
		
		if(map.get("PROC_LASTCZY") != null){
			czybh = map.get("PROC_LASTCZY").toString();
		}
		
		Employee employee = new Employee();
		employee = employeeService.get(Employee.class, startUserId);
		
		if(employee != null){
			String remarks = StringUtils.isNotBlank(employee.getRemarks())?employee.getRemarks()+"/n":"";
			employee.setStatus("SUS");
			employee.setLastUpdate(new Date());
			employee.setLastUpdatedBy("1");
			employee.setExpired("T");
			employee.setLeaveDate(leaveDate);
			employee.setM_umState("M");
			// 申请时间 - 离职时间 >2 修改员工备注 ，在员工备注后面 加 "离职时间"离职，"申请时间"提交离职单
			if(leaveDate != null && applyDate != null && DateUtil.dateDiff(leaveDate, applyDate)>=2){
				remarks += leaveDateStr+"离职，"+DateUtil.DateToString(applyDate,"yyyy-MM-dd")+"提交离职单,离职单号："+wfProcInst.getNo();
			}
			employee.setRemarks(remarks);
			
			employeeService.doUpdateEmpStatus(employee);
		}
		
	}
}
