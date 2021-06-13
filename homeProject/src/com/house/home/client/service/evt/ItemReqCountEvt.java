package com.house.home.client.service.evt;

public class ItemReqCountEvt extends BaseEvt{

	private String custCode;
	private String itemType12;
	private String isCupboard;

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getItemType12() {
		return itemType12;
	}

	public void setItemType12(String itemType12) {
		this.itemType12 = itemType12;
	}

	public String getIsCupboard() {
		return isCupboard;
	}

	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	
	
}
