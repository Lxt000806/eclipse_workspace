package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * ActTask信息
 */
@Entity
@Table(name = "ACT_RU_TASK")
public class ActTask extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "EXECUTION_ID_")
	private String executionId;
	@Column(name = "PROC_INST_ID_")
	private String procInstId;
	@Column(name = "PROC_DEF_ID_")
	private String procDefId;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "PARENT_TASK_ID_")
	private String parentTaskId;
	@Column(name = "DESCRIPTION_")
	private String description;
	@Column(name = "TASK_DEF_KEY_")
	private String taskDefKey;
	@Column(name = "OWNER_")
	private String owner;
	@Column(name = "ASSIGNEE_")
	private String assignee;
	@Column(name = "DELEGATION_")
	private String delegation;
	@Column(name = "PRIORITY_")
	private Integer priority;
	@Column(name = "CREATE_TIME_")
	private Date createTime;
	@Column(name = "DUE_DATE_")
	private Date dueDate;
	@Column(name = "SUSPENSION_STATE_")
	private Integer suspensionState;
	
	@Transient
	private String procDefKey;
	@Transient
	private String type;//审批类型
	@Transient
	private String status;//审批状态
	@Transient
	private String userName;
	@Transient
	private String userId;
	@Transient
	private String procName;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	
	public Integer getRev() {
		return this.rev;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	public String getExecutionId() {
		return this.executionId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	public String getProcInstId() {
		return this.procInstId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	public String getProcDefId() {
		return this.procDefId;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	
	public String getParentTaskId() {
		return this.parentTaskId;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
	public String getTaskDefKey() {
		return this.taskDefKey;
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
	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}
	
	public String getDelegation() {
		return this.delegation;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public Integer getPriority() {
		return this.priority;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Date getDueDate() {
		return this.dueDate;
	}
	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}
	
	public Integer getSuspensionState() {
		return this.suspensionState;
	}

	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

}
