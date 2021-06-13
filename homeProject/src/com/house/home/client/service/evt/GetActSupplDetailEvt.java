package com.house.home.client.service.evt;

public class GetActSupplDetailEvt extends BaseEvt {
	private String supplCode;
	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
}
