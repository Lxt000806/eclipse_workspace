package com.house.home.client.service.evt;

public class ItemReturnArrivedQueryEvt extends BaseQueryEvt {
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
