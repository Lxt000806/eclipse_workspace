package com.house.home.client.service.evt;

public class PrjQtlFeeEvt extends BaseQueryEvt{
	
	private String type;
	private String empCode;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	
}
