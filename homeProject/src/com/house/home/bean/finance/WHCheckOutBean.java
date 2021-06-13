package com.house.home.bean.finance;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * WHCheckOut信息bean
 */

public class WHCheckOutBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="记账单号", order=1)
	private String no;
	@ExcelAnnotation(exportName="账单状态", order=2)
	private String statusDescr;
	@ExcelAnnotation(exportName="凭证号", order=3)
	private String documentNo;
	@ExcelAnnotation(exportName="记账日期", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date checkDate;
	@ExcelAnnotation(exportName="备注", order=5)
	private String remarks;
	@ExcelAnnotation(exportName="仓库", order=6)
	private String whDescr;
	@ExcelAnnotation(exportName="开单人", order=7)
	private String appczyDescr;
	@ExcelAnnotation(exportName="申请日期", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date date;
	@ExcelAnnotation(exportName="审核人员", order=9)
	private String confirmczyDescr;
	@ExcelAnnotation(exportName="审核日期", pattern="yyyy-MM-dd HH:mm:ss", order=10)
	private Date confirmDate;
	@ExcelAnnotation(exportName="最后修改时间", pattern="yyyy-MM-dd HH:mm:ss", order=11)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="修改人", order=12)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="是否过期", order=13)
	private String expired;
	@ExcelAnnotation(exportName="操作", order=14)
	private String actionLog;
	@ExcelAnnotation(exportName="领料单号", order=14)
	private String iano;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getWhDescr() {
		return whDescr;
	}
	public void setWhDescr(String whDescr) {
		this.whDescr = whDescr;
	}
	public String getAppczyDescr() {
		return appczyDescr;
	}
	public void setAppczyDescr(String appczyDescr) {
		this.appczyDescr = appczyDescr;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getConfirmczyDescr() {
		return confirmczyDescr;
	}
	public void setConfirmczyDescr(String confirmczyDescr) {
		this.confirmczyDescr = confirmczyDescr;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getIano() {
		return iano;
	}
	public void setIano(String iano) {
		this.iano = iano;
	}
	
		
    
}
