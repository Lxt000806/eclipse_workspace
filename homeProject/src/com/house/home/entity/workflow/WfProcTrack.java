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
 * tWfProcTrack信息
 */
@Entity
@Table(name = "tWfProcTrack")
public class WfProcTrack extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "WfProcInstNo")
    private String wfProcInstNo;
	@Column(name = "OperId")
	private String operId;
	@Column(name = "ActionType")
	private String actionType;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "FromActivityId")
	private String fromActivityId;
	@Column(name = "FromActivityDescr")
	private String fromActivityDescr;
	@Column(name = "ToActivityId")
	private String toActivityId;
	@Column(name = "ToActivityDescr")
	private String toActivityDescr;
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
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFromActivityId() {
		return fromActivityId;
	}
	public void setFromActivityId(String fromActivityId) {
		this.fromActivityId = fromActivityId;
	}
	public String getFromActivityDescr() {
		return fromActivityDescr;
	}
	public void setFromActivityDescr(String fromActivityDescr) {
		this.fromActivityDescr = fromActivityDescr;
	}
	public String getToActivityId() {
		return toActivityId;
	}
	public void setToActivityId(String toActivityId) {
		this.toActivityId = toActivityId;
	}
	public String getToActivityDescr() {
		return toActivityDescr;
	}
	public void setToActivityDescr(String toActivityDescr) {
		this.toActivityDescr = toActivityDescr;
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
