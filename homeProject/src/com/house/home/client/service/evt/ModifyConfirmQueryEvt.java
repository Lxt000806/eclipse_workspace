package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ModifyConfirmQueryEvt extends BaseQueryEvt {
	@NotEmpty(message="操作员不能为空")
	private String czy;
	private String address;
	private String status;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCzy() {
		return czy;
	}
	public void setCzy(String czy) {
		this.czy = czy;
	}
	
}
