package com.house.home.client.service.evt;

public class ItemAppTempQueryEvt extends BaseQueryEvt {
	private String itemType1;
	private String custCode;
	private String isItemSet;
	private Integer custWkPk;
	private String workerCode;
	private String workType12;
	
	public String getWorkType12() {
		return workType12;
	}

	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}

	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	public Integer getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}

	public String getIsItemSet() {
		return isItemSet;
	}

	public void setIsItemSet(String isItemSet) {
		this.isItemSet = isItemSet;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
}
