package com.house.home.client.service.evt;

public class WorkerListEvt extends BaseQueryEvt {
	
	private String custCode;
	private String prjRole;
	
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getPrjRole() {
		return prjRole;
	}

	public void setPrjRole(String prjRole) {
		this.prjRole = prjRole;
	}
	
}
