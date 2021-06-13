package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * WorkCost信息bean
 */
public class WorkCostBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="type", order=2)
	private String type;
	@ExcelAnnotation(exportName="appCzy", order=3)
	private String appCzy;
    	@ExcelAnnotation(exportName="date", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date date;
	@ExcelAnnotation(exportName="remarks", order=5)
	private String remarks;
	@ExcelAnnotation(exportName="status", order=6)
	private String status;
	@ExcelAnnotation(exportName="confirmCzy", order=7)
	private String confirmCzy;
    	@ExcelAnnotation(exportName="confirmDate", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date confirmDate;
	@ExcelAnnotation(exportName="documentNo", order=9)
	private String documentNo;
	@ExcelAnnotation(exportName="payCzy", order=10)
	private String payCzy;
    	@ExcelAnnotation(exportName="payDate", pattern="yyyy-MM-dd HH:mm:ss", order=11)
	private Date payDate;
	@ExcelAnnotation(exportName="isSysCrt", order=12)
	private String isSysCrt;
	@ExcelAnnotation(exportName="commiNo", order=13)
	private String commiNo;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=14)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=15)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=16)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=17)
	private String actionLog;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
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
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
	public String getConfirmCzy() {
		return this.confirmCzy;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	
	public String getDocumentNo() {
		return this.documentNo;
	}
	public void setPayCzy(String payCzy) {
		this.payCzy = payCzy;
	}
	
	public String getPayCzy() {
		return this.payCzy;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	public Date getPayDate() {
		return this.payDate;
	}
	public void setIsSysCrt(String isSysCrt) {
		this.isSysCrt = isSysCrt;
	}
	
	public String getIsSysCrt() {
		return this.isSysCrt;
	}
	public void setCommiNo(String commiNo) {
		this.commiNo = commiNo;
	}
	
	public String getCommiNo() {
		return this.commiNo;
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
