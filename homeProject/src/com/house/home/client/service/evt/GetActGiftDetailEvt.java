package com.house.home.client.service.evt;

public class GetActGiftDetailEvt extends BaseEvt {
	private String itemCode;
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
}
