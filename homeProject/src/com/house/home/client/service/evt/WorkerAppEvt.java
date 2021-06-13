package com.house.home.client.service.evt;

public class WorkerAppEvt extends BaseQueryEvt{
	private String code;
	private String custCode;
	private String workType12;
	private Integer custWkPk;
	private String fromCostApply;
	private String salaryType;
	private String status;
	private String address;
	private String workType2;
	private String custWorkerStatus;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getWorkType12() {
		return workType12;
	}

	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}

	public Integer getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}

	public String getFromCostApply() {
		return fromCostApply;
	}

	public void setFromCostApply(String fromCostApply) {
		this.fromCostApply = fromCostApply;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWorkType2() {
		return workType2;
	}

	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}

	public String getCustWorkerStatus() {
		return custWorkerStatus;
	}

	public void setCustWorkerStatus(String custWorkerStatus) {
		this.custWorkerStatus = custWorkerStatus;
	}
	
}
