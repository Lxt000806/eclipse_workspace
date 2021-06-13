package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class CustAccountEvt extends BaseEvt {
	@NotEmpty(message="账号不能为空")
	private String portalAccount;
	@NotNull(message="新密码不能为空")
	@Length(min=6, message="新密码应大于6位")
	private String portalPwd;
	@NotNull(message="确认密码不能为空")
	@Length(min=6, message="确认密码应大于6位")
	private String confirmPwd;
	@NotEmpty(message="验证码不能为空")
	private String smsPwd;
	@NotEmpty(message="短信请求类型不能为空")
	private String smsType;
	@NotEmpty(message="昵称不能为空")
	private String nickName;
	
	
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPortalAccount() {
		return portalAccount;
	}
	public void setPortalAccount(String portalAccount) {
		this.portalAccount = portalAccount;
	}
	public String getPortalPwd() {
		return portalPwd;
	}
	public void setPortalPwd(String portalPwd) {
		this.portalPwd = portalPwd;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
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
	
}
