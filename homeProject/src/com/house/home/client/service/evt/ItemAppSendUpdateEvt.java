package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemAppSendUpdateEvt extends BaseEvt {
	@NotEmpty(message="送货编号不能为空")
	private String id;
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
	private String address;
	private String remark;
}
