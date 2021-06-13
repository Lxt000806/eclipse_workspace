package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class GetPosiInfoEvt extends BaseEvt {
	private String city;
	private String city_limit;
	@NotEmpty(message="地址不能为空")
	private String address;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
