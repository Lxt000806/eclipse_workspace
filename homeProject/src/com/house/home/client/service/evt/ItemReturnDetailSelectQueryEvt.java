package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemReturnDetailSelectQueryEvt extends BaseQueryEvt{
	
	@NotEmpty(message="项目经理不能为空")
	private String projectMan;
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	private String itemType1;
	private String itemType2;
	private String itemCodeDescr;

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

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

	public String getItemCodeDescr() {
		return itemCodeDescr;
	}

	public void setItemCodeDescr(String itemCodeDescr) {
		this.itemCodeDescr = itemCodeDescr;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

}
