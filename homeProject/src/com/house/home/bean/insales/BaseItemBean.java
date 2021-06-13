package com.house.home.bean.insales;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * BaseItem信息bean
 */
public class BaseItemBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="区域名称", order=1)
	private String fixAreaPkDescr;
	@ExcelAnnotation(exportName="基础项目编号", order=2)
	private String baseItemCode;
	@ExcelAnnotation(exportName="基础项目名称", order=3)
	private String baseItemDescr;
	@ExcelAnnotation(exportName="数量", order=4)
	private Double qty;
	@ExcelAnnotation(exportName="材料描述", order=5)
	private String remark;
	@ExcelAnnotation(exportName="套外", order=6)
	private String isOutSetDescr;
	@ExcelAnnotation(exportName="错误信息", order=7)
	private String error;
	@ExcelAnnotation(exportName="算法", order=8)
	private String baseAlgorithmDescr;
	@ExcelAnnotation(exportName="必选项", order=9)
	private String isRequiredDescr;
	@ExcelAnnotation(exportName="可替换", order=10)
	private String canReplaceDescr;
	@ExcelAnnotation(exportName="数量可修改", order=11)
	private String canModiQtyDescr;
	@ExcelAnnotation(exportName="人工单价", order=12)
	private  Double unitPrice;
	@ExcelAnnotation(exportName="材料单价", order=13)
	private  Double material;
	@ExcelAnnotation(exportName="系统算量", order=14)
	private  Double autoQty;
	@ExcelAnnotation(exportName="模板pk", order=15)
	private Integer tempDtPk;
	@ExcelAnnotation(exportName="赠送项目编号", order=16)
	private Integer giftPk;
	@ExcelAnnotation(exportName="套餐包", order=17)
	private String baseItemSetNo;
	@ExcelAnnotation(exportName="主项目", order=18)
	private String isMainItem;

	public String getFixAreaPkDescr() {
		return fixAreaPkDescr;
	}
	public void setFixAreaPkDescr(String fixAreaPkDescr) {
		this.fixAreaPkDescr = fixAreaPkDescr;
	}
	public String getBaseItemCode() {
		return baseItemCode;
	}
	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}
	public String getBaseItemDescr() {
		return baseItemDescr;
	}
	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getIsOutSetDescr() {
		return isOutSetDescr;
	}
	public void setIsOutSetDescr(String isOutSetDescr) {
		this.isOutSetDescr = isOutSetDescr;
	}
	public String getBaseAlgorithmDescr() {
		return baseAlgorithmDescr;
	}
	public void setBaseAlgorithmDescr(String baseAlgorithmDescr) {
		this.baseAlgorithmDescr = baseAlgorithmDescr;
	}
	public String getIsRequiredDescr() {
		return isRequiredDescr;
	}
	public void setIsRequiredDescr(String isRequiredDescr) {
		this.isRequiredDescr = isRequiredDescr;
	}
	public String getCanReplaceDescr() {
		return canReplaceDescr;
	}
	public void setCanReplaceDescr(String canReplaceDescr) {
		this.canReplaceDescr = canReplaceDescr;
	}
	public String getCanModiQtyDescr() {
		return canModiQtyDescr;
	}
	public void setCanModiQtyDescr(String canModiQtyDescr) {
		this.canModiQtyDescr = canModiQtyDescr;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getMaterial() {
		return material;
	}
	public void setMaterial(Double material) {
		this.material = material;
	}
	public Double getAutoQty() {
		return autoQty;
	}
	public void setAutoQty(Double autoQty) {
		this.autoQty = autoQty;
	}
	public Integer getTempDtPk() {
		return tempDtPk;
	}
	public void setTempDtPk(Integer tempDtPk) {
		this.tempDtPk = tempDtPk;
	}
	public Integer getGiftPk() {
		return giftPk;
	}
	public void setGiftPk(Integer giftPk) {
		this.giftPk = giftPk;
	}
	public String getBaseItemSetNo() {
		return baseItemSetNo;
	}
	public void setBaseItemSetNo(String baseItemSetNo) {
		this.baseItemSetNo = baseItemSetNo;
	}
	public String getIsMainItem() {
		return isMainItem;
	}
	public void setIsMainItem(String isMainItem) {
		this.isMainItem = isMainItem;
	}	
	
}
