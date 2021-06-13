package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * BaseItemChg信息bean
 */
public class BaseItemChgBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="status", order=3)
	private String status;
    	@ExcelAnnotation(exportName="date", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date date;
	@ExcelAnnotation(exportName="befAmount", order=5)
	private Double befAmount;
	@ExcelAnnotation(exportName="discAmount", order=6)
	private Double discAmount;
	@ExcelAnnotation(exportName="amount", order=7)
	private Double amount;
	@ExcelAnnotation(exportName="remarks", order=8)
	private String remarks;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=9)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=10)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=11)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=12)
	private String actionLog;
	@ExcelAnnotation(exportName="manageFee", order=13)
	private Double manageFee;
	@ExcelAnnotation(exportName="setMinus", order=14)
	private Double setMinus;
	@ExcelAnnotation(exportName="perfPk", order=15)
	private Integer perfPk;
	@ExcelAnnotation(exportName="iscalPerf", order=16)
	private String iscalPerf;

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
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	
	public Double getManageFee() {
		return this.manageFee;
	}
	public void setSetMinus(Double setMinus) {
		this.setMinus = setMinus;
	}
	
	public Double getSetMinus() {
		return this.setMinus;
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
