package com.house.home.bean.project;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemChgDetail信息bean
 */
public class ItemChgDetailBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="区域名称", order=1)
	private String fixAreaPkDescr;
	@ExcelAnnotation(exportName="集成成品", order=2)
	private String intProdPkDescr;
	@ExcelAnnotation(exportName="材料编号", order=3)
	private String itemCode;
	@ExcelAnnotation(exportName="变动数量", order=4)
	private Double qty;
	@ExcelAnnotation(exportName="单价", order=5)
	private Double unitPrice;
	@ExcelAnnotation(exportName="折扣", order=6)
	private Double markup;
	@ExcelAnnotation(exportName="其他费用", order=7)
	private Double processCost;
	@ExcelAnnotation(exportName="材料描述", order=8)
	private String remarks;
	@ExcelAnnotation(exportName="套外", order=9)
	private String isOutSetDescr;
	@ExcelAnnotation(exportName="需求PK", order=10)
	private Integer reqPk;
	@ExcelAnnotation(exportName="套餐包", order=11)
	private String itemSetDescr;
	@ExcelAnnotation(exportName="套餐材料信息编号", order=12)
	private Integer custTypeItemPk;
	@ExcelAnnotation(exportName="错误信息", order=13)
	private String error;
	@ExcelAnnotation(exportName="供应商促销PK", order=14)
	private Integer supplPromItemPk;
	@ExcelAnnotation(exportName="算法编号", order=15)
	private String algorithm;
	@ExcelAnnotation(exportName="门窗Pk", order=16)
	private Integer doorPk;
	@ExcelAnnotation(exportName="空间名称", order=17)
	private String prePlanAreaDescr;
	@ExcelAnnotation(exportName="切割类型编号", order=18)
	private String cutType;
	@ExcelAnnotation(exportName="算法系数", order=19)
	private Double algorithmPer;
	@ExcelAnnotation(exportName="算法扣除数", order=20)
	private Double algorithmDeduct;
	
	public String getFixAreaPkDescr() {
		return fixAreaPkDescr;
	}
	public void setFixAreaPkDescr(String fixAreaPkDescr) {
		this.fixAreaPkDescr = fixAreaPkDescr;
	}
	public String getIntProdPkDescr() {
		return intProdPkDescr;
	}
	public void setIntProdPkDescr(String intProdPkDescr) {
		this.intProdPkDescr = intProdPkDescr;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getMarkup() {
		return markup;
	}
	public void setMarkup(Double markup) {
		this.markup = markup;
	}
	public Double getProcessCost() {
		return processCost;
	}
	public void setProcessCost(Double processCost) {
		this.processCost = processCost;
	}


	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIsOutSetDescr() {
		return isOutSetDescr;
	}
	public void setIsOutSetDescr(String isOutSetDescr) {
		this.isOutSetDescr = isOutSetDescr;
	}
	public Integer getReqPk() {
		return reqPk;
	}
	public void setReqPk(Integer reqPk) {
		this.reqPk = reqPk;
	}
	public String getItemSetDescr() {
		return itemSetDescr;
	}
	public void setItemSetDescr(String itemSetDescr) {
		this.itemSetDescr = itemSetDescr;
	}
	public Integer getCustTypeItemPk() {
		return custTypeItemPk;
	}
	public void setCustTypeItemPk(Integer custTypeItemPk) {
		this.custTypeItemPk = custTypeItemPk;
	}
	public Integer getSupplPromItemPk() {
		return supplPromItemPk;
	}
	public void setSupplPromItemPk(Integer supplPromItemPk) {
		this.supplPromItemPk = supplPromItemPk;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public String getCutType() {
		return cutType;
	}
	public void setCutType(String cutType) {
		this.cutType = cutType;
	}
	public Integer getDoorPk() {
		return doorPk;
	}
	public void setDoorPk(Integer doorPk) {
		this.doorPk = doorPk;
	}
	public String getPrePlanAreaDescr() {
		return prePlanAreaDescr;
	}
	public void setPrePlanAreaDescr(String prePlanAreaDescr) {
		this.prePlanAreaDescr = prePlanAreaDescr;
	}
	
	public Double getAlgorithmPer() {
		return algorithmPer;
	}
	public void setAlgorithmPer(Double algorithmPer) {
		this.algorithmPer = algorithmPer;
	}
	public Double getAlgorithmDeduct() {
		return algorithmDeduct;
	}
	public void setAlgorithmDeduct(Double algorithmDeduct) {
		this.algorithmDeduct = algorithmDeduct;
	}
	
}
