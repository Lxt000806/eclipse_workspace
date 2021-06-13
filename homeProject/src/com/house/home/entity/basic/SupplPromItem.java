package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tSupplPromItem")
public class SupplPromItem extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private String pk;
	@Column(name = "No")
	private String no;
	@Column(name = "ItemType3")
	private String itemType3;
	@Column(name = "ItemDescr")
	private String itemDescr;
	@Column(name = "Model")
	private String model;
	@Column(name = "Uom")
	private String uom;
	@Column(name = "ItemSize")
	private String itemSize;
	@Column(name = "UnitPrice")
	private Double unitPrice;
	@Column(name = "PromPrice")
	private Double promPrice;
	@Column(name = "Cost")
	private Double cost;
	@Column(name = "PromCost")
	private Double promCost;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getItemType3() {
		return itemType3;
	}

	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getPromPrice() {
		return promPrice;
	}

	public void setPromPrice(Double promPrice) {
		this.promPrice = promPrice;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getPromCost() {
		return promCost;
	}

	public void setPromCost(Double promCost) {
		this.promCost = promCost;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getActionLog() {
		return actionLog;
	}

	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

}
