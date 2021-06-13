package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class IsSetItemEvt extends BaseEvt{
	
	@NotEmpty(message="客户类型不能为空")
	private String custType;
	@NotEmpty(message="材料类型1不能为空")
	private String itemType1;
	
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	
}
