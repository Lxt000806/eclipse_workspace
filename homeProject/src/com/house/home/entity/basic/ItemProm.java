package com.house.home.entity.basic;

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
 * ItemProm信息
 */
@Entity
@Table(name = "tItemProm")
public class ItemProm extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "PromPrice")
	private Double promPrice;
	@Column(name = "PromCost")
	private Double promCost;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	@Transient 
	String itemType1;
	@Transient 
	String itemDescr;
	@Transient 
	String SupplCode;
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemCode() {
		return this.itemCode;
	}
	public void setPromPrice(Double promPrice) {
		this.promPrice = promPrice;
	}
	
	public Double getPromPrice() {
		return this.promPrice;
	}
	public void setPromCost(Double promCost) {
		this.promCost = promCost;
	}
	
	public Double getPromCost() {
		return this.promCost;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getBeginDate() {
		return this.beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
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

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public String getSupplCode() {
		return SupplCode;
	}

	public void setSupplCode(String supplCode) {
		SupplCode = supplCode;
	}
	
}
