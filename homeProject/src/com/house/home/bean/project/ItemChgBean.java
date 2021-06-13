package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemChg信息bean
 */
public class ItemChgBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="itemType1", order=2)
	private String itemType1;
	@ExcelAnnotation(exportName="custCode", order=3)
	private String custCode;
	@ExcelAnnotation(exportName="status", order=4)
	private String status;
    	@ExcelAnnotation(exportName="date", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date date;
	@ExcelAnnotation(exportName="befAmount", order=6)
	private Double befAmount;
	@ExcelAnnotation(exportName="discAmount", order=7)
	private Double discAmount;
	@ExcelAnnotation(exportName="amount", order=8)
	private Double amount;
	@ExcelAnnotation(exportName="remarks", order=9)
	private String remarks;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=10)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=11)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=12)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=13)
	private String actionLog;
	@ExcelAnnotation(exportName="isService", order=14)
	private Integer isService;
	@ExcelAnnotation(exportName="manageFee", order=15)
	private Double manageFee;
	@ExcelAnnotation(exportName="isCupboard", order=16)
	private String isCupboard;
	@ExcelAnnotation(exportName="discCost", order=17)
	private Double discCost;
	@ExcelAnnotation(exportName="appCzy", order=18)
	private String appCzy;
	@ExcelAnnotation(exportName="confirmCzy", order=19)
	private String confirmCzy;
    	@ExcelAnnotation(exportName="confirmdate", pattern="yyyy-MM-dd HH:mm:ss", order=20)
	private Date confirmdate;
	@ExcelAnnotation(exportName="perfPk", order=21)
	private Integer perfPk;
	@ExcelAnnotation(exportName="iscalPerf", order=22)
	private String iscalPerf;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setBefAmount(Double befAmount) {
		this.befAmount = befAmount;
	}
	
	public Double getBefAmount() {
		return this.befAmount;
	}
	public void setDiscAmount(Double discAmount) {
		this.discAmount = discAmount;
	}
	
	public Double getDiscAmount() {
		return this.discAmount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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
	public void setIsService(Integer isService) {
		this.isService = isService;
	}
	
	public Integer getIsService() {
		return this.isService;
	}
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	
	public Double getManageFee() {
		return this.manageFee;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	
	public String getIsCupboard() {
		return this.isCupboard;
	}
	public void setDiscCost(Double discCost) {
		this.discCost = discCost;
	}
	
	public Double getDiscCost() {
		return this.discCost;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
	public String getConfirmCzy() {
		return this.confirmCzy;
	}
	public void setConfirmdate(Date confirmdate) {
		this.confirmdate = confirmdate;
	}
	
	public Date getConfirmdate() {
		return this.confirmdate;
	}
	public void setPerfPk(Integer perfPk) {
		this.perfPk = perfPk;
	}
	
	public Integer getPerfPk() {
		return this.perfPk;
	}
	public void setIscalPerf(String iscalPerf) {
		this.iscalPerf = iscalPerf;
	}
	
	public String getIscalPerf() {
		return this.iscalPerf;
	}

}
