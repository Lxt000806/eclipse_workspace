package com.house.home.bean.driver;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemReturn信息bean
 */
public class ItemReturnBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="itemType1", order=2)
	private String itemType1;
	@ExcelAnnotation(exportName="custCode", order=3)
	private String custCode;
	@ExcelAnnotation(exportName="status", order=4)
	private String status;
	@ExcelAnnotation(exportName="appCzy", order=5)
	private String appCzy;
    	@ExcelAnnotation(exportName="date", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date date;
	@ExcelAnnotation(exportName="remarks", order=7)
	private String remarks;
	@ExcelAnnotation(exportName="driverCode", order=8)
	private String driverCode;
	@ExcelAnnotation(exportName="sendBatchNo", order=9)
	private String sendBatchNo;
	@ExcelAnnotation(exportName="actionLog", order=10)
	private String actionLog;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=11)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=12)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=13)
	private String expired;

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
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	
	public String getDriverCode() {
		return this.driverCode;
	}
	public void setSendBatchNo(String sendBatchNo) {
		this.sendBatchNo = sendBatchNo;
	}
	
	public String getSendBatchNo() {
		return this.sendBatchNo;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
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

}
