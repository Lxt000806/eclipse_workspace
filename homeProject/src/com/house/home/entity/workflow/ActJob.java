package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActJob信息
 */
@Entity
@Table(name = "ACT_RU_JOB")
public class ActJob extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "TYPE_")
	private String type;
	@Column(name = "LOCK_EXP_TIME_")
	private Date lockExpTime;
	@Column(name = "LOCK_OWNER_")
	private String lockOwner;
	@Column(name = "EXCLUSIVE_")
	private Boolean exclusive;
	@Column(name = "EXECUTION_ID_")
	private String executionId;
	@Column(name = "PROCESS_INSTANCE_ID_")
	private String processInstanceId;
	@Column(name = "PROC_DEF_ID_")
	private String procDefId;
	@Column(name = "RETRIES_")
	private Integer retries;
	@Column(name = "EXCEPTION_STACK_ID_")
	private String exceptionStackId;
	@Column(name = "EXCEPTION_MSG_")
	private String exceptionMsg;
	@Column(name = "DUEDATE_")
	private Date duedate;
	@Column(name = "REPEAT_")
	private String repeat;
	@Column(name = "HANDLER_TYPE_")
	private String handlerType;
	@Column(name = "HANDLER_CFG_")
	private String handlerCfg;

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
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setLockExpTime(Date lockExpTime) {
		this.lockExpTime = lockExpTime;
	}
	
	public Date getLockExpTime() {
		return this.lockExpTime;
	}
	public void setLockOwner(String lockOwner) {
		this.lockOwner = lockOwner;
	}
	
	public String getLockOwner() {
		return this.lockOwner;
	}
	public void setExclusive(Boolean exclusive) {
		this.exclusive = exclusive;
	}
	
	public Boolean getExclusive() {
		return this.exclusive;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	public String getExecutionId() {
		return this.executionId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public String getProcessInstanceId() {
		return this.processInstanceId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	public String getProcDefId() {
		return this.procDefId;
	}
	public void setRetries(Integer retries) {
		this.retries = retries;
	}
	
	public Integer getRetries() {
		return this.retries;
	}
	public void setExceptionStackId(String exceptionStackId) {
		this.exceptionStackId = exceptionStackId;
	}
	
	public String getExceptionStackId() {
		return this.exceptionStackId;
	}
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
	public String getExceptionMsg() {
		return this.exceptionMsg;
	}
	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
	
	public Date getDuedate() {
		return this.duedate;
	}
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	
	public String getRepeat() {
		return this.repeat;
	}
	public void setHandlerType(String handlerType) {
		this.handlerType = handlerType;
	}
	
	public String getHandlerType() {
		return this.handlerType;
	}
	public void setHandlerCfg(String handlerCfg) {
		this.handlerCfg = handlerCfg;
	}
	
	public String getHandlerCfg() {
		return this.handlerCfg;
	}

}
