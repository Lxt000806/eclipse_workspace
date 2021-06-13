package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class PreWorkCostDetailUpdateEvt extends BaseEvt{
	@NotNull(message="pk不能为空")
	private Integer pk;
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="工资类型不能为空")
	private String salaryType;
	private String workType1;
	@NotEmpty(message="工种类型2不能为空")
	private String workType2;
	@NotEmpty(message="工人编号不能为空")
	private String workerCode;
	@NotEmpty(message="工人姓名不能为空")
	private String actName;
	@NotEmpty(message="工人卡号不能为空")
	private String cardId;
	@NotNull(message="申请金额不能为空")
	private Double appAmount;
	@NotEmpty(message="申请人不能为空")
	private String applyMan;
	private String status;
	private String remarks;
	private String isWithHold;
	private Integer withHoldNo;
	
	private String cardId2;
	
	private Integer checkStatus;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getSalaryType() {
		return salaryType;
	}
	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}
	public String getWorkType2() {
		return workType2;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
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
	public Double getAppAmount() {
		return appAmount;
	}
	public void setAppAmount(Double appAmount) {
		this.appAmount = appAmount;
	}
	public String getApplyMan() {
		return applyMan;
	}
	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getWorkType1() {
		return workType1;
	}
	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
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
	public String getCardId2() {
		return cardId2;
	}
	public void setCardId2(String cardId2) {
		this.cardId2 = cardId2;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	
}
