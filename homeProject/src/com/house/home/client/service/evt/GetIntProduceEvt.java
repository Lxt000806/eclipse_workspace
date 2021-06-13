package com.house.home.client.service.evt;

public class GetIntProduceEvt extends BaseQueryEvt {
	private String custCode;
	private String supplCode;

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getSupplCode() {
		return supplCode;
	}

	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	
}
