package com.house.home.client.service.evt;

public class GetItemReqCountEvt extends BaseQueryEvt {
	private String custCode;
	private String itemType2;
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	
	
}
