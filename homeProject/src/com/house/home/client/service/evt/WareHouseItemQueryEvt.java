package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class WareHouseItemQueryEvt extends BaseQueryEvt {
	@NotEmpty(message="仓库编号不能为空")
	private String whCode;
	private String itCode;
	private String desc1;
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	
	
}
