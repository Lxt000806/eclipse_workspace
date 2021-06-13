package com.house.home.client.service.evt;

public class GetCustomerEvt extends BaseQueryEvt {
	private String constructStatus;
	private String address;
	private String projectMan;
	private String status;
	private String custCode;
	private String saleIndependence;
	
	public String getConstructStatus() {
		return constructStatus;
	}

	public void setConstructStatus(String constructStatus) {
		this.constructStatus = constructStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getSaleIndependence() {
		return saleIndependence;
	}

	public void setSaleIndependence(String saleIndependence) {
		this.saleIndependence = saleIndependence;
	}

	
}
