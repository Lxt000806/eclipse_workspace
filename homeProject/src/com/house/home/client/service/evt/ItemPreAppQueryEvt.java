package com.house.home.client.service.evt;

public class ItemPreAppQueryEvt extends BaseQueryEvt{
	
	private String address;
	private String itemType1;
	private String appCzy;
    private String saleIndependence;
    
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAppCzy() {
		return appCzy;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getSaleIndependence() {
		return saleIndependence;
	}
	public void setSaleIndependence(String saleIndependence) {
		this.saleIndependence = saleIndependence;
	}


}
