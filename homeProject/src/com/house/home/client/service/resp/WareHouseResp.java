package com.house.home.client.service.resp;

public class WareHouseResp {
	private String whCode;
	private String code; //wareHouseService.findPageBySqlCode 返回的是code add by zb on 20190920
	private String desc1;
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	
}
