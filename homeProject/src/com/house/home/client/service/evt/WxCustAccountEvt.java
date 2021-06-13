package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class WxCustAccountEvt extends BaseEvt {
	@NotEmpty(message="账号不能为空")
	private String portalAccount;
	private String smsPwd;
	private String smsType;
	private Boolean fastLogin;
	//绑定楼盘的手机号
	private String phone;
	private String recommendSource;
	private String recommendCzybh;
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getFastLogin() {
		return fastLogin;
	}
	public void setFastLogin(Boolean fastLogin) {
		this.fastLogin = fastLogin;
	}
	public String getPortalAccount() {
		return portalAccount;
	}
	public void setPortalAccount(String portalAccount) {
		this.portalAccount = portalAccount;
	}
	public String getSmsPwd() {
		return smsPwd;
	}
	public void setSmsPwd(String smsPwd) {
		this.smsPwd = smsPwd;
	}
	public String getSmsType() {
		return smsType;
	}
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	public String getRecommendSource() {
		return recommendSource;
	}
	public void setRecommendSource(String recommendSource) {
		this.recommendSource = recommendSource;
	}
	public String getRecommendCzybh() {
		return recommendCzybh;
	}
	public void setRecommendCzybh(String recommendCzybh) {
		this.recommendCzybh = recommendCzybh;
	}
	
}
