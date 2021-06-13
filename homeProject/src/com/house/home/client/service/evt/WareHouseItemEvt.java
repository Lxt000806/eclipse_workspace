package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class WareHouseItemEvt extends BaseEvt {
	@NotEmpty(message="仓库编号不能为空")
	private String whCode;
	@NotEmpty(message="材料编号不能为空")
	private String itCode;
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
	
}
