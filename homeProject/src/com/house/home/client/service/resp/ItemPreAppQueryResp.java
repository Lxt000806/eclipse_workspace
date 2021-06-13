package com.house.home.client.service.resp;

import java.util.Date;

public class ItemPreAppQueryResp {
	private String no;
	private String custCode;
	private String address;
	private Date date;
	private String itemType1Descr;
	private String statusDescr;
	private String endCodeDescr;
	private String itemType2Descr;
	private String mark;
	private String appStatusDescr;
	private String isPrjManApp;
	private String itemPreAppStatusTipDescr;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
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
	public String getItemType1Descr() {
		return itemType1Descr;
	}
	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getEndCodeDescr() {
		return endCodeDescr;
	}
	public void setEndCodeDescr(String endCodeDescr) {
		this.endCodeDescr = endCodeDescr;
	}
	public String getItemType2Descr() {
		return itemType2Descr;
	}
	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getAppStatusDescr() {
		return appStatusDescr;
	}
	public void setAppStatusDescr(String appStatusDescr) {
		this.appStatusDescr = appStatusDescr;
	}
	public String getIsPrjManApp() {
		return isPrjManApp;
	}
	public void setIsPrjManApp(String isPrjManApp) {
		this.isPrjManApp = isPrjManApp;
	}
	public String getItemPreAppStatusTipDescr() {
		return itemPreAppStatusTipDescr;
	}
	public void setItemPreAppStatusTipDescr(String itemPreAppStatusTipDescr) {
		this.itemPreAppStatusTipDescr = itemPreAppStatusTipDescr;
	}
	
}
