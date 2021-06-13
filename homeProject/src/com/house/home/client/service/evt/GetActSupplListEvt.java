package com.house.home.client.service.evt;

public class GetActSupplListEvt extends BaseQueryEvt {
	private String actNo;
	private String supplType;
	private String supplCodeDescr;
	
	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}
	public String getSupplType() {
		return supplType;
	}
	public void setSupplType(String supplType) {
		this.supplType = supplType;
	}
	public String getSupplCodeDescr() {
		return supplCodeDescr;
	}
	public void setSupplCodeDescr(String supplCodeDescr) {
		this.supplCodeDescr = supplCodeDescr;
	}	
}
