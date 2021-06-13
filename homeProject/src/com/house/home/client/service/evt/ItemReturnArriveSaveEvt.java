package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemReturnArriveSaveEvt extends BaseEvt {
	@NotEmpty(message="退货编号不能为空")
	private String id;
	@NotEmpty(message="司机编号不能为空")
	private String code;
	private String pk;
	private String photoNames;
	private String address;
	private String remark;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getPhotoNames() {
		return photoNames;
	}
	public void setPhotoNames(String photoNames) {
		this.photoNames = photoNames;
	}

	
}
