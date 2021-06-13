package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ItemAppSendArrivedDetailResp extends BaseResponse {
	private String driverRemark;
	private String arriveAddress;
	private Date date;
	private Date arriveDate;
	private String address;
	private String nameChi;
	private String phone;
	private String receiveAddress;
	private List<Map<String,Object>> materialDetail;
	public String getDriverRemark() {
		return driverRemark;
	}
	public void setDriverRemark(String driverRemark) {
		this.driverRemark = driverRemark;
	}
	public String getArriveAddress() {
		return arriveAddress;
	}
	public void setArriveAddress(String arriveAddress) {
		this.arriveAddress = arriveAddress;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public List<Map<String, Object>> getMaterialDetail() {
		return materialDetail;
	}
	public void setMaterialDetail(List<Map<String, Object>> materialDetail) {
		this.materialDetail = materialDetail;
	}
	public Date getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	
}
