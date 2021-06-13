package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tRegion")
public class Region extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Code")
	private String code;
	@Column(name="Descr")
	private String descr;
	@Column(name="IsSpcWorker")
	private String isSpcWorker;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "CmpCode")
	private String cmpCode; //公司编号
	@Column(name = "intSendType")
	private String intSendType;
	@Column(name = "WaterLongPension")
	private double waterLongPension;
	
	@Transient
	private String validDescr;
	@Transient
	private String isSpcWorkerDescr;
	@Transient
	private String cmpDescr; //公司名称
	
	public String getIntSendType() {
		return intSendType;
	}
	public void setIntSendType(String intSendType) {
		this.intSendType = intSendType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getIsSpcWorker() {
		return isSpcWorker;
	}
	public void setIsSpcWorker(String isSpcWorker) {
		this.isSpcWorker = isSpcWorker;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
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
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getValidDescr() {
		return validDescr;
	}
	public void setValidDescr(String validDescr) {
		this.validDescr = validDescr;
	}
	public String getIsSpcWorkerDescr() {
		return isSpcWorkerDescr;
	}
	public void setIsSpcWorkerDescr(String isSpcWorkerDescr) {
		this.isSpcWorkerDescr = isSpcWorkerDescr;
	}
	public String getCmpCode() {
		return cmpCode;
	}
	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}
	public String getCmpDescr() {
		return cmpDescr;
	}
	public void setCmpDescr(String cmpDescr) {
		this.cmpDescr = cmpDescr;
	}
	public double getWaterLongPension() {
		return waterLongPension;
	}
	public void setWaterLongPension(double waterLongPension) {
		this.waterLongPension = waterLongPension;
	}
}
