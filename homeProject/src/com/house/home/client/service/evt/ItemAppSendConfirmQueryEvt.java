package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemAppSendConfirmQueryEvt extends BaseQueryEvt {
	@NotEmpty(message="项目经理不能为空")
	private String projectMan;
	private String address;
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
