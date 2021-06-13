package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UpdatePwdEvt extends BaseEvt{
	
	@NotEmpty(message="用户账号不能为空")
	private String phone;
	
	@NotEmpty(message="旧密码不能为空")
	private String oldPwd;
	
	@NotNull(message="新密码不能为空")
	@Length(min=6,max=16,message="新密码应介于6-16之间")
	private String newPwd;
	
	@NotNull(message="确认密码不能为空")
	@Length(min=6,max=16,message="确认密码应介于6-16之间")
	private String confirmPwd;
	
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
