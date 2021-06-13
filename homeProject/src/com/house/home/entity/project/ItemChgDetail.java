package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemChgDetail信息
 */
@Entity
@Table(name = "tItemChgDetail")
public class ItemChgDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "ReqPk")
	private Integer reqPk;
	@Column(name = "FixAreaPk")
	private Integer fixAreaPk;
	@Column(name = "IntProdPk")
	private Integer intProdPk;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "Cost")
	private Double cost;
	@Column(name = "UnitPrice")
	private Double unitPrice;
	@Column(name = "BefLineAmount")
	private Double befLineAmount;
	@Column(name = "Markup")
	private Double markup;
	@Column(name = "LineAmount")
	private Double lineAmount;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "ProcessCost")
	private Double processCost;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "IsService")
	private Integer isService;
	@Column(name = "IsCommi")
	private Integer isCommi;
	@Column(name = "IsOutSet")
	private Integer isOutSet;
	@Column(name = "ItemSetNo")
	private String itemSetNo;
	
	@Transient
	private String itemDescr;
	@Transient
	private String fixAreaDescr;
	@Transient
	private String intProdDescr;
	@Transient
	private String uom;
	@Transient
	private String uomDescr;
	@Transient
	private Double preQty;
	@Transient
	private String itemType2;
	@Transient
	private String itemType2Descr;
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setReqPk(Integer reqPk) {
		this.reqPk = reqPk;
	}
	
	public Integer getReqPk() {
		return this.reqPk;
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
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
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
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public String getFixAreaDescr() {
		return fixAreaDescr;
	}

	public void setFixAreaDescr(String fixAreaDescr) {
		this.fixAreaDescr = fixAreaDescr;
	}

	public String getIntProdDescr() {
		return intProdDescr;
	}

	public void setIntProdDescr(String intProdDescr) {
		this.intProdDescr = intProdDescr;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getUomDescr() {
		return uomDescr;
	}

	public void setUomDescr(String uomDescr) {
		this.uomDescr = uomDescr;
	}

	public Double getPreQty() {
		return preQty;
	}

	public void setPreQty(Double preQty) {
		this.preQty = preQty;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getItemType2Descr() {
		return itemType2Descr;
	}

	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}

	public Integer getIsOutSet() {
		return isOutSet;
	}

	public void setIsOutSet(Integer isOutSet) {
		this.isOutSet = isOutSet;
	}

	public String getItemSetNo() {
		return itemSetNo;
	}

	public void setItemSetNo(String itemSetNo) {
		this.itemSetNo = itemSetNo;
	}

}
