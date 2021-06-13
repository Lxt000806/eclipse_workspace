package com.house.home.client.service.evt;

public class GetShowBuildDetailEvt extends BaseEvt {
	private String custCode;
	private String gdbb;
	
	
	
	public String getGdbb() {
		return gdbb;
	}

	public void setGdbb(String gdbb) {
		this.gdbb = gdbb;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
}
