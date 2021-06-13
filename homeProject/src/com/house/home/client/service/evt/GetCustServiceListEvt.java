package com.house.home.client.service.evt;

public class GetCustServiceListEvt extends BaseQueryEvt {

	private String custCode;
	private String custAccountMobile1;
	private String status;
	private String address;
	private String appStatus;
	
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getCustAccountMobile1() {
		return custAccountMobile1;
	}

	public void setCustAccountMobile1(String custAccountMobile1) {
		this.custAccountMobile1 = custAccountMobile1;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	
}
