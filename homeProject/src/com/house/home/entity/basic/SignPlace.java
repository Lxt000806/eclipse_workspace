package com.house.home.entity.basic;

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
 * SignPlace信息
 */
@Entity
@Table(name = "tSignPlace")
public class SignPlace extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pK;
	@Column(name="Descr")
	private String descr;
	@Column(name="CmpCode")
	private String cmpCode;
	@Column(name="Longitudetppc")
	private Double longitudetppc;
	@Column(name="Latitudetppc")
	private Double latitudetppc;
	@Column(name="LimitDistance")
	private Double limitDistance;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	
	@Transient
	private String cmpCodeDescr; //公司简称
	@Transient
	private String tude;//经纬度
	
	public Integer getpK() {
		return pK;
	}
	public void setpK(Integer pK) {
		this.pK = pK;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCmpCode() {
		return cmpCode;
	}
	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}
	public Double getLongitudetppc() {
		return longitudetppc;
	}
	public void setLongitudetppc(Double longitudetppc) {
		this.longitudetppc = longitudetppc;
	}
	public Double getLatitudetppc() {
		return latitudetppc;
	}
	public void setLatitudetppc(Double latitudetppc) {
		this.latitudetppc = latitudetppc;
	}
	public Double getLimitDistance() {
		return limitDistance;
	}
	public void setLimitDistance(Double limitDistance) {
		this.limitDistance = limitDistance;
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
	public String getCmpCodeDescr() {
		return cmpCodeDescr;
	}
	public void setCmpCodeDescr(String cmpCodeDescr) {
		this.cmpCodeDescr = cmpCodeDescr;
	}
	public String getTude() {
		return tude;
	}
	public void setTude(String tude) {
		this.tude = tude;
	}
	
}
