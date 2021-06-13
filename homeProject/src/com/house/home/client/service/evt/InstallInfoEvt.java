package com.house.home.client.service.evt;

public class InstallInfoEvt extends BaseQueryEvt {

	private Integer custWkPk;
	private String itemType2;
	
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	
}
