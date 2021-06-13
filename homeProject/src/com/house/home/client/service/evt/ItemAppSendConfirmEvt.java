package com.house.home.client.service.evt;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemAppSendConfirmEvt extends BaseEvt {
	@NotEmpty(message="发货单号不能为空")
	private String no;
	private String confirmMan;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getConfirmMan() {
		return confirmMan;
	}
	public void setConfirmMan(String confirmMan) {
		this.confirmMan = confirmMan;
	}
	
}
