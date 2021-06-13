package com.house.home.client.service.evt;

public class GetAdvertListEvt extends BaseQueryEvt {
	private String advType;

	public String getAdvType() {
		return advType;
	}

	public void setAdvType(String advType) {
		this.advType = advType;
	}
	
}
