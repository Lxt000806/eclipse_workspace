package com.house.home.client.service.evt;

public class WorkerQueryEvt extends BaseQueryEvt{
	
	private String nameChi;
	private String workType1;
	private String isLifeCost;
	private String EmpCode;
	private String isWithHold;
	private String custCode;
	private String workType2;
	
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getWorkType1() {
		return workType1;
	}
	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}
	public String getIsLifeCost() {
		return isLifeCost;
	}
	public void setIsLifeCost(String isLifeCost) {
		this.isLifeCost = isLifeCost;
	}
	public String getEmpCode() {
		return EmpCode;
	}
	public void setEmpCode(String empCode) {
		EmpCode = empCode;
	}
	public String getIsWithHold() {
		return isWithHold;
	}
	public void setIsWithHold(String isWithHold) {
		this.isWithHold = isWithHold;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getWorkType2() {
		return workType2;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	
}
