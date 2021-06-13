package com.house.home.client.service.evt;

public class GetRoomInfoEvt extends BaseEvt {

	private String custCode;

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
}
