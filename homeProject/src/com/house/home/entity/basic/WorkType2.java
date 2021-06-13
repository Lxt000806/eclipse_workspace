package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * WorkType2信息
 */
@Entity
@Table(name = "tWorkType2")
public class WorkType2 extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "WorkType1")
	private String workType1;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "CalType")
	private String calType;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "PrjItem")
	private String prjItem;
	@Column(name = "IsCalProjectCost")
	private String isCalProjectCost;
	@Column(name = "Salary")
	private String salary;
	@Column(name = "Worktype12")
	private String workType12;
	@Column(name = "IsConfirmTwo")
	private String isConfirmTwo;
	@Column(name = "IsPrjApp")
	private String isPrjApp;
	@Column(name="DenyOfferWorkType2")
	private String denyOfferWorkType2;
	@Column(name="SalaryCtrlType")
	private String salaryCtrlType;
	
	@Transient
	private String denyOfferWorkType2Descr;
	
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
	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}
	
	public String getWorkType1() {
		return this.workType1;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setCalType(String calType) {
		this.calType = calType;
	}
	
	public String getCalType() {
		return this.calType;
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
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	
	public String getPrjItem() {
		return this.prjItem;
	}

	public String getIsCalProjectCost() {
		return isCalProjectCost;
	}

	public void setIsCalProjectCost(String isCalProjectCost) {
		this.isCalProjectCost = isCalProjectCost;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getWorkType12() {
		return workType12;
	}

	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}

	public String getIsConfirmTwo() {
		return isConfirmTwo;
	}

	public void setIsConfirmTwo(String isConfirmTwo) {
		this.isConfirmTwo = isConfirmTwo;
	}

	public String getIsPrjApp() {
		return isPrjApp;
	}

	public void setIsPrjApp(String isPrjApp) {
		this.isPrjApp = isPrjApp;
	}

	public String getDenyOfferWorkType2() {
		return denyOfferWorkType2;
	}

	public void setDenyOfferWorkType2(String denyOfferWorkType2) {
		this.denyOfferWorkType2 = denyOfferWorkType2;
	}

	public String getDenyOfferWorkType2Descr() {
		return denyOfferWorkType2Descr;
	}

	public void setDenyOfferWorkType2Descr(String denyOfferWorkType2Descr) {
		this.denyOfferWorkType2Descr = denyOfferWorkType2Descr;
	}

	public String getSalaryCtrlType() {
		return salaryCtrlType;
	}

	public void setSalaryCtrlType(String salaryCtrlType) {
		this.salaryCtrlType = salaryCtrlType;
	}
	

}
