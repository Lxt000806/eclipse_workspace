package com.house.home.client.service.resp;

import java.util.Date;

public class ItemAppDetailPageQueryResp<T> extends BasePageQueryResp<T>{
	
	private Date date;
	private Date confirmDate;
	private Date sendDate;
	private Date arriveDate;
	private String remarks;
	private String no;
	private String address;
	private String status;
	private String supplyAddress;
	private String followRemark;
	private Date actualArriveDate;
	private String itemAppStatus;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Date getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSupplyAddress() {
		return supplyAddress;
	}
	public void setSupplyAddress(String supplyAddress) {
		this.supplyAddress = supplyAddress;
	}
	public String getFollowRemark() {
		return followRemark;
	}
	public void setFollowRemark(String followRemark) {
		this.followRemark = followRemark;
	}
	public Date getActualArriveDate() {
		return actualArriveDate;
	}
	public void setActualArriveDate(Date actualArriveDate) {
		this.actualArriveDate = actualArriveDate;
	}
	public String getItemAppStatus() {
		return itemAppStatus;
	}
	public void setItemAppStatus(String itemAppStatus) {
		this.itemAppStatus = itemAppStatus;
	}

}
