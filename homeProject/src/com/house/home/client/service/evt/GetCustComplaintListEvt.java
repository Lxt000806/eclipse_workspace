package com.house.home.client.service.evt;

public class GetCustComplaintListEvt extends BaseQueryEvt{
	
	private String address;
	private String status;
	private String department2Descr;
	private String haveGd;
	
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
	public String getDepartment2Descr() {
		return department2Descr;
	}
	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}
	public String getHaveGd() {
		return haveGd;
	}
	public void setHaveGd(String haveGd) {
		this.haveGd = haveGd;
	}
	
}
