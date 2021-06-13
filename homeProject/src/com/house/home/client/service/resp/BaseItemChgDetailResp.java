package com.house.home.client.service.resp;

import java.util.Date;

/**
 * 基础增减明细
 * @author created by zb
 * @date   2019-3-13--上午9:33:44
 */
public class BaseItemChgDetailResp extends BaseResponse {
	private Integer pk;
	private String no;
	private Integer reqPk;
	private Integer fixAreaPk;
	private String baseItemCode;
	private Double qty;
	private Double cost;
	private Double unitPrice;
	private Double lineAmount;
	private String remarks;
	private Date lastUpdate;
	private String lastUpdatedBy;
	private String expired;
	private String actionLog;
	private Double material;
	private Integer dispSeq;
	private String isOutSet;
	private String prjCtrlType;
	private Double offerCtrl;
	private Double materialCtrl;

	private String custCode;
	private String fixAreaDescr;
	private String baseItemDescr;
	private String uom;
	private String isCalMangeFee;
	private String category;
	private Double preQty; //基装需求数量
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getReqPk() {
		return reqPk;
	}
	public void setReqPk(Integer reqPk) {
		this.reqPk = reqPk;
	}
	public Integer getFixAreaPk() {
		return fixAreaPk;
	}
	public void setFixAreaPk(Integer fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}
	public String getBaseItemCode() {
		return baseItemCode;
	}
	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public Double getMaterial() {
		return material;
	}
	public void setMaterial(Double material) {
		this.material = material;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getFixAreaDescr() {
		return fixAreaDescr;
	}
	public void setFixAreaDescr(String fixAreaDescr) {
		this.fixAreaDescr = fixAreaDescr;
	}
	public String getBaseItemDescr() {
		return baseItemDescr;
	}
	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getIsCalMangeFee() {
		return isCalMangeFee;
	}
	public void setIsCalMangeFee(String isCalMangeFee) {
		this.isCalMangeFee = isCalMangeFee;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getIsOutSet() {
		return isOutSet;
	}
	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
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
	public Double getPreQty() {
		return preQty;
	}
	public void setPreQty(Double preQty) {
		this.preQty = preQty;
	}
	
}
