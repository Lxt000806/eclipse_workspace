package com.house.home.client.service.evt;

public class GetCustLoanEvt extends BaseQueryEvt {
	private String custCode;

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
}
