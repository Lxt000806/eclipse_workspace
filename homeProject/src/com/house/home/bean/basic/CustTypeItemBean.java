package com.house.home.bean.basic;

import com.house.framework.commons.excel.ExcelAnnotation;

public class CustTypeItemBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="PK", order=1)
	private String pk;
	@ExcelAnnotation(exportName="客户类型", order=2)
	private String custType;
	@ExcelAnnotation(exportName="材料编号", order=3)
	private String itemCode;
	@ExcelAnnotation(exportName="升级价", order=4)
	private String price;
	@ExcelAnnotation(exportName="升级结算价", order=5)
	private String projectCost;
	@ExcelAnnotation(exportName="优惠额度计算方式", order=6)
	private String discAmtCalcType;
	@ExcelAnnotation(exportName="适用装修区域", order=7)
	private String fixAreaType;
	@ExcelAnnotation(exportName="描述", order=8)
	private String remark;
	
	
	
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProjectCost() {
		return projectCost;
	}
	public void setProjectCost(String projectCost) {
		this.projectCost = projectCost;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDiscAmtCalcType() {
		return discAmtCalcType;
	}
	public void setDiscAmtCalcType(String discAmtCalcType) {
		this.discAmtCalcType = discAmtCalcType;
	}
	public String getFixAreaType() {
		return fixAreaType;
	}
	public void setFixAreaType(String fixAreaType) {
		this.fixAreaType = fixAreaType;
	}
	
	
}
