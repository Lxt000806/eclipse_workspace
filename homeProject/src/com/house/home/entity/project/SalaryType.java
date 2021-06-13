package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.house.framework.commons.orm.BaseEntity;

/**
 * SalaryType信息
 */
@Entity
@Table(name = "tSalaryType")
public class SalaryType extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "IsCalCost")
	private String isCalCost;
	@Column(name = "IsSign")
	private String isSign;
	@Column(name = "WorkType2")
	private String workType2;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IsCalProjectCost")
	private String isCalProjectCost;
	
	@Transient
	private String isCalCostDescr;
	@Transient
	private String isSignDescr;
	@Transient
	private String isCalProjectCoseDescr;
	@Transient
	private String workType1;
	@Transient
	private String workType1Descr;
	
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
	public void setIsCalCost(String isCalCost) {
		this.isCalCost = isCalCost;
	}
	
	public String getIsCalCost() {
		return this.isCalCost;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	
	public String getIsSign() {
		return this.isSign;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	
	public String getWorkType2() {
		return this.workType2;
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

	public String getIsCalProjectCost() {
		return isCalProjectCost;
	}

	public void setIsCalProjectCost(String isCalProjectCost) {
		this.isCalProjectCost = isCalProjectCost;
	}

	public String getIsCalCostDescr() {
		return isCalCostDescr;
	}

	public void setIsCalCostDescr(String isCalCostDescr) {
		this.isCalCostDescr = isCalCostDescr;
	}

	public String getIsSignDescr() {
		return isSignDescr;
	}

	public void setIsSignDescr(String isSignDescr) {
		this.isSignDescr = isSignDescr;
	}


	public String getIsCalProjectCoseDescr() {
		return isCalProjectCoseDescr;
	}

	public void setIsCalProjectCoseDescr(String isCalProjectCoseDescr) {
		this.isCalProjectCoseDescr = isCalProjectCoseDescr;
	}

	public String getWorkType1() {
		return workType1;
	}

	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}

	public String getWorkType1Descr() {
		return workType1Descr;
	}

	public void setWorkType1Descr(String workType1Descr) {
		this.workType1Descr = workType1Descr;
	}

}
