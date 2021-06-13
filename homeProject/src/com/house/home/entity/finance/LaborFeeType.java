package com.house.home.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tLaborFeeType")
public class LaborFeeType extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "IsCalCost")
	private String isCalCost;
	@Column(name = "IsHaveSendNo")
	private String isHaveSendNo;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "ItemType12")
	private String itemType12;
	@Column(name = "FeeBigType")
	private String feeBigType;
	
	@Transient
	private String isCalCostDescr;
	@Transient
	private String isHaveSendNoDescr;
	@Transient
	private String itemType1Descr;
	@Transient
	private String itemType12Descr;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getIsCalCost() {
		return isCalCost;
	}
	public void setIsCalCost(String isCalCost) {
		this.isCalCost = isCalCost;
	}
	public String getIsHaveSendNo() {
		return isHaveSendNo;
	}
	public void setIsHaveSendNo(String isHaveSendNo) {
		this.isHaveSendNo = isHaveSendNo;
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
	public String getItemType12() {
		return itemType12;
	}
	public void setItemType12(String itemType12) {
		this.itemType12 = itemType12;
	}
	public String getIsCalCostDescr() {
		return isCalCostDescr;
	}
	public void setIsCalCostDescr(String isCalCostDescr) {
		this.isCalCostDescr = isCalCostDescr;
	}
	public String getIsHaveSendNoDescr() {
		return isHaveSendNoDescr;
	}
	public void setIsHaveSendNoDescr(String isHaveSendNoDescr) {
		this.isHaveSendNoDescr = isHaveSendNoDescr;
	}
	public String getItemType1Descr() {
		return itemType1Descr;
	}
	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}
	public String getItemType12Descr() {
		return itemType12Descr;
	}
	public void setItemType12Descr(String itemType12Descr) {
		this.itemType12Descr = itemType12Descr;
	}
	public String getFeeBigType() {
		return feeBigType;
	}
	public void setFeeBigType(String feeBigType) {
		this.feeBigType = feeBigType;
	}
	

}
