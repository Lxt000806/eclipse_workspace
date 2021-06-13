package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * WaterAreaCfm信息
 */
@Entity
@Table(name = "tWaterAreaCfm")
public class WaterAreaCfm extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "WorkerCode")
	private String workerCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "WaterArea")
	private Double waterArea;
	@Column(name = "AppDate")
	private Date appDate;
	@Column(name = "ConfirmCZY")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "ConfirmRemarks")
	private String confirmRemarks;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "ActionLog")
	private String actionLog;

	@Transient
	private String address;
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	
	public String getWorkerCode() {
		return this.workerCode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setWaterArea(Double waterArea) {
		this.waterArea = waterArea;
	}
	
	public Double getWaterArea() {
		return this.waterArea;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	
	public Date getAppDate() {
		return this.appDate;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
	public String getConfirmCzy() {
		return this.confirmCzy;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	public void setConfirmRemarks(String confirmRemarks) {
		this.confirmRemarks = confirmRemarks;
	}
	
	public String getConfirmRemarks() {
		return this.confirmRemarks;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
