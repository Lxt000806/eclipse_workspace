package com.house.home.client.service.resp;

import java.util.List;
import java.util.Map;

public class WxCustomerResp extends BaseResponse{
	private String openId;
	private String session_key;
	private Boolean isLogined;
	private String phone;
	private List<Map<String,Object>> custCodeList;
	private String picAddr;
	private String nickName;
	private String userToken;
	
	
	public Boolean getIsLogined() {
		return isLogined;
	}
	public void setIsLogined(Boolean isLogined) {
		this.isLogined = isLogined;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<Map<String, Object>> getCustCodeList() {
		return custCodeList;
	}
	public void setCustCodeList(List<Map<String, Object>> custCodeList) {
		this.custCodeList = custCodeList;
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
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	
}
