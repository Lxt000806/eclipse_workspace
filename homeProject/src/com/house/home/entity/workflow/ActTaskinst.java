package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActTaskinst信息
 */
@Entity
@Table(name = "ACT_HI_TASKINST")
public class ActTaskinst extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "PROC_DEF_ID_")
	private String procDefId;
	@Column(name = "TASK_DEF_KEY_")
	private String taskDefKey;
	@Column(name = "PROC_INST_ID_")
	private String procInstId;
	@Column(name = "EXECUTION_ID_")
	private String executionId;
	@Column(name = "PARENT_TASK_ID_")
	private String parentTaskId;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "DESCRIPTION_")
	private String description;
	@Column(name = "OWNER_")
	private String owner;
	@Column(name = "ASSIGNEE_")
	private String assignee;
	@Column(name = "START_TIME_")
	private Date startTime;
	@Column(name = "CLAIM_TIME_")
	private Date claimTime;
	@Column(name = "END_TIME_")
	private Date endTime;
	@Column(name = "DURATION_")
	private Double duration;
	@Column(name = "DELETE_REASON_")
	private String deleteReason;
	@Column(name = "PRIORITY_")
	private Integer priority;
	@Column(name = "DUE_DATE_")
	private Date dueDate;
	@Column(name = "FORM_KEY_")
	private String formKey;

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
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
	public String getTaskDefKey() {
		return this.taskDefKey;
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
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	
	public String getParentTaskId() {
		return this.parentTaskId;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getOwner() {
		return this.owner;
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
	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}
	
	public Date getClaimTime() {
		return this.claimTime;
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
	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}
	
	public String getDeleteReason() {
		return this.deleteReason;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public Integer getPriority() {
		return this.priority;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Date getDueDate() {
		return this.dueDate;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	
	public String getFormKey() {
		return this.formKey;
	}

}
