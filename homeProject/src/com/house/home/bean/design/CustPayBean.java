package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CustPay信息bean
 */
public class CustPayBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="客户编号", order=2)
	private String custCode;
    @ExcelAnnotation(exportName="付款日期", pattern="yyyy-MM-dd HH:mm:ss", order=3)
	private Date date;
	@ExcelAnnotation(exportName="付款金额", order=4)
	private Double amount;
	@ExcelAnnotation(exportName="备注", order=5)
	private String remarks;
    @ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=7)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=8)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=9)
	private String actionLog;
	@ExcelAnnotation(exportName="isCheckOut", order=10)
	private String isCheckOut;
	@ExcelAnnotation(exportName="payCheckOutNo", order=11)
	private String payCheckOutNo;
	@ExcelAnnotation(exportName="checkSeq", order=12)
	private Integer checkSeq;
	@ExcelAnnotation(exportName="收款账户", order=13)
	private String rcvAct;
	@ExcelAnnotation(exportName="POS机", order=14)
	private String posCode;
	@ExcelAnnotation(exportName="手续费", order=15)
	private Double procedureFee;
    @ExcelAnnotation(exportName="addDate", pattern="yyyy-MM-dd HH:mm:ss", order=16)
	private Date addDate;
	@ExcelAnnotation(exportName="类型", order=17)
	private String type;
	@ExcelAnnotation(exportName="收款单号", order=18)
	private String payNo;
	
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
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
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
	public void setIsCheckOut(String isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	
	public String getIsCheckOut() {
		return this.isCheckOut;
	}
	public void setPayCheckOutNo(String payCheckOutNo) {
		this.payCheckOutNo = payCheckOutNo;
	}
	
	public String getPayCheckOutNo() {
		return this.payCheckOutNo;
	}
	public void setCheckSeq(Integer checkSeq) {
		this.checkSeq = checkSeq;
	}
	
	public Integer getCheckSeq() {
		return this.checkSeq;
	}
	public void setRcvAct(String rcvAct) {
		this.rcvAct = rcvAct;
	}
	
	public String getRcvAct() {
		return this.rcvAct;
	}
	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}
	
	public String getPosCode() {
		return this.posCode;
	}
	public void setProcedureFee(Double procedureFee) {
		this.procedureFee = procedureFee;
	}
	
	public Double getProcedureFee() {
		return this.procedureFee;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	public Date getAddDate() {
		return this.addDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

}
