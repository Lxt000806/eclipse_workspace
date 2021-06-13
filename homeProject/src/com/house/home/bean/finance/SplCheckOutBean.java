package com.house.home.bean.finance;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * SplCheckOut信息bean
 */
public class SplCheckOutBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="splCode", order=2)
	private String splCode;
    	@ExcelAnnotation(exportName="date", pattern="yyyy-MM-dd HH:mm:ss", order=3)
	private Date date;
	@ExcelAnnotation(exportName="payType", order=4)
	private String payType;
    	@ExcelAnnotation(exportName="beginDate", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date beginDate;
    	@ExcelAnnotation(exportName="endDate", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date endDate;
	@ExcelAnnotation(exportName="payAmount", order=7)
	private Double payAmount;
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
	@ExcelAnnotation(exportName="remark", order=13)
	private String remark;
	@ExcelAnnotation(exportName="otherCost", order=14)
	private Double otherCost;
	@ExcelAnnotation(exportName="status", order=15)
	private String status;
	@ExcelAnnotation(exportName="confirmCzy", order=16)
	private String confirmCzy;
    	@ExcelAnnotation(exportName="confirmDate", pattern="yyyy-MM-dd HH:mm:ss", order=17)
	private Date confirmDate;
	@ExcelAnnotation(exportName="documentNo", order=18)
	private String documentNo;
	@ExcelAnnotation(exportName="payCzy", order=19)
	private String payCzy;
    	@ExcelAnnotation(exportName="payDate", pattern="yyyy-MM-dd HH:mm:ss", order=20)
	private Date payDate;
	@ExcelAnnotation(exportName="paidAmount", order=21)
	private Double paidAmount;
	@ExcelAnnotation(exportName="nowAmount", order=22)
	private Double nowAmount;
	@ExcelAnnotation(exportName="preAmount", order=23)
	private Double preAmount;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setSplCode(String splCode) {
		this.splCode = splCode;
	}
	
	public String getSplCode() {
		return this.splCode;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public String getPayType() {
		return this.payType;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getBeginDate() {
		return this.beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	
	public Double getPayAmount() {
		return this.payAmount;
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
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}
	
	public Double getOtherCost() {
		return this.otherCost;
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
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	
	public Double getPaidAmount() {
		return this.paidAmount;
	}
	public void setNowAmount(Double nowAmount) {
		this.nowAmount = nowAmount;
	}
	
	public Double getNowAmount() {
		return this.nowAmount;
	}
	public void setPreAmount(Double preAmount) {
		this.preAmount = preAmount;
	}
	
	public Double getPreAmount() {
		return this.preAmount;
	}

}
