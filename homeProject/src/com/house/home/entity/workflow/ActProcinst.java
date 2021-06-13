package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActProcinst信息
 */
@Entity
@Table(name = "ACT_HI_PROCINST")
public class ActProcinst extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "PROC_INST_ID_")
	private String procInstId;
	@Column(name = "BUSINESS_KEY_")
	private String businessKey;
	@Column(name = "PROC_DEF_ID_")
	private String procDefId;
	@Column(name = "START_TIME_")
	private Date startTime;
	@Column(name = "END_TIME_")
	private Date endTime;
	@Column(name = "DURATION_")
	private Double duration;
	@Column(name = "START_USER_ID_")
	private String startUserId;
	@Column(name = "START_ACT_ID_")
	private String startActId;
	@Column(name = "END_ACT_ID_")
	private String endActId;
	@Column(name = "SUPER_PROCESS_INSTANCE_ID_")
	private String superProcessInstanceId;
	@Column(name = "DELETE_REASON_")
	private String deleteReason;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
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
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	public String getProcDefId() {
		return this.procDefId;
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
	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}
	
	public String getStartUserId() {
		return this.startUserId;
	}
	public void setStartActId(String startActId) {
		this.startActId = startActId;
	}
	
	public String getStartActId() {
		return this.startActId;
	}
	public void setEndActId(String endActId) {
		this.endActId = endActId;
	}
	
	public String getEndActId() {
		return this.endActId;
	}
	public void setSuperProcessInstanceId(String superProcessInstanceId) {
		this.superProcessInstanceId = superProcessInstanceId;
	}
	
	public String getSuperProcessInstanceId() {
		return this.superProcessInstanceId;
	}
	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}
	
	public String getDeleteReason() {
		return this.deleteReason;
	}

}
