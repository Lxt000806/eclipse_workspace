package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActActinst信息
 */
@Entity
@Table(name = "ACT_HI_ACTINST")
public class ActActinst extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "PROC_DEF_ID_")
	private String procDefId;
	@Column(name = "PROC_INST_ID_")
	private String procInstId;
	@Column(name = "EXECUTION_ID_")
	private String executionId;
	@Column(name = "ACT_ID_")
	private String actId;
	@Column(name = "TASK_ID_")
	private String taskId;
	@Column(name = "CALL_PROC_INST_ID_")
	private String callProcInstId;
	@Column(name = "ACT_NAME_")
	private String actName;
	@Column(name = "ACT_TYPE_")
	private String actType;
	@Column(name = "ASSIGNEE_")
	private String assignee;
	@Column(name = "START_TIME_")
	private Date startTime;
	@Column(name = "END_TIME_")
	private Date endTime;
	@Column(name = "DURATION_")
	private Double duration;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	public String getProcDefId() {
		return this.procDefId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	public String getProcInstId() {
		return this.procInstId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	public String getExecutionId() {
		return this.executionId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	
	public String getActId() {
		return this.actId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskId() {
		return this.taskId;
	}
	public void setCallProcInstId(String callProcInstId) {
		this.callProcInstId = callProcInstId;
	}
	
	public String getCallProcInstId() {
		return this.callProcInstId;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	
	public String getActName() {
		return this.actName;
	}
	public void setActType(String actType) {
		this.actType = actType;
	}
	
	public String getActType() {
		return this.actType;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
	public String getAssignee() {
		return this.assignee;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	
	public Double getDuration() {
		return this.duration;
	}

}
