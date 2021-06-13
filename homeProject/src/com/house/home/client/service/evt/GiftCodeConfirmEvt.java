package com.house.home.client.service.evt;

public class GiftCodeConfirmEvt extends BaseQueryEvt {

	private String text;
	private String phone;
	private String custCode;
	private String address;
	private String isNoAddress;
	private String remarks;
	private String czybh;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsNoAddress() {
		return isNoAddress;
	}

	public void setIsNoAddress(String isNoAddress) {
		this.isNoAddress = isNoAddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
}
