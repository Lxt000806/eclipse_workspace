package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * Roll信息
 */
@Entity
@Table(name = "tRoll")
public class Roll extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "ChildCode")
	private String childCode;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "Department2")
	private String department2;
	@Column(name = "Department3")
	private String department3;
	@Column(name = "IsSale")
	private String isSale;
	
	@Transient
	private String childCodeDescr;
	@Transient
	private String department1Descr;
	@Transient
	private String department2Descr;
	@Transient
	private String department3Descr;

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setChildCode(String childCode) {
		this.childCode = childCode;
	}
	
	public String getChildCode() {
		return this.childCode;
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
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	
	public String getDepartment1() {
		return this.department1;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	
	public String getDepartment2() {
		return this.department2;
	}
	public void setDepartment3(String department3) {
		this.department3 = department3;
	}
	
	public String getDepartment3() {
		return this.department3;
	}
	public void setIsSale(String isSale) {
		this.isSale = isSale;
	}
	
	public String getIsSale() {
		return this.isSale;
	}

	public String getChildCodeDescr() {
		return childCodeDescr;
	}

	public void setChildCodeDescr(String childCodeDescr) {
		this.childCodeDescr = childCodeDescr;
	}

	public String getDepartment1Descr() {
		return department1Descr;
	}

	public void setDepartment1Descr(String department1Descr) {
		this.department1Descr = department1Descr;
	}

	public String getDepartment2Descr() {
		return department2Descr;
	}

	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}

	public String getDepartment3Descr() {
		return department3Descr;
	}

	public void setDepartment3Descr(String department3Descr) {
		this.department3Descr = department3Descr;
	}

}
