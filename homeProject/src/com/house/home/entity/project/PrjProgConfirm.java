package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tPrjProgConfirm")
public class PrjProgConfirm extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "PrjItem")
	private String prjItem;
	@Column(name = "Date")
	private Date date;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Address")
	private String address;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name="IsPass")
	private String isPass;
	@Column(name="prjLevel")
	private String prjLevel;
	@Column(name = "AppCheck")
	private String appCheck;
	@Column(name = "WorkerCode")
	private String workerCode;
	@Column(name= "IsPushCust")
	private String isPushCust;
	@Column(name = "CustScore")
	private Integer custScore;
	@Column(name = "CustRemarks" )
	private String custRemarks;
	@Column(name = "ErrPosi" )
	private String errPosi;
	@Column(name = "PrjWorkable")
	private String prjWorkable;
	
	@Transient
	private String department2;
	@Transient
	private String prjItem1;
	@Transient
	private String allNo;
	@Transient
	private String nowNo;
	@Transient
	private String photoPath;
	
	@Transient
	private String fromPageFlag;
	@Transient
	private String constructType;
	@Transient
	private String fromCzy;
	@Transient
	private String isPrjConfirm;
	@Transient
	private String custScoreComfirm;
	
	
	

	

	public String getPrjWorkable() {
		return prjWorkable;
	}
	public void setPrjWorkable(String prjWorkable) {
		this.prjWorkable = prjWorkable;
	}
	public String getErrPosi() {
		return errPosi;
	}
	public void setErrPosi(String errPosi) {
		this.errPosi = errPosi;
	}
	public String getCustScoreComfirm() {
		return custScoreComfirm;
	}
	public void setCustScoreComfirm(String custScoreComfirm) {
		this.custScoreComfirm = custScoreComfirm;
	}
	public Integer getCustScore() {
		return custScore;
	}
	public void setCustScore(Integer custScore) {
		this.custScore = custScore;
	}
	public String getCustRemarks() {
		return custRemarks;
	}
	public void setCustRemarks(String custRemarks) {
		this.custRemarks = custRemarks;
	}
	public String getIsPrjConfirm() {
		return isPrjConfirm;
	}
	public void setIsPrjConfirm(String isPrjConfirm) {
		this.isPrjConfirm = isPrjConfirm;
	}
	public String getFromCzy() {
		return fromCzy;
	}
	public void setFromCzy(String fromCzy) {
		this.fromCzy = fromCzy;
	}
	public String getConstructType() {
		return constructType;
	}
	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getAllNo() {
		return allNo;
	}
	public void setAllNo(String allNo) {
		this.allNo = allNo;
	}
	public String getNowNo() {
		return nowNo;
	}
	public void setNowNo(String nowNo) {
		this.nowNo = nowNo;
	}
	public String getPrjItem1() {
		return prjItem1;
	}
	public void setPrjItem1(String prjItem1) {
		this.prjItem1 = prjItem1;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPrjLevel() {
		return prjLevel;
	}
	public void setPrjLevel(String prjLevel) {
		this.prjLevel = prjLevel;
	}
	public String getAppCheck() {
		return appCheck;
	}
	public void setAppCheck(String appCheck) {
		this.appCheck = appCheck;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getFromPageFlag() {
		return fromPageFlag;
	}
	public void setFromPageFlag(String fromPageFlag) {
		this.fromPageFlag = fromPageFlag;
	}
	public String getIsPushCust() {
		return isPushCust;
	}
	public void setIsPushCust(String isPushCust) {
		this.isPushCust = isPushCust;
	}

}
