package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CustItemConfProg信息bean
 */
public class CustItemConfProgBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
    	@ExcelAnnotation(exportName="confirmDate", pattern="yyyy-MM-dd HH:mm:ss", order=3)
	private Date confirmDate;
	@ExcelAnnotation(exportName="itemConfStatus", order=4)
	private String itemConfStatus;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=6)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="actionLog", order=7)
	private String actionLog;
	@ExcelAnnotation(exportName="expired", order=8)
	private String expired;
	@ExcelAnnotation(exportName="remarks", order=9)
	private String remarks;

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
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	public void setItemConfStatus(String itemConfStatus) {
		this.itemConfStatus = itemConfStatus;
	}
	
	public String getItemConfStatus() {
		return this.itemConfStatus;
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
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}

}
