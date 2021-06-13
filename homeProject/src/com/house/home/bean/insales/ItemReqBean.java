package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemReq信息bean
 */
public class ItemReqBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="fixAreaPk", order=3)
	private Integer fixAreaPk;
	@ExcelAnnotation(exportName="intProdPk", order=4)
	private Integer intProdPk;
	@ExcelAnnotation(exportName="itemCode", order=5)
	private String itemCode;
	@ExcelAnnotation(exportName="itemType1", order=6)
	private String itemType1;
	@ExcelAnnotation(exportName="qty", order=7)
	private Double qty;
	@ExcelAnnotation(exportName="sendQty", order=8)
	private Double sendQty;
	@ExcelAnnotation(exportName="cost", order=9)
	private Double cost;
	@ExcelAnnotation(exportName="unitPrice", order=10)
	private Double unitPrice;
	@ExcelAnnotation(exportName="befLineAmount", order=11)
	private Double befLineAmount;
	@ExcelAnnotation(exportName="markup", order=12)
	private Double markup;
	@ExcelAnnotation(exportName="lineAmount", order=13)
	private Double lineAmount;
	@ExcelAnnotation(exportName="remark", order=14)
	private String remark;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=15)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=16)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=17)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=18)
	private String actionLog;
	@ExcelAnnotation(exportName="processCost", order=19)
	private Double processCost;
	@ExcelAnnotation(exportName="dispSeq", order=20)
	private Integer dispSeq;
	@ExcelAnnotation(exportName="isService", order=21)
	private Integer isService;
	@ExcelAnnotation(exportName="isCommi", order=22)
	private Integer isCommi;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setFixAreaPk(Integer fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}
	
	public Integer getFixAreaPk() {
		return this.fixAreaPk;
	}
	public void setIntProdPk(Integer intProdPk) {
		this.intProdPk = intProdPk;
	}
	
	public Integer getIntProdPk() {
		return this.intProdPk;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemCode() {
		return this.itemCode;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
	}
	public void setSendQty(Double sendQty) {
		this.sendQty = sendQty;
	}
	
	public Double getSendQty() {
		return this.sendQty;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Double getCost() {
		return this.cost;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Double getUnitPrice() {
		return this.unitPrice;
	}
	public void setBefLineAmount(Double befLineAmount) {
		this.befLineAmount = befLineAmount;
	}
	
	public Double getBefLineAmount() {
		return this.befLineAmount;
	}
	public void setMarkup(Double markup) {
		this.markup = markup;
	}
	
	public Double getMarkup() {
		return this.markup;
	}
	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}
	
	public Double getLineAmount() {
		return this.lineAmount;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setProcessCost(Double processCost) {
		this.processCost = processCost;
	}
	
	public Double getProcessCost() {
		return this.processCost;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setIsService(Integer isService) {
		this.isService = isService;
	}
	
	public Integer getIsService() {
		return this.isService;
	}
	public void setIsCommi(Integer isCommi) {
		this.isCommi = isCommi;
	}
	
	public Integer getIsCommi() {
		return this.isCommi;
	}

}
