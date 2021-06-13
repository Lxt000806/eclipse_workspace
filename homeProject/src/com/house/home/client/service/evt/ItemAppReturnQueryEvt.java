package com.house.home.client.service.evt;

public class ItemAppReturnQueryEvt extends BaseQueryEvt {
	private String address;
	private String status;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
