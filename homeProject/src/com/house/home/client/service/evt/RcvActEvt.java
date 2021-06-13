package com.house.home.client.service.evt;

public class RcvActEvt  extends BaseQueryEvt{
	
	private String descr;
	private String admin;
	private String cardId;
	private String custCode;
	private String payDate;
	private String receiveActName;
	private Double payAmount;
	private String payActName;
	private String actName;
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getReceiveActName() {
		return receiveActName;
	}
	public void setReceiveActName(String receiveActName) {
		this.receiveActName = receiveActName;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayActName() {
		return payActName;
	}
	public void setPayActName(String payActName) {
		this.payActName = payActName;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	
}
