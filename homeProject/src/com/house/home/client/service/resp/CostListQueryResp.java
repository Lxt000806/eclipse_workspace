package com.house.home.client.service.resp;

public class CostListQueryResp<T> extends BaseListQueryResp<T>{
	
	private Double allAmountJs;

	public Double getAllAmountJs() {
		return allAmountJs;
	}

	public void setAllAmountJs(Double allAmountJs) {
		this.allAmountJs = allAmountJs;
	}

}
