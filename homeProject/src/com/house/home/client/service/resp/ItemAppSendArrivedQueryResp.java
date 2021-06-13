package com.house.home.client.service.resp;

import java.util.Date;

public class ItemAppSendArrivedQueryResp {
	private String iaNo;
	private String address;
	private Date arriveDate;
	private String no;
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
	public Date getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}

}
