package com.house.home.client.service.resp;

import java.util.Date;

public class ItemAppSendConfirmResp extends BaseResponse {
	private String address;
	private String confirmStatusDescr;
	private String no;
	private Date date;
	private String itemType1Descr;
	private String itemType2Descr;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getConfirmStatusDescr() {
		return confirmStatusDescr;
	}
	public void setConfirmStatusDescr(String confirmStatusDescr) {
		this.confirmStatusDescr = confirmStatusDescr;
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
	public String getItemType1Descr() {
		return itemType1Descr;
	}
	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}
	public String getItemType2Descr() {
		return itemType2Descr;
	}
	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}
	
	
}
