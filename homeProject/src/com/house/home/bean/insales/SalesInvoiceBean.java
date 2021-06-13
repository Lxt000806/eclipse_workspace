package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * SalesInvoice信息bean
 */
public class SalesInvoiceBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="status", order=2)
	private String status;
	@ExcelAnnotation(exportName="type", order=3)
	private String type;
    	@ExcelAnnotation(exportName="date", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date date;
    	@ExcelAnnotation(exportName="getItemDate", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date getItemDate;
	@ExcelAnnotation(exportName="whcode", order=6)
	private String whcode;
	@ExcelAnnotation(exportName="custCode", order=7)
	private String custCode;
	@ExcelAnnotation(exportName="custName", order=8)
	private String custName;
	@ExcelAnnotation(exportName="befAmount", order=9)
	private Double befAmount;
	@ExcelAnnotation(exportName="calOnDiscAmt", order=10)
	private String calOnDiscAmt;
	@ExcelAnnotation(exportName="discPercentage", order=11)
	private Double discPercentage;
	@ExcelAnnotation(exportName="discAmount", order=12)
	private Double discAmount;
	@ExcelAnnotation(exportName="amount", order=13)
	private Double amount;
	@ExcelAnnotation(exportName="firstAmount", order=14)
	private Double firstAmount;
	@ExcelAnnotation(exportName="firstCash", order=15)
	private Double firstCash;
	@ExcelAnnotation(exportName="secondAmount", order=16)
	private Double secondAmount;
	@ExcelAnnotation(exportName="secondCash", order=17)
	private Double secondCash;
	@ExcelAnnotation(exportName="remainAmount", order=18)
	private Double remainAmount;
	@ExcelAnnotation(exportName="saleMan", order=19)
	private String saleMan;
	@ExcelAnnotation(exportName="softDesign", order=20)
	private String softDesign;
	@ExcelAnnotation(exportName="saleDirt", order=21)
	private String saleDirt;
	@ExcelAnnotation(exportName="remarks", order=22)
	private String remarks;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=23)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=24)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=25)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=26)
	private String actionLog;
	@ExcelAnnotation(exportName="isCal", order=27)
	private String isCal;
	@ExcelAnnotation(exportName="calNo", order=28)
	private String calNo;
	@ExcelAnnotation(exportName="itemType1", order=29)
	private String itemType1;
	@ExcelAnnotation(exportName="oldNo", order=30)
	private String oldNo;
	@ExcelAnnotation(exportName="otherAmount", order=31)
	private Double otherAmount;
	@ExcelAnnotation(exportName="isCheckOut", order=32)
	private String isCheckOut;
	@ExcelAnnotation(exportName="whcheckOutNo", order=33)
	private String whcheckOutNo;
	@ExcelAnnotation(exportName="checkSeq", order=34)
	private Integer checkSeq;
	@ExcelAnnotation(exportName="isAuthorized", order=35)
	private String isAuthorized;
	@ExcelAnnotation(exportName="authorizer", order=36)
	private String authorizer;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setGetItemDate(Date getItemDate) {
		this.getItemDate = getItemDate;
	}
	
	public Date getGetItemDate() {
		return this.getItemDate;
	}
	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}
	
	public String getWhcode() {
		return this.whcode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getCustName() {
		return this.custName;
	}
	public void setBefAmount(Double befAmount) {
		this.befAmount = befAmount;
	}
	
	public Double getBefAmount() {
		return this.befAmount;
	}
	public void setCalOnDiscAmt(String calOnDiscAmt) {
		this.calOnDiscAmt = calOnDiscAmt;
	}
	
	public String getCalOnDiscAmt() {
		return this.calOnDiscAmt;
	}
	public void setDiscPercentage(Double discPercentage) {
		this.discPercentage = discPercentage;
	}
	
	public Double getDiscPercentage() {
		return this.discPercentage;
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
	public void setFirstAmount(Double firstAmount) {
		this.firstAmount = firstAmount;
	}
	
	public Double getFirstAmount() {
		return this.firstAmount;
	}
	public void setFirstCash(Double firstCash) {
		this.firstCash = firstCash;
	}
	
	public Double getFirstCash() {
		return this.firstCash;
	}
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}
	
	public Double getSecondAmount() {
		return this.secondAmount;
	}
	public void setSecondCash(Double secondCash) {
		this.secondCash = secondCash;
	}
	
	public Double getSecondCash() {
		return this.secondCash;
	}
	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}
	
	public Double getRemainAmount() {
		return this.remainAmount;
	}
	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}
	
	public String getSaleMan() {
		return this.saleMan;
	}
	public void setSoftDesign(String softDesign) {
		this.softDesign = softDesign;
	}
	
	public String getSoftDesign() {
		return this.softDesign;
	}
	public void setSaleDirt(String saleDirt) {
		this.saleDirt = saleDirt;
	}
	
	public String getSaleDirt() {
		return this.saleDirt;
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
	public void setIsCal(String isCal) {
		this.isCal = isCal;
	}
	
	public String getIsCal() {
		return this.isCal;
	}
	public void setCalNo(String calNo) {
		this.calNo = calNo;
	}
	
	public String getCalNo() {
		return this.calNo;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setOldNo(String oldNo) {
		this.oldNo = oldNo;
	}
	
	public String getOldNo() {
		return this.oldNo;
	}
	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}
	
	public Double getOtherAmount() {
		return this.otherAmount;
	}
	public void setIsCheckOut(String isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	
	public String getIsCheckOut() {
		return this.isCheckOut;
	}
	public void setWhcheckOutNo(String whcheckOutNo) {
		this.whcheckOutNo = whcheckOutNo;
	}
	
	public String getWhcheckOutNo() {
		return this.whcheckOutNo;
	}
	public void setCheckSeq(Integer checkSeq) {
		this.checkSeq = checkSeq;
	}
	
	public Integer getCheckSeq() {
		return this.checkSeq;
	}
	public void setIsAuthorized(String isAuthorized) {
		this.isAuthorized = isAuthorized;
	}
	
	public String getIsAuthorized() {
		return this.isAuthorized;
	}
	public void setAuthorizer(String authorizer) {
		this.authorizer = authorizer;
	}
	
	public String getAuthorizer() {
		return this.authorizer;
	}

}
