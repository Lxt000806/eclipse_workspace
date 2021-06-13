package com.house.home.client.service.evt;

public class ExpenseAdvanceEvt extends BaseQueryEvt{
	
	private String actName;
	private String wfProcInstNo;
	private String empCode;
	
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getWfProcInstNo() {
		return wfProcInstNo;
	}

	public void setWfProcInstNo(String wfProcInstNo) {
		this.wfProcInstNo = wfProcInstNo;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}
	
	
}
