package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * tWfProcInstCopy信息
 */
@Entity
@Table(name = "tWfProcInstCopy")
public class WfProcInstCopy extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "WfProcInstNo")
	private String wfProcInstNo;
	@Column(name = "TaskKey")
	private String taskKey;
	@Column(name = "CopyCzy")
	private String copyCzy;
	@Column(name = "CopyDate")
	private Date copyDate;
	@Column(name = "RcvDate")
	private Date rcvDate;
	@Column(name = "RcvStatus")
	private String rcvStatus;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getWfProcInstNo() {
		return wfProcInstNo;
	}
	public void setWfProcInstNo(String wfProcInstNo) {
		this.wfProcInstNo = wfProcInstNo;
	}
	public String getTaskKey() {
		return taskKey;
	}
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	public String getCopyCzy() {
		return copyCzy;
	}
	public void setCopyCzy(String copyCzy) {
		this.copyCzy = copyCzy;
	}
	public Date getCopyDate() {
		return copyDate;
	}
	public void setCopyDate(Date copyDate) {
		this.copyDate = copyDate;
	}
	public Date getRcvDate() {
		return rcvDate;
	}
	public void setRcvDate(Date rcvDate) {
		this.rcvDate = rcvDate;
	}
	public String getRcvStatus() {
		return rcvStatus;
	}
	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}

}
