package com.house.home.client.service.evt;

public class EmployeeQueryEvt extends BaseQueryEvt {
	private String name;
	private String isManager;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
