package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class PrjProDateEvt extends BaseEvt {
	private String id;
	@NotEmpty(message="楼盘号不能为空")
	private String code;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
