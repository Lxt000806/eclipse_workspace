package com.house.home.client.service.evt;

public class GetWfProcInstDataEvt extends BaseQueryEvt{
	
	private String processInstanceId;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}
