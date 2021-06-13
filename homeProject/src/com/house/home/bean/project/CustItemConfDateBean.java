package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CustItemConfDate信息bean
 */
public class CustItemConfDateBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="custCode", order=1)
	private String custCode;
	@ExcelAnnotation(exportName="itemTimeCode", order=2)
	private String itemTimeCode;
    	@ExcelAnnotation(exportName="confirmDate", pattern="yyyy-MM-dd HH:mm:ss", order=3)
	private Date confirmDate;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=5)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="actionLog", order=6)
	private String actionLog;
	@ExcelAnnotation(exportName="expired", order=7)
	private String expired;

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setItemTimeCode(String itemTimeCode) {
		this.itemTimeCode = itemTimeCode;
	}
	
	public String getItemTimeCode() {
		return this.itemTimeCode;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
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
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}

}
