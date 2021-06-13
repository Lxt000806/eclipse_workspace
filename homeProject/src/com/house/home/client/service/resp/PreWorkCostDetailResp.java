package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class PreWorkCostDetailResp extends BaseResponse{
	private Integer pk;
	private String custCode;
	private String address;
	private String workType1;
	private String workType1Descr;
	private String workType2;
	private String workType2Descr;
	private String status;
	private String statusDescr;
	private Double appAmount;
	private String salaryType;
	private String workerCode;
	private String actName;
	private String cardId;
	private String applyMan;
	private Double confirmAmount;
	private String confirmRemark;
	private String remarks;
	private String isWithHold;
	private Integer withHoldNo;
	private String salaryDescr;
	private String isWorkApp;
	private Double workAppAmount;
	private String workType12;
	private String projectMan;
	private String cardId2;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date applyDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date payDate;
	
	private Double qualityFee;
	private Double realSalary;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getWorkType2() {
		return workType2;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	public String getWorkType2Descr() {
		return workType2Descr;
	}
	public void setWorkType2Descr(String workType2Descr) {
		this.workType2Descr = workType2Descr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public Double getAppAmount() {
		return appAmount;
	}
	public void setAppAmount(Double appAmount) {
		this.appAmount = appAmount;
	}
	public String getSalaryType() {
		return salaryType;
	}
	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getApplyMan() {
		return applyMan;
	}
	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}
	public Double getConfirmAmount() {
		return confirmAmount;
	}
	public void setConfirmAmount(Double confirmAmount) {
		this.confirmAmount = confirmAmount;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getConfirmRemark() {
		return confirmRemark;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}
	public String getIsWithHold() {
		return isWithHold;
	}
	public void setIsWithHold(String isWithHold) {
		this.isWithHold = isWithHold;
	}
	public Integer getWithHoldNo() {
		return withHoldNo;
	}
	public void setWithHoldNo(Integer withHoldNo) {
		this.withHoldNo = withHoldNo;
	}
	public String getSalaryDescr() {
		return salaryDescr;
	}
	public void setSalaryDescr(String salaryDescr) {
		this.salaryDescr = salaryDescr;
	}
	public String getIsWorkApp() {
		return isWorkApp;
	}
	public void setIsWorkApp(String isWorkApp) {
		this.isWorkApp = isWorkApp;
	}
	public Double getWorkAppAmount() {
		return workAppAmount;
	}
	public void setWorkAppAmount(Double workAppAmount) {
		this.workAppAmount = workAppAmount;
	}
	public String getCardId2() {
		return cardId2;
	}
	public void setCardId2(String cardId2) {
		this.cardId2 = cardId2;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Double getQualityFee() {
		return qualityFee;
	}
	public void setQualityFee(Double qualityFee) {
		this.qualityFee = qualityFee;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public Double getRealSalary() {
		return realSalary;
	}
	public void setRealSalary(Double realSalary) {
		this.realSalary = realSalary;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	
	
}
