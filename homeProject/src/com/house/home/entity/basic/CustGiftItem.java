package com.house.home.entity.basic;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tCustGiftItem")
public class CustGiftItem extends BaseEntity{

	private static final long serialVersionUID = 1L;
@Id
	@Column(name = "PK")
	private Integer pK;
	@Column(name = "CustGiftPK")
	private Integer custGiftPK;
	@Column(name = "FixAreaPK")
	private Integer fixAreaPK;
	@Column(name = "BaseItemCode")
	private String baseItemCode;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "Markup")
	private Double markup;
	@Column(name = "SaleAmount")
	private Double saleAmount;
	@Column(name = "TotalCost")
	private Double totalCost;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String custCode;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Integer getpK() {
		return pK;
	}
	public void setpK(Integer pK) {
		this.pK = pK;
	}
	public Integer getCustGiftPK() {
		return custGiftPK;
	}
	public void setCustGiftPK(Integer custGiftPK) {
		this.custGiftPK = custGiftPK;
	}
	public Integer getFixAreaPK() {
		return fixAreaPK;
	}
	public void setFixAreaPK(Integer fixAreaPK) {
		this.fixAreaPK = fixAreaPK;
	}
	public String getBaseItemCode() {
		return baseItemCode;
	}
	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
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
	public Double getMarkup() {
		return markup;
	}
	public void setMarkup(Double markup) {
		this.markup = markup;
	}
	public Double getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(Double saleAmount) {
		this.saleAmount = saleAmount;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
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

	

}
