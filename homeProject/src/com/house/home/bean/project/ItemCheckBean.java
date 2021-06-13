package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemCheck信息bean
 */
public class ItemCheckBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="itemType1", order=3)
	private String itemType1;
	@ExcelAnnotation(exportName="status", order=4)
	private String status;
	@ExcelAnnotation(exportName="appRemark", order=5)
	private String appRemark;
    	@ExcelAnnotation(exportName="date", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date date;
	@ExcelAnnotation(exportName="appEmp", order=7)
	private String appEmp;
	@ExcelAnnotation(exportName="confirmRemark", order=8)
	private String confirmRemark;
    	@ExcelAnnotation(exportName="confirmDate", pattern="yyyy-MM-dd HH:mm:ss", order=9)
	private Date confirmDate;
	@ExcelAnnotation(exportName="confirmEmp", order=10)
	private String confirmEmp;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=11)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=12)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=13)
	private String actionLog;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=14)
	private Date lastUpdate;

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
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setAppRemark(String appRemark) {
		this.appRemark = appRemark;
	}
	
	public String getAppRemark() {
		return this.appRemark;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setAppEmp(String appEmp) {
		this.appEmp = appEmp;
	}
	
	public String getAppEmp() {
		return this.appEmp;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}
	
	public String getConfirmRemark() {
		return this.confirmRemark;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	public void setConfirmEmp(String confirmEmp) {
		this.confirmEmp = confirmEmp;
	}
	
	public String getConfirmEmp() {
		return this.confirmEmp;
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
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

}
