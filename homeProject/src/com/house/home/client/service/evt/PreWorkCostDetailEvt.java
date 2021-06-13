package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;

public class PreWorkCostDetailEvt extends BaseEvt{
	
	@NotNull(message="pk不能为空")
	private Integer pk;

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

}
