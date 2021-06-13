package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * WorkCostDetail信息bean
 */
public class WorkCostDetailBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="no", order=2)
	private String no;
	@ExcelAnnotation(exportName="custCode", order=3)
	private String custCode;
	@ExcelAnnotation(exportName="applyMan", order=4)
	private String applyMan;
	@ExcelAnnotation(exportName="salaryType", order=5)
	private String salaryType;
	@ExcelAnnotation(exportName="workType2", order=6)
	private String workType2;
	@ExcelAnnotation(exportName="appAmount", order=7)
	private Double appAmount;
	@ExcelAnnotation(exportName="remarks", order=8)
	private String remarks;
	@ExcelAnnotation(exportName="actName", order=9)
	private String actName;
	@ExcelAnnotation(exportName="cardId", order=10)
	private String cardId;
	@ExcelAnnotation(exportName="isWithHold", order=11)
	private String isWithHold;
	@ExcelAnnotation(exportName="withHoldNo", order=12)
	private Integer withHoldNo;
	@ExcelAnnotation(exportName="confirmAmount", order=13)
	private Double confirmAmount;
	@ExcelAnnotation(exportName="confirmRemark", order=14)
	private String confirmRemark;
	@ExcelAnnotation(exportName="status", order=15)
	private String status;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=16)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=17)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=18)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=19)
	private String actionLog;
	@ExcelAnnotation(exportName="workerCode", order=20)
	private String workerCode;
	@ExcelAnnotation(exportName="refPrePk", order=21)
	private Integer refPrePk;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}
	
	public String getApplyMan() {
		return this.applyMan;
	}
	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}
	
	public String getSalaryType() {
		return this.salaryType;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	
	public String getWorkType2() {
		return this.workType2;
	}
	public void setAppAmount(Double appAmount) {
		this.appAmount = appAmount;
	}
	
	public Double getAppAmount() {
		return this.appAmount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	
	public String getActName() {
		return this.actName;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getCardId() {
		return this.cardId;
	}
	public void setIsWithHold(String isWithHold) {
		this.isWithHold = isWithHold;
	}
	
	public String getIsWithHold() {
		return this.isWithHold;
	}
	public void setWithHoldNo(Integer withHoldNo) {
		this.withHoldNo = withHoldNo;
	}
	
	public Integer getWithHoldNo() {
		return this.withHoldNo;
	}
	public void setConfirmAmount(Double confirmAmount) {
		this.confirmAmount = confirmAmount;
	}
	
	public Double getConfirmAmount() {
		return this.confirmAmount;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}
	
	public String getConfirmRemark() {
		return this.confirmRemark;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
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
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	
	public String getWorkerCode() {
		return this.workerCode;
	}
	public void setRefPrePk(Integer refPrePk) {
		this.refPrePk = refPrePk;
	}
	
	public Integer getRefPrePk() {
		return this.refPrePk;
	}

}
