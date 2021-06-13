package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotBlank;

public class ItemType2QueryEvt extends BaseQueryEvt{
	
	@NotBlank(message="材料类型1不能为空")
	private String itemType1;
	private String code;
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
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
