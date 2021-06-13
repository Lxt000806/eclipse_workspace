package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * Activity信息
 */
@Entity
@Table(name = "tRegion2")
public class Region2 extends BaseEntity {
	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "RegionCode")
	private String regionCode;
	@Column(name = "PrjRegionCode")
	private String prjRegionCode;
	@Column(name = "LongFee")
	private Double longFee;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String validDescr;
	
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
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getPrjRegionCode() {
		return prjRegionCode;
	}
	public void setPrjRegionCode(String prjRegionCode) {
		this.prjRegionCode = prjRegionCode;
	}
	public Double getLongFee() {
		return longFee;
	}
	public void setLongFee(Double longFee) {
		this.longFee = longFee;
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
	public String getValidDescr() {
		return validDescr;
	}
	public void setValidDescr(String validDescr) {
		this.validDescr = validDescr;
	}
}
