package com.house.home.client.service.resp;

import java.util.Date;

public class ItemAppSendQueryResp {
	private String iaNo;
	private String address;
	private Date date;
	private String no;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getIaNo() {
		return iaNo;
	}
	public void setIaNo(String iaNo) {
		this.iaNo = iaNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
