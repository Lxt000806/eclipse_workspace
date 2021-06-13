package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class IntMeasureBrandEvt extends BaseEvt{
	
	@NotEmpty(message="类型不能为空")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
