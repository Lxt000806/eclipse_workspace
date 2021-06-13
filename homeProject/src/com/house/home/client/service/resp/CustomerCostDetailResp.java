package com.house.home.client.service.resp;

public class CustomerCostDetailResp {
	
	private String workType1Name;
	private String workType2Name;
	private Double materialCostJs;
	private Double workCost;
	private Double allAmountJs;
	
	public String getWorkType1Name() {
		return workType1Name;
	}
	public void setWorkType1Name(String workType1Name) {
		this.workType1Name = workType1Name;
	}
	public String getWorkType2Name() {
		return workType2Name;
	}
	public void setWorkType2Name(String workType2Name) {
		this.workType2Name = workType2Name;
	}
	public Double getMaterialCostJs() {
		return materialCostJs;
	}
	public void setMaterialCostJs(Double materialCostJs) {
		this.materialCostJs = materialCostJs;
	}
	public Double getWorkCost() {
		return workCost;
	}
	public void setWorkCost(Double workCost) {
		this.workCost = workCost;
	}
	public Double getAllAmountJs() {
		return allAmountJs;
	}
	public void setAllAmountJs(Double allAmountJs) {
		this.allAmountJs = allAmountJs;
	}
	
	
}
