package com.house.home.client.service.resp;

import java.util.List;
import java.util.Map;


public class CustAccountDetailResp extends BaseResponse {
	private String mobile1;
	private String token;
	private List<Map<String,Object>> custCodeList;
	private String picAddr;
	private String smsPassWord;
	private String nickName;
	private String sessionId;
	private String userToken;

	public String getSmsPassWord() {
		return smsPassWord;
	}
	public void setSmsPassWord(String smsPassWord) {
		this.smsPassWord = smsPassWord;
	}
	private String descr;

	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	
	
}
