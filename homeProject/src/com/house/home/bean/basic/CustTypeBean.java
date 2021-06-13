package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CustType信息bean
 */
public class CustTypeBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="code", order=1)
	private String code;
	@ExcelAnnotation(exportName="desc1", order=2)
	private String desc1;
	@ExcelAnnotation(exportName="type", order=3)
	private String type;
	@ExcelAnnotation(exportName="areaPer", order=4)
	private Integer areaPer;
	@ExcelAnnotation(exportName="baseFeeCompPer", order=5)
	private Double baseFeeCompPer;
	@ExcelAnnotation(exportName="baseFeeDirctPer", order=6)
	private Double baseFeeDirctPer;
	@ExcelAnnotation(exportName="mainSetFeePer", order=7)
	private Double mainSetFeePer;
	@ExcelAnnotation(exportName="setMinusPer", order=8)
	private Double setMinusPer;
	@ExcelAnnotation(exportName="setAddPer", order=9)
	private Double setAddPer;
	@ExcelAnnotation(exportName="longFeePer", order=10)
	private Double longFeePer;
	@ExcelAnnotation(exportName="manageFeeBasePer", order=11)
	private Double manageFeeBasePer;
	@ExcelAnnotation(exportName="mainFeePer", order=12)
	private Double mainFeePer;
	@ExcelAnnotation(exportName="mainDiscPer", order=13)
	private Double mainDiscPer;
	@ExcelAnnotation(exportName="manageFeeMainPer", order=14)
	private Double manageFeeMainPer;
	@ExcelAnnotation(exportName="mainServFeePer", order=15)
	private Double mainServFeePer;
	@ExcelAnnotation(exportName="manageFeeServPer", order=16)
	private Double manageFeeServPer;
	@ExcelAnnotation(exportName="softFeePer", order=17)
	private Double softFeePer;
	@ExcelAnnotation(exportName="softDiscPer", order=18)
	private Double softDiscPer;
	@ExcelAnnotation(exportName="manageFeeSoftPer", order=19)
	private Double manageFeeSoftPer;
	@ExcelAnnotation(exportName="integrateFeePer", order=20)
	private Double integrateFeePer;
	@ExcelAnnotation(exportName="integrateDiscPer", order=21)
	private Double integrateDiscPer;
	@ExcelAnnotation(exportName="manageFeeIntPer", order=22)
	private Double manageFeeIntPer;
	@ExcelAnnotation(exportName="cupboardFeePer", order=23)
	private Double cupboardFeePer;
	@ExcelAnnotation(exportName="cupBoardDiscPer", order=24)
	private Double cupBoardDiscPer;
	@ExcelAnnotation(exportName="manageFeeCupPer", order=25)
	private Double manageFeeCupPer;
	@ExcelAnnotation(exportName="chgManageFeePer", order=26)
	private Double chgManageFeePer;
	@ExcelAnnotation(exportName="isDefaultStatic", order=27)
	private String isDefaultStatic;
	@ExcelAnnotation(exportName="expired", order=28)
	private String expired;
	@ExcelAnnotation(exportName="confirmItem", order=29)
	private String confirmItem;
	@ExcelAnnotation(exportName="isAddAllInfo", order=30)
	private String isAddAllInfo;
	@ExcelAnnotation(exportName="ctrlExpr", order=31)
	private String ctrlExpr;
	@ExcelAnnotation(exportName="isCalcPerf", order=32)
	private String isCalcPerf;
	@ExcelAnnotation(exportName="firstPayPer", order=33)
	private Double firstPayPer;

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	
	public String getDesc1() {
		return this.desc1;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setAreaPer(Integer areaPer) {
		this.areaPer = areaPer;
	}
	
	public Integer getAreaPer() {
		return this.areaPer;
	}
	public void setBaseFeeCompPer(Double baseFeeCompPer) {
		this.baseFeeCompPer = baseFeeCompPer;
	}
	
	public Double getBaseFeeCompPer() {
		return this.baseFeeCompPer;
	}
	public void setBaseFeeDirctPer(Double baseFeeDirctPer) {
		this.baseFeeDirctPer = baseFeeDirctPer;
	}
	
	public Double getBaseFeeDirctPer() {
		return this.baseFeeDirctPer;
	}
	public void setMainSetFeePer(Double mainSetFeePer) {
		this.mainSetFeePer = mainSetFeePer;
	}
	
	public Double getMainSetFeePer() {
		return this.mainSetFeePer;
	}
	public void setSetMinusPer(Double setMinusPer) {
		this.setMinusPer = setMinusPer;
	}
	
	public Double getSetMinusPer() {
		return this.setMinusPer;
	}
	public void setSetAddPer(Double setAddPer) {
		this.setAddPer = setAddPer;
	}
	
	public Double getSetAddPer() {
		return this.setAddPer;
	}
	public void setLongFeePer(Double longFeePer) {
		this.longFeePer = longFeePer;
	}
	
	public Double getLongFeePer() {
		return this.longFeePer;
	}
	public void setManageFeeBasePer(Double manageFeeBasePer) {
		this.manageFeeBasePer = manageFeeBasePer;
	}
	
	public Double getManageFeeBasePer() {
		return this.manageFeeBasePer;
	}
	public void setMainFeePer(Double mainFeePer) {
		this.mainFeePer = mainFeePer;
	}
	
	public Double getMainFeePer() {
		return this.mainFeePer;
	}
	public void setMainDiscPer(Double mainDiscPer) {
		this.mainDiscPer = mainDiscPer;
	}
	
	public Double getMainDiscPer() {
		return this.mainDiscPer;
	}
	public void setManageFeeMainPer(Double manageFeeMainPer) {
		this.manageFeeMainPer = manageFeeMainPer;
	}
	
	public Double getManageFeeMainPer() {
		return this.manageFeeMainPer;
	}
	public void setMainServFeePer(Double mainServFeePer) {
		this.mainServFeePer = mainServFeePer;
	}
	
	public Double getMainServFeePer() {
		return this.mainServFeePer;
	}
	public void setManageFeeServPer(Double manageFeeServPer) {
		this.manageFeeServPer = manageFeeServPer;
	}
	
	public Double getManageFeeServPer() {
		return this.manageFeeServPer;
	}
	public void setSoftFeePer(Double softFeePer) {
		this.softFeePer = softFeePer;
	}
	
	public Double getSoftFeePer() {
		return this.softFeePer;
	}
	public void setSoftDiscPer(Double softDiscPer) {
		this.softDiscPer = softDiscPer;
	}
	
	public Double getSoftDiscPer() {
		return this.softDiscPer;
	}
	public void setManageFeeSoftPer(Double manageFeeSoftPer) {
		this.manageFeeSoftPer = manageFeeSoftPer;
	}
	
	public Double getManageFeeSoftPer() {
		return this.manageFeeSoftPer;
	}
	public void setIntegrateFeePer(Double integrateFeePer) {
		this.integrateFeePer = integrateFeePer;
	}
	
	public Double getIntegrateFeePer() {
		return this.integrateFeePer;
	}
	public void setIntegrateDiscPer(Double integrateDiscPer) {
		this.integrateDiscPer = integrateDiscPer;
	}
	
	public Double getIntegrateDiscPer() {
		return this.integrateDiscPer;
	}
	public void setManageFeeIntPer(Double manageFeeIntPer) {
		this.manageFeeIntPer = manageFeeIntPer;
	}
	
	public Double getManageFeeIntPer() {
		return this.manageFeeIntPer;
	}
	public void setCupboardFeePer(Double cupboardFeePer) {
		this.cupboardFeePer = cupboardFeePer;
	}
	
	public Double getCupboardFeePer() {
		return this.cupboardFeePer;
	}
	public void setCupBoardDiscPer(Double cupBoardDiscPer) {
		this.cupBoardDiscPer = cupBoardDiscPer;
	}
	
	public Double getCupBoardDiscPer() {
		return this.cupBoardDiscPer;
	}
	public void setManageFeeCupPer(Double manageFeeCupPer) {
		this.manageFeeCupPer = manageFeeCupPer;
	}
	
	public Double getManageFeeCupPer() {
		return this.manageFeeCupPer;
	}
	public void setChgManageFeePer(Double chgManageFeePer) {
		this.chgManageFeePer = chgManageFeePer;
	}
	
	public Double getChgManageFeePer() {
		return this.chgManageFeePer;
	}
	public void setIsDefaultStatic(String isDefaultStatic) {
		this.isDefaultStatic = isDefaultStatic;
	}
	
	public String getIsDefaultStatic() {
		return this.isDefaultStatic;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setConfirmItem(String confirmItem) {
		this.confirmItem = confirmItem;
	}
	
	public String getConfirmItem() {
		return this.confirmItem;
	}
	public void setIsAddAllInfo(String isAddAllInfo) {
		this.isAddAllInfo = isAddAllInfo;
	}
	
	public String getIsAddAllInfo() {
		return this.isAddAllInfo;
	}
	public void setCtrlExpr(String ctrlExpr) {
		this.ctrlExpr = ctrlExpr;
	}
	
	public String getCtrlExpr() {
		return this.ctrlExpr;
	}
	public void setIsCalcPerf(String isCalcPerf) {
		this.isCalcPerf = isCalcPerf;
	}
	
	public String getIsCalcPerf() {
		return this.isCalcPerf;
	}
	public void setFirstPayPer(Double firstPayPer) {
		this.firstPayPer = firstPayPer;
	}
	
	public Double getFirstPayPer() {
		return this.firstPayPer;
	}

}
