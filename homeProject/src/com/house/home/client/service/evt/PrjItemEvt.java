package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class PrjItemEvt extends BaseEvt{
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	private String prjItemDescr;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}

}
