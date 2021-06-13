package com.house.home.bean.finance;

import com.house.framework.commons.excel.ExcelAnnotation;

public class LaborFeeDetailBean implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="PK", order=1)
	private String pk;
	@ExcelAnnotation(exportName="客户编号", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="费用类型", order=3)
	private String feeType;
	@ExcelAnnotation(exportName="送货单号", order=4)
	private String appSendNo;
	@ExcelAnnotation(exportName="金额", order=5)
	private String amount;
	@ExcelAnnotation(exportName="户名", order=6)
	private String actName;
	@ExcelAnnotation(exportName="卡号", order=7)
	private String cardID;
	@ExcelAnnotation(exportName="备注", order=8)
	private String remarks;
	
	
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getAppSendNo() {
		return appSendNo;
	}
	public void setAppSendNo(String appSendNo) {
		this.appSendNo = appSendNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
