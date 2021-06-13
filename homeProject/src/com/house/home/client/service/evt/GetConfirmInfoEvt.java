package com.house.home.client.service.evt;

public class GetConfirmInfoEvt extends BaseEvt {
	private String custCode;
	private String prjItem;
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	
}
