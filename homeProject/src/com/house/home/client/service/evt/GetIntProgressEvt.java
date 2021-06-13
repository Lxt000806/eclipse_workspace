package com.house.home.client.service.evt;

public class GetIntProgressEvt extends BaseQueryEvt {
	private String custCode;

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
}
