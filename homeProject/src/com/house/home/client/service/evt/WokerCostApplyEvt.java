package com.house.home.client.service.evt;

public class WokerCostApplyEvt extends BaseQueryEvt {
	private String custCode;
	private String workerCode;
	private String salaryType;
	private String status;
	private Integer custWkPk;
	private String workType2;
	private String workType12;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getSalaryType() {
		return salaryType;
	}
	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public String getWorkType2() {
		return workType2;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	
}
