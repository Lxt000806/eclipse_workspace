package com.house.home.client.service.evt;

public class GetCustStakeholderEvt extends BaseEvt{
	
	private String roll;
	private String custCode;
	
	public String getRoll() {
		return roll;
	}
	public void setRoll(String roll) {
		this.roll = roll;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

}
