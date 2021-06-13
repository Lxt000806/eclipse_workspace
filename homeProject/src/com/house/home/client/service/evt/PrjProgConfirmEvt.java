package com.house.home.client.service.evt;

public class PrjProgConfirmEvt extends BaseQueryEvt {
	private String custCode;
	private String fromPageFlag;
	private Integer custWkPk;
	private String isLeaveProblem;
	
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getFromPageFlag() {
		return fromPageFlag;
	}

	public void setFromPageFlag(String fromPageFlag) {
		this.fromPageFlag = fromPageFlag;
	}

	public Integer getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}

	public String getIsLeaveProblem() {
		return isLeaveProblem;
	}

	public void setIsLeaveProblem(String isLeaveProblem) {
		this.isLeaveProblem = isLeaveProblem;
	}
	
	
}
