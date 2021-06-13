package com.house.home.entity.insales;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tPurchaseDelay")
public class PurchaseDelay extends BaseEntity{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK")
	private Integer Pk;
	@Column(name="PUNo")
	private String puno;
	@Column(name="ArriveDate")
	private Date arriveDate;
	@Column(name="Remarks")
	private String remarks;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="lastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	
	
	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	@Transient
	private String fitchID;
	@Transient
	private String m_umstate;
	@Transient
	private String oldStatus;
	
	public Integer getPk() {
		return Pk;
	}
	public void setPk(Integer pk) {
		Pk = pk;
	}
	public Date getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getPuno() {
		return puno;
	}
	public void setPuno(String puno) {
		this.puno = puno;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getFitchID() {
		return fitchID;
	}
	public void setFitchID(String fitchID) {
		this.fitchID = fitchID;
	}
	public String getM_umstate() {
		return m_umstate;
	}
	public void setM_umstate(String m_umstate) {
		this.m_umstate = m_umstate;
	}
	public String getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	
	
}
