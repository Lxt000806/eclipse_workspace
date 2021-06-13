package com.house.home.client.service.resp;

public class GetActGiftDetailResp extends BaseResponse {
	private String itemCode;
	private String itemCodeDescr;
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemCodeDescr() {
		return itemCodeDescr;
	}
	public void setItemCodeDescr(String itemCodeDescr) {
		this.itemCodeDescr = itemCodeDescr;
	}
	

}
