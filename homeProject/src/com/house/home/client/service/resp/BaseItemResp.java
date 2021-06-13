package com.house.home.client.service.resp;

/**
 * APP_基础项目
 * @author created by zb
 * @date   2019-3-4--上午11:09:22
 */
public class BaseItemResp extends BaseResponse {
	private String code;
	private Integer dispSeq;
	private String baseItemType1;
	private String baseItemType2;
	private String descr; //基装项目名称
	private String categoryDescr; //项目类型
	private String category; //项目类型
	private Double marketPrice; //市场价
	private Double offerPri; //人工单价
	private Double material; //材料单价
	private String uom; //单位
	private String remark;
	private Double cost; //成本
	private String prjCtrlType; //发包方式
	private Double offerCtrl; //人工发包
	private Double materialCtrl; //材料发包
	private String isCalMangeFee; //是否计算管理费
	
	private Integer pk; //reqPk
	private String baseItemCode; //code
	private String baseItemDescr; //descr
	private Double unitPrice; //人工单价
	private Double lineAmount; //总价
	private String fixAreaDescr2; //区域2名称
	private Integer fixAreaPk; //区域编码
	private Double qty; //数量
	private String isOutSet; //是否套餐外 0:不是
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getBaseItemType1() {
		return baseItemType1;
	}
	public void setBaseItemType1(String baseItemType1) {
		this.baseItemType1 = baseItemType1;
	}
	public String getBaseItemType2() {
		return baseItemType2;
	}
	public void setBaseItemType2(String baseItemType2) {
		this.baseItemType2 = baseItemType2;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCategoryDescr() {
		return categoryDescr;
	}
	public void setCategoryDescr(String categoryDescr) {
		this.categoryDescr = categoryDescr;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Double getOfferPri() {
		return offerPri;
	}
	public void setOfferPri(Double offerPri) {
		this.offerPri = offerPri;
	}
	public Double getMaterial() {
		return material;
	}
	public void setMaterial(Double material) {
		this.material = material;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public String getPrjCtrlType() {
		return prjCtrlType;
	}
	public void setPrjCtrlType(String prjCtrlType) {
		this.prjCtrlType = prjCtrlType;
	}
	public Double getOfferCtrl() {
		return offerCtrl;
	}
	public void setOfferCtrl(Double offerCtrl) {
		this.offerCtrl = offerCtrl;
	}
	public Double getMaterialCtrl() {
		return materialCtrl;
	}
	public void setMaterialCtrl(Double materialCtrl) {
		this.materialCtrl = materialCtrl;
	}
	public String getIsCalMangeFee() {
		return isCalMangeFee;
	}
	public void setIsCalMangeFee(String isCalMangeFee) {
		this.isCalMangeFee = isCalMangeFee;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getBaseItemCode() {
		return baseItemCode;
	}
	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getLineAmount() {
		return lineAmount;
	}
	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}
	public String getBaseItemDescr() {
		return baseItemDescr;
	}
	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
	}
	public String getFixAreaDescr2() {
		return fixAreaDescr2;
	}
	public void setFixAreaDescr2(String fixAreaDescr2) {
		this.fixAreaDescr2 = fixAreaDescr2;
	}
	public Integer getFixAreaPk() {
		return fixAreaPk;
	}
	public void setFixAreaPk(Integer fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getIsOutSet() {
		return isOutSet;
	}
	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}
}
