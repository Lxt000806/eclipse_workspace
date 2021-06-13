package com.house.home.client.service.evt;

/**
 * 开门二维码Evt
 * @author created by zb
 * @date   2019-5-20--下午3:35:22
 */
public class FarbellEvt extends BaseEvt {
	
	private String czybm;
	private String userCode; //使用者编号
	private String role; //角色

	public String getCzybm() {
		return czybm;
	}

	public void setCzybm(String czybm) {
		this.czybm = czybm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
