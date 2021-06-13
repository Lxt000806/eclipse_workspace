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
@Table(name = "tActivity")
public class Activity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "ActName")
	private String actName;
	@Column(name = "Times")
	private String times;
	@Column(name = "Sites")
	private String sites;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Prefix")
	private String prefix;
	@Column(name = "Length")
	private Integer length;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name="CmpActNo")
	private String cmpActNo;
	
	@Transient
	private String validAct;
	@Transient
	private String cmpActDescr;

	
	
	
	public String getCmpActDescr() {
		return cmpActDescr;
	}

	public void setCmpActDescr(String cmpActDescr) {
		this.cmpActDescr = cmpActDescr;
	}

	public String getValidAct() {
		return validAct;
	}

	public void setValidAct(String validAct) {
		this.validAct = validAct;
	}

	public void setNo(String no) {
		this.no = no;
	}
	public String getCmpActNo() {
		return cmpActNo;
	}

	public void setCmpActNo(String cmpActNo) {
		this.cmpActNo = cmpActNo;
	}
	public String getNo() {
		return this.no;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	
	public String getActName() {
		return this.actName;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	
	public String getTimes() {
		return this.times;
	}
	public void setSites(String sites) {
		this.sites = sites;
	}
	
	public String getSites() {
		return this.sites;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getBeginDate() {
		return this.beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	
	public Integer getLength() {
		return this.length;
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
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}

}
