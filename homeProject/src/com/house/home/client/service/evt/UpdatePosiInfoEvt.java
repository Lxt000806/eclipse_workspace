package com.house.home.client.service.evt;

public class UpdatePosiInfoEvt extends BaseEvt {
	private String city;
	private String city_limit;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity_limit() {
		return city_limit;
	}
	public void setCity_limit(String city_limit) {
		this.city_limit = city_limit;
	}
	
}
