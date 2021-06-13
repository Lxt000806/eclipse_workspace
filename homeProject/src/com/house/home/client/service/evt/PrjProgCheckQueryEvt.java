package com.house.home.client.service.evt;

public class PrjProgCheckQueryEvt extends BaseQueryEvt {
	private String address;
	private String isModify;
	private String custCode;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIsModify() {
		return isModify;
	}
	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
}
