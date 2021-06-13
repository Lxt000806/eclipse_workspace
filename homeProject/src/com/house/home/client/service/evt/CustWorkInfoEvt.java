package com.house.home.client.service.evt;

public class CustWorkInfoEvt extends BaseEvt{
	public String workerCode;
	public String custCode;
	public Integer custWkPk;
	public String itemType1;
	public String workType12;
	public String workType2;
	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public Integer getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getWorkType12() {
		return workType12;
	}

	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}

	public String getWorkType2() {
		return workType2;
	}

	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	
}
