package com.house.home.client.service.evt;

public class DoUpdateActGiftEvt {
	private String opFlag;
	private Integer pk;
	private String itemCode;
	private Double qty;
	private String actNo;
	public String getOpFlag() {
		return opFlag;
	}
	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}
	
}
