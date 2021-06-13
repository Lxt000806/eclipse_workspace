package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * PreWorkCostDetail信息bean
 */
public class PreWorkCostDetailBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="salaryType", order=3)
	private String salaryType;
	@ExcelAnnotation(exportName="workType2", order=4)
	private String workType2;
	@ExcelAnnotation(exportName="workerCode", order=5)
	private String workerCode;
	@ExcelAnnotation(exportName="actName", order=6)
	private String actName;
	@ExcelAnnotation(exportName="cardId", order=7)
	private String cardId;
	@ExcelAnnotation(exportName="appAmount", order=8)
	private Double appAmount;
	@ExcelAnnotation(exportName="remarks", order=9)
	private String remarks;
	@ExcelAnnotation(exportName="status", order=10)
	private String status;
	@ExcelAnnotation(exportName="applyMan", order=11)
	private String applyMan;
    	@ExcelAnnotation(exportName="applyDate", pattern="yyyy-MM-dd HH:mm:ss", order=12)
	private Date applyDate;
	@ExcelAnnotation(exportName="confirmAssist", order=13)
	private String confirmAssist;
    	@ExcelAnnotation(exportName="assistConfirmDate", pattern="yyyy-MM-dd HH:mm:ss", order=14)
	private Date assistConfirmDate;
	@ExcelAnnotation(exportName="confirmManager", order=15)
	private String confirmManager;
    	@ExcelAnnotation(exportName="managerConfirmDate", pattern="yyyy-MM-dd HH:mm:ss", order=16)
	private Date managerConfirmDate;
	@ExcelAnnotation(exportName="no", order=17)
	private String no;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=18)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=19)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=20)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=21)
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
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
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	
	public String getWorkerCode() {
		return this.workerCode;
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
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}
	
	public String getApplyMan() {
		return this.applyMan;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	public Date getApplyDate() {
		return this.applyDate;
	}
	public void setConfirmAssist(String confirmAssist) {
		this.confirmAssist = confirmAssist;
	}
	
	public String getConfirmAssist() {
		return this.confirmAssist;
	}
	public void setAssistConfirmDate(Date assistConfirmDate) {
		this.assistConfirmDate = assistConfirmDate;
	}
	
	public Date getAssistConfirmDate() {
		return this.assistConfirmDate;
	}
	public void setConfirmManager(String confirmManager) {
		this.confirmManager = confirmManager;
	}
	
	public String getConfirmManager() {
		return this.confirmManager;
	}
	public void setManagerConfirmDate(Date managerConfirmDate) {
		this.managerConfirmDate = managerConfirmDate;
	}
	
	public Date getManagerConfirmDate() {
		return this.managerConfirmDate;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
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
