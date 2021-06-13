package com.house.home.client.service.resp;

import java.util.Map;

public class PosiInfoResp extends BaseResponse {
	private String name;
	private Integer num;
	private String address;
	private String street_id;
	private String telephone;
	private Integer detail;
	private String uid;
	private Map location;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStreet_id() {
		return street_id;
	}
	public void setStreet_id(String street_id) {
		this.street_id = street_id;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getDetail() {
		return detail;
	}
	public void setDetail(Integer detail) {
		this.detail = detail;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Map getLocation() {
		return location;
	}
	public void setLocation(Map location) {
		this.location = location;
	}
	
}
