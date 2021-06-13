package com.house.home.client.service.resp;

public class GetActSupplDetailResp extends BaseResponse {
	private String supplType;
	private String supplCode;
	private String supplCodeDescr;
	
	public String getSupplType() {
		return supplType;
	}
	public void setSupplType(String supplType) {
		this.supplType = supplType;
	}
	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	public String getSupplCodeDescr() {
		return supplCodeDescr;
	}
	public void setSupplCodeDescr(String supplCodeDescr) {
		this.supplCodeDescr = supplCodeDescr;
	}
}
