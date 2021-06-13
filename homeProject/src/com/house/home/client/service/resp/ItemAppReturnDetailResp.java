package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ItemAppReturnDetailResp extends BaseResponse {
	private String address;
	private Date date;
	private String nameChi;
	private String phone;
	private String status;
	private String itemType1;
	private String custCode;
	private String number;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private List<Map<String,Object>> materialDetail;
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
	public List<Map<String, Object>> getMaterialDetail() {
		return materialDetail;
	}
	public void setMaterialDetail(List<Map<String, Object>> materialDetail) {
		this.materialDetail = materialDetail;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
