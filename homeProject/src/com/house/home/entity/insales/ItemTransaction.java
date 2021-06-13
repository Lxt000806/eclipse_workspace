package com.house.home.entity.insales;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name="tItemTransaction")
public class ItemTransaction extends BaseEntity {
	@Id
	@Column(name="PK")
	private Integer pk;
	@Column(name="Date")
	private Date date;
	@Column(name="ITCode")
	private String itCode;
	@Column(name="WHCode")
	private String whCode;
	@Column(name="PrefixCode")
	private String prefixCode;
	@Column(name="Document")
	private String document;
	@Column(name="RefDocument")
	private String refDocument;
	@Column(name="TrsQty")
	private Double trsQty;
	@Column(name="Qty")
	private Double qty;
	@Column(name="Remarks")
	private String remarks;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="Cost")
	private Double cost;
	@Column(name="AftAllQty")
	private Double aftAllQty;
	@Column(name="AftCost")
	private Double aftCost;
	@Transient
	private String descr;
	@Transient
	private String itemType1;
	@Transient
	private String itemType2;
	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	
	@Transient
	private String itemType3;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getPrefixCode() {
		return prefixCode;
	}
	public void setPrefixCode(String prefixCode) {
		this.prefixCode = prefixCode;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getRefDocument() {
		return refDocument;
	}
	public void setRefDocument(String refDocument) {
		this.refDocument = refDocument;
	}
	public Double getTrsQty() {
		return trsQty;
	}
	public void setTrsQty(Double trsQty) {
		this.trsQty = trsQty;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
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
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getAftAllQty() {
		return aftAllQty;
	}
	public void setAftAllQty(Double aftAllQty) {
		this.aftAllQty = aftAllQty;
	}
	public Double getAftCost() {
		return aftCost;
	}
	public void setAftCost(Double aftCost) {
		this.aftCost = aftCost;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getItemType3() {
		return itemType3;
	}
	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}
	
}
