package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemReqEvt extends BaseQueryEvt{
	
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="材料类型1不能为空")
	private String itemType1;
	private Integer isService;
	
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
	public Integer getIsService() {
		return isService;
	}
	public void setIsService(Integer isService) {
		this.isService = isService;
	}

}
