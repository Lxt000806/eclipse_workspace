package com.house.home.client.service.evt;

public class ItemSentListEvt extends BaseQueryEvt{
	
	private String no ;
	private String custCode;
	private String itemType1;
	
	
	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	
}
