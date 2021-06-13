package com.house.home.bean.query;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * 客户签订bean
 */
public class CustomerClqdtjBean_Xm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="事业部", order=1)
	private String dept1Descr;
	@ExcelAnnotation(exportName="设计部门", order=2)
	private String dept2Descr;
	@ExcelAnnotation(exportName="楼盘地址", order=3)
	private String address;
	@ExcelAnnotation(exportName="设计师", order=4)
	private String designManDescr; 
	@ExcelAnnotation(exportName="平方数", order=5)
	private Integer area;
	@ExcelAnnotation(exportName="主材合同额", order=6)
   	private Double mainFee;
	@ExcelAnnotation(exportName="瓷砖", order=7)
   	private Double tileAmount;
	@ExcelAnnotation(exportName="地板", order=8)
   	private Double floorAmount;
	@ExcelAnnotation(exportName="集成吊顶", order=9)
   	private Double ceilingAmount;
	@ExcelAnnotation(exportName="铝合金门", order=10)
   	private Double aluferDoorAmount;
	@ExcelAnnotation(exportName="木门", order=11)
   	private Double woodDoorAmount;
	@ExcelAnnotation(exportName="卫浴", order=12)
   	private Double bathAmount;
	@ExcelAnnotation(exportName="主材开关", order=13)
   	private Double switchAmount;
	@ExcelAnnotation(exportName="其他", order=14)
   	private Double otherAmount;
	@ExcelAnnotation(exportName="石材", order=15)
   	private Double stoneAmount;
	@ExcelAnnotation(exportName="服务性开关", order=16)
   	private Double servSwitchAmount;
	@ExcelAnnotation(exportName="主材成本", order=17)
   	private Double mainCost;
	@ExcelAnnotation(exportName="主材毛利润", order=18)
   	private Double grossProfit;
	@ExcelAnnotation(exportName="主材每平方净利", order=19)
   	private Double unitProfit;
	@ExcelAnnotation(exportName="主材管家", order=20)
	private String mainPlanDescr;
	public String getDept1Descr() {
		return dept1Descr;
	}
	public void setDept1Descr(String dept1Descr) {
		this.dept1Descr = dept1Descr;
	}
	public String getDept2Descr() {
		return dept2Descr;
	}
	public void setDept2Descr(String dept2Descr) {
		this.dept2Descr = dept2Descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDesignManDescr() {
		return designManDescr;
	}
	public void setDesignManDescr(String designManDescr) {
		this.designManDescr = designManDescr;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Double getMainFee() {
		return mainFee;
	}
	public void setMainFee(Double mainFee) {
		this.mainFee = mainFee;
	}
	public Double getTileAmount() {
		return tileAmount;
	}
	public void setTileAmount(Double tileAmount) {
		this.tileAmount = tileAmount;
	}
	public Double getFloorAmount() {
		return floorAmount;
	}
	public void setFloorAmount(Double floorAmount) {
		this.floorAmount = floorAmount;
	}
	public Double getCeilingAmount() {
		return ceilingAmount;
	}
	public void setCeilingAmount(Double ceilingAmount) {
		this.ceilingAmount = ceilingAmount;
	}
	public Double getAluferDoorAmount() {
		return aluferDoorAmount;
	}
	public void setAluferDoorAmount(Double aluferDoorAmount) {
		this.aluferDoorAmount = aluferDoorAmount;
	}
	public Double getWoodDoorAmount() {
		return woodDoorAmount;
	}
	public void setWoodDoorAmount(Double woodDoorAmount) {
		this.woodDoorAmount = woodDoorAmount;
	}
	public Double getBathAmount() {
		return bathAmount;
	}
	public void setBathAmount(Double bathAmount) {
		this.bathAmount = bathAmount;
	}
	public Double getSwitchAmount() {
		return switchAmount;
	}
	public void setSwitchAmount(Double switchAmount) {
		this.switchAmount = switchAmount;
	}
	public Double getOtherAmount() {
		return otherAmount;
	}
	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}
	public Double getStoneAmount() {
		return stoneAmount;
	}
	public void setStoneAmount(Double stoneAmount) {
		this.stoneAmount = stoneAmount;
	}
	public Double getServSwitchAmount() {
		return servSwitchAmount;
	}
	public void setServSwitchAmount(Double servSwitchAmount) {
		this.servSwitchAmount = servSwitchAmount;
	}
	public Double getMainCost() {
		return mainCost;
	}
	public void setMainCost(Double mainCost) {
		this.mainCost = mainCost;
	}
	public Double getGrossProfit() {
		return grossProfit;
	}
	public void setGrossProfit(Double grossProfit) {
		this.grossProfit = grossProfit;
	}
	public Double getUnitProfit() {
		return unitProfit;
	}
	public void setUnitProfit(Double unitProfit) {
		this.unitProfit = unitProfit;
	}
	public String getMainPlanDescr() {
		return mainPlanDescr;
	}
	public void setMainPlanDescr(String mainPlanDescr) {
		this.mainPlanDescr = mainPlanDescr;
	}
}
