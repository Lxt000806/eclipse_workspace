package com.house.home.client.service.resp;

import java.util.Date;

public class GiftCodeConfirmResp extends BaseResponse{
	
	private String nameChi;
	private Date lastUpdate;
	private String itemDescr;
	private int qty;
	private String uomDescr;
	private String address;
	private String nickName;
	private String phone;
	private String custCode;
	private String confirmCzy;
	
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getUomDescr() {
		return uomDescr;
	}
	public void setUomDescr(String uomDescr) {
		this.uomDescr = uomDescr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getConfirmCzy() {
		return confirmCzy;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
}
