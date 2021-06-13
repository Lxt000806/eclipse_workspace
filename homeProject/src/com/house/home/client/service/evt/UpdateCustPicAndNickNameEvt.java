package com.house.home.client.service.evt;

public class UpdateCustPicAndNickNameEvt extends BaseEvt {

	private String phone;
	private String picAddr;
	private String nickName;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
}
