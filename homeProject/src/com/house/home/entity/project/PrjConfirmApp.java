package com.house.home.entity.project;

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
@Table(name = "tPrjConfirmApp")
public class PrjConfirmApp extends BaseEntity {
	private static final long serialVersionUID = 1L;

		@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private int pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "PrjItem")
	private String prjItem;
	@Column(name = "AppDate")
	private Date appDate;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name="RefConfirmNo")
	private String refConfirmNo;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "WorkerCode")
	private String workerCode; //验收申请工人 add by zb
	
	
	@Transient
	private String address;
	@Transient
	private String isPrjSpc;
	@Transient
	private String department1;
	@Transient
	private String department1Descr;
	@Transient
	private String department2Descr;
	@Transient
	private String projectManDescr;
	@Transient
	private Date date;
	@Transient
	private String phone;
	@Transient
	private String confirmCZY;
	@Transient
	private String prjLevelDescr;
	@Transient
	private String prjItemDescr;
	@Transient
	private String custDescr;
	
	@Transient
	private String region;
	

	@Transient
	private Date appDateFrom;
	@Transient
	private Date appDateTo;
	
	@Transient
	private String department2;
	@Transient
	private String custType;
	@Transient
	private String workerName;//验收申请姓名
	
	@Transient
	private String isLeaveProblem;
	@Transient
	private String leaveProblemRemark;
	@Transient
	private String prjRegion;
	@Transient
	private String workType12Dept;
	
	
	
	public String getPrjRegion() {
		return prjRegion;
	}
	public void setPrjRegion(String prjRegion) {
		this.prjRegion = prjRegion;
	}
	public String getWorkType12Dept() {
		return workType12Dept;
	}
	public void setWorkType12Dept(String workType12Dept) {
		this.workType12Dept = workType12Dept;
	}
	public String getIsLeaveProblem() {
		return isLeaveProblem;
	}
	public void setIsLeaveProblem(String isLeaveProblem) {
		this.isLeaveProblem = isLeaveProblem;
	}
	public String getLeaveProblemRemark() {
		return leaveProblemRemark;
	}
	public void setLeaveProblemRemark(String leaveProblemRemark) {
		this.leaveProblemRemark = leaveProblemRemark;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRefConfirmNo() {
		return refConfirmNo;
	}
	public void setRefConfirmNo(String refConfirmNo) {
		this.refConfirmNo = refConfirmNo;
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
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIsPrjSpc() {
		return isPrjSpc;
	}
	public void setIsPrjSpc(String isPrjSpc) {
		this.isPrjSpc = isPrjSpc;
	}
	public String getDepartment1() {
		return department1;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getConfirmCZY() {
		return confirmCZY;
	}
	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
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
	public String getProjectManDescr() {
		return projectManDescr;
	}
	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}
	public String getPrjLevelDescr() {
		return prjLevelDescr;
	}
	public void setPrjLevelDescr(String prjLevelDescr) {
		this.prjLevelDescr = prjLevelDescr;
	}
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}
	public String getCustDescr() {
		return custDescr;
	}
	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}
	public Date getAppDateFrom() {
		return appDateFrom;
	}
	public void setAppDateFrom(Date appDateFrom) {
		this.appDateFrom = appDateFrom;
	}
	public Date getAppDateTo() {
		return appDateTo;
	}
	public void setAppDateTo(Date appDateTo) {
		this.appDateTo = appDateTo;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	
}
