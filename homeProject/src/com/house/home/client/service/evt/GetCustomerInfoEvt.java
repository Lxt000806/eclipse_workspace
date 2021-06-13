package com.house.home.client.service.evt;

import java.util.Date;

public class GetCustomerInfoEvt extends BaseQueryEvt {

	private Date dateFrom;
	private Date dateTo;
	private String currentPrjItem;
	private String region;
	private String address;
	
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getCurrentPrjItem() {
		return currentPrjItem;
	}
	public void setCurrentPrjItem(String currentPrjItem) {
		this.currentPrjItem = currentPrjItem;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
