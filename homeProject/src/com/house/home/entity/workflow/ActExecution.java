package com.house.home.entity.workflow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActExecution信息
 */
@Entity
@Table(name = "ACT_RU_EXECUTION")
public class ActExecution extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "PROC_INST_ID_")
	private String procInstId;
	@Column(name = "BUSINESS_KEY_")
	private String businessKey;
	@Column(name = "PARENT_ID_")
	private String parentId;
	@Column(name = "PROC_DEF_ID_")
	private String procDefId;
	@Column(name = "SUPER_EXEC_")
	private String superExec;
	@Column(name = "ACT_ID_")
	private String actId;
	@Column(name = "IS_ACTIVE_")
	private Boolean isActive;
	@Column(name = "IS_CONCURRENT_")
	private Boolean isConcurrent;
	@Column(name = "IS_SCOPE_")
	private Boolean isScope;
	@Column(name = "IS_EVENT_SCOPE_")
	private Boolean isEventScope;
	@Column(name = "SUSPENSION_STATE_")
	private Integer suspensionState;
	@Column(name = "CACHED_ENT_STATE_")
	private Integer cachedEntState;

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
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	public String getProcInstId() {
		return this.procInstId;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
	public String getBusinessKey() {
		return this.businessKey;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getParentId() {
		return this.parentId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	public String getProcDefId() {
		return this.procDefId;
	}
	public void setSuperExec(String superExec) {
		this.superExec = superExec;
	}
	
	public String getSuperExec() {
		return this.superExec;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	
	public String getActId() {
		return this.actId;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Boolean getIsActive() {
		return this.isActive;
	}
	public void setIsConcurrent(Boolean isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
	
	public Boolean getIsConcurrent() {
		return this.isConcurrent;
	}
	public void setIsScope(Boolean isScope) {
		this.isScope = isScope;
	}
	
	public Boolean getIsScope() {
		return this.isScope;
	}
	public void setIsEventScope(Boolean isEventScope) {
		this.isEventScope = isEventScope;
	}
	
	public Boolean getIsEventScope() {
		return this.isEventScope;
	}
	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}
	
	public Integer getSuspensionState() {
		return this.suspensionState;
	}
	public void setCachedEntState(Integer cachedEntState) {
		this.cachedEntState = cachedEntState;
	}
	
	public Integer getCachedEntState() {
		return this.cachedEntState;
	}

}
