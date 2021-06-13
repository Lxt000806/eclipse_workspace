package com.house.home.client.service.evt;

public class YkCustomerEvt extends BaseQueryEvt{
	
	private String address;
	
	private String code;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
