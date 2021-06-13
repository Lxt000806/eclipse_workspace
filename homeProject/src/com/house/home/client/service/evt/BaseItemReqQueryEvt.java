package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class BaseItemReqQueryEvt extends BaseQueryEvt{
	
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	private String fixAreaDescr;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getFixAreaDescr() {
		return fixAreaDescr;
	}
	public void setFixAreaDescr(String fixAreaDescr) {
		this.fixAreaDescr = fixAreaDescr;
	}
	

}
