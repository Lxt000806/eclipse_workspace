package com.house.home.entity.insales;

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
 * SalesInvoiceDetail信息
 */
@Entity
@Table(name = "tSalesInvoiceDetail")
public class SalesInvoiceDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SINo")
	private String sino;
	@Column(name = "ITCode")
	private String itcode;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "BCost")
	private Double bcost;
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
	@Column(name = "SaleQty")
	private Double saleQty;
	
	@Transient
	private String itemType1;
	@Transient
	private String puno;
	@Transient
	private Integer XZKZ;//为1：出现选择按钮，0：无选择按钮
	@Transient
	private String itDescr;
	@Transient
	private Double marketPrice;//市场价
	
	
	public Integer getXZKZ() {
		return XZKZ;
	}

	public void setXZKZ(Integer xZKZ) {
		XZKZ = xZKZ;
	}

	public String getPuno() {
		return puno;
	}

	public void setPuno(String puno) {
		this.puno = puno;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public Integer getPk() {
		return this.pk;
	}
	public void setSino(String sino) {
		this.sino = sino;
	}
	
	public String getSino() {
		return this.sino;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	
	public String getItcode() {
		return this.itcode;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
	}
	public void setBcost(Double bcost) {
		this.bcost = bcost;
	}
	
	public Double getBcost() {
		return this.bcost;
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
	public void setSaleQty(Double saleQty) {
		this.saleQty = saleQty;
	}
	
	public Double getSaleQty() {
		return this.saleQty;
	}

	public String getItDescr() {
		return itDescr;
	}

	public void setItDescr(String itDescr) {
		this.itDescr = itDescr;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

}
