package com.house.home.client.service.evt;

public class CustEvalEvt extends BaseEvt{
	
	private String custCode;
	private Integer prjScope;
	private Integer designScope;
	
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Integer getPrjScope() {
		return prjScope;
	}
	public void setPrjScope(Integer prjScope) {
		this.prjScope = prjScope;
	}
	public Integer getDesignScope() {
		return designScope;
	}
	public void setDesignScope(Integer designScope) {
		this.designScope = designScope;
	}
	
	
}
