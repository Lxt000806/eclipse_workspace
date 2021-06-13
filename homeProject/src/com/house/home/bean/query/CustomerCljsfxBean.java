package com.house.home.bean.query;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * 客户签订bean
 */
public class CustomerCljsfxBean implements java.io.Serializable {

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
	@ExcelAnnotation(exportName="主材增减", order=7)
   	private Double mainChg;
	@ExcelAnnotation(exportName="主材结算", order=8)
   	private Double mainCheck;
	@ExcelAnnotation(exportName="瓷砖", order=9)
   	private Double tileAmount;
	@ExcelAnnotation(exportName="地板", order=10)
   	private Double floorAmount;
	@ExcelAnnotation(exportName="卫浴", order=11)
   	private Double bathAmount;
	@ExcelAnnotation(exportName="门", order=12)
   	private Double doorAmount;
	@ExcelAnnotation(exportName="集成吊顶", order=13)
   	private Double ceilingAmount;
	@ExcelAnnotation(exportName="开关", order=14)
   	private Double switchAmount;
	@ExcelAnnotation(exportName="其他", order=15)
   	private Double otherAmount;
	@ExcelAnnotation(exportName="主材成本", order=16)
   	private Double mainCost;
	@ExcelAnnotation(exportName="主材每平方净利", order=17)
   	private Double mainProfit;
	@ExcelAnnotation(exportName="主材实际净利率", order=18)
	private String mainProfitRateDescr;
	@ExcelAnnotation(exportName="主材管家", order=19)
	private String mainPlanDescr;
	@ExcelAnnotation(exportName="服务性产品预算", order=20)
   	private Double mainServFee;
	@ExcelAnnotation(exportName="服务性产品增减", order=21)
   	private Double servChg;
	@ExcelAnnotation(exportName="服务性产品结算", order=22)
   	private Double servCheck;
	@ExcelAnnotation(exportName="服务性产品成本", order=23)
   	private Double servCost;
	@ExcelAnnotation(exportName="毛利额", order=24)
   	private Double grossProfit;
	@ExcelAnnotation(exportName="提成", order=25)
   	private Double commiAmount;
	@ExcelAnnotation(exportName="实际毛利率", order=26)
	private String grossProfitRateDescr;
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
	public Double getMainChg() {
		return mainChg;
	}
	public void setMainChg(Double mainChg) {
		this.mainChg = mainChg;
	}
	public Double getMainCheck() {
		return mainCheck;
	}
	public void setMainCheck(Double mainCheck) {
		this.mainCheck = mainCheck;
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
	public Double getBathAmount() {
		return bathAmount;
	}
	public void setBathAmount(Double bathAmount) {
		this.bathAmount = bathAmount;
	}
	public Double getDoorAmount() {
		return doorAmount;
	}
	public void setDoorAmount(Double doorAmount) {
		this.doorAmount = doorAmount;
	}
	public Double getCeilingAmount() {
		return ceilingAmount;
	}
	public void setCeilingAmount(Double ceilingAmount) {
		this.ceilingAmount = ceilingAmount;
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
	public Double getMainCost() {
		return mainCost;
	}
	public void setMainCost(Double mainCost) {
		this.mainCost = mainCost;
	}
	public Double getMainProfit() {
		return mainProfit;
	}
	public void setMainProfit(Double mainProfit) {
		this.mainProfit = mainProfit;
	}
	public String getMainProfitRateDescr() {
		return mainProfitRateDescr;
	}
	public void setMainProfitRateDescr(String mainProfitRateDescr) {
		this.mainProfitRateDescr = mainProfitRateDescr;
	}
	public String getMainPlanDescr() {
		return mainPlanDescr;
	}
	public void setMainPlanDescr(String mainPlanDescr) {
		this.mainPlanDescr = mainPlanDescr;
	}
	public Double getMainServFee() {
		return mainServFee;
	}
	public void setMainServFee(Double mainServFee) {
		this.mainServFee = mainServFee;
	}
	public Double getServChg() {
		return servChg;
	}
	public void setServChg(Double servChg) {
		this.servChg = servChg;
	}
	public Double getServCheck() {
		return servCheck;
	}
	public void setServCheck(Double servCheck) {
		this.servCheck = servCheck;
	}
	public Double getServCost() {
		return servCost;
	}
	public void setServCost(Double servCost) {
		this.servCost = servCost;
	}
	public Double getGrossProfit() {
		return grossProfit;
	}
	public void setGrossProfit(Double grossProfit) {
		this.grossProfit = grossProfit;
	}
	public Double getCommiAmount() {
		return commiAmount;
	}
	public void setCommiAmount(Double commiAmount) {
		this.commiAmount = commiAmount;
	}
	public String getGrossProfitRateDescr() {
		return grossProfitRateDescr;
	}
	public void setGrossProfitRateDescr(String grossProfitRateDescr) {
		this.grossProfitRateDescr = grossProfitRateDescr;
	}	
}
