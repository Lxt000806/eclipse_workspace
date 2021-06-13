package com.house.home.client.service.resp;

import java.util.Date;

public class ModifyConfirmQueryResp {
	private String address;
	private String no;
	private Date date;
	private String projectMan;
	private String projectManDescr;
	private String status;
	private String statusDescr;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getProjectManDescr() {
		return projectManDescr;
	}
	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	
}
