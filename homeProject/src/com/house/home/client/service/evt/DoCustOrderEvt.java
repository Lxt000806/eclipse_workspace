package com.house.home.client.service.evt;

public class DoCustOrderEvt extends BaseEvt {
	
	private String custDescr;
	
	private String custPhone;

	public String getCustDescr() {
		return custDescr;
	}

	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	
}
