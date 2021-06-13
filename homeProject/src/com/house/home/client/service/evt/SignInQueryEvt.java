package com.house.home.client.service.evt;

import java.util.Date;



public class SignInQueryEvt extends BaseQueryEvt{
	
	private String custCode;
	private String signCzy;
	private Date crtDate;

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getSignCzy() {
		return signCzy;
	}

	public void setSignCzy(String signCzy) {
		this.signCzy = signCzy;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

}
