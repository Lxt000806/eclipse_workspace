package com.house.home.web.controller.oa;

import java.util.Date;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.house.home.entity.oa.Leave;
import com.house.home.service.oa.LeaveService;

/**
 * 调整申请内容处理器
 * 
 * @author HenryYan
 */
@Component
@Transactional
public class AfterModifyApplyContentProcessor implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	LeaveService leaveService;

	@Autowired
	RuntimeService runtimeService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.activiti.engine.delegate.TaskListener#notify(org.activiti.engine.
	 * delegate.DelegateTask)
	 */
	public void notify(DelegateTask delegateTask) {
		String processInstanceId = delegateTask.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		Leave leave = leaveService.get(Leave.class, Long.valueOf(processInstance.getBusinessKey()));

		if ("3".equals(leave.getType())){
			leave.setLeaveType(String.valueOf(delegateTask.getVariable("leaveType")));
			leave.setStartTime((Date) delegateTask.getVariable("startTime"));
			leave.setEndTime((Date) delegateTask.getVariable("endTime"));
			if (delegateTask.getVariable("reason")==null){
				leave.setReason(null);
			}else{
				leave.setReason(String.valueOf(delegateTask.getVariable("reason")));
			}
			if (delegateTask.getVariable("dayNum")==null){
				leave.setDayNum(null);
			}else{
				leave.setDayNum(Double.valueOf(String.valueOf(delegateTask.getVariable("dayNum"))));
			}
		}else if ("1".equals(leave.getType())){
			leave.setStartTime((Date) delegateTask.getVariable("startTime"));
			leave.setEndTime((Date) delegateTask.getVariable("endTime"));
			if (delegateTask.getVariable("reason")==null){
				leave.setReason(null);
			}else{
				leave.setReason(String.valueOf(delegateTask.getVariable("reason")));
			}
		}else if ("2".equals(leave.getType())){
			leave.setStartTime((Date) delegateTask.getVariable("startTime"));
			leave.setEndTime((Date) delegateTask.getVariable("endTime"));
			leave.setRealityStartTime((Date) delegateTask.getVariable("realityStartTime"));
			leave.setRealityEndTime((Date) delegateTask.getVariable("realityEndTime"));
			if (delegateTask.getVariable("reason")==null){
				leave.setReason(null);
			}else{
				leave.setReason(String.valueOf(delegateTask.getVariable("reason")));
			}
			if (delegateTask.getVariable("dayNum")==null){
				leave.setDayNum(null);
			}else{
				leave.setDayNum(Double.valueOf(String.valueOf(delegateTask.getVariable("dayNum"))));
			}
		}

		leaveService.update(leave);
	}

}
