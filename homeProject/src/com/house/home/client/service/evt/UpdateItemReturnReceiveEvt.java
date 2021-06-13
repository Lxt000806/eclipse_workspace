package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class UpdateItemReturnReceiveEvt extends BaseEvt {
	@NotEmpty(message="退货编号不能为空")
	private String id;
	@NotEmpty(message="司机编号不能为空")
	private String code;
	private String photoNames;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPhotoNames() {
		return photoNames;
	}
	public void setPhotoNames(String photoNames) {
		this.photoNames = photoNames;
	}

	
}
