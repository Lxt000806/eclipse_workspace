package com.house.home.client.service.resp;

public class IsSetItemResp extends BaseResponse{
	
	private String custType;
	private String itemType1;
	private String canInPlan;//能领预算材料（非套餐材料）1、是； 0、否
	private String canOutPlan;//能领无预算材料（套餐材料）1、是； 0、否
	
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
	public String getCanInPlan() {
		return canInPlan;
	}
	public void setCanInPlan(String canInPlan) {
		this.canInPlan = canInPlan;
	}
	public String getCanOutPlan() {
		return canOutPlan;
	}
	public void setCanOutPlan(String canOutPlan) {
		this.canOutPlan = canOutPlan;
	}

}
