package com.house.home.client.service.evt;

public class WorkerEvalListEvt extends BaseQueryEvt{
	private Integer custWkPk;

	public Integer getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
}
