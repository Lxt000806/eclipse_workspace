package com.house.home.entity.insales;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name="tItemWHBal")
public class ItemWHBal extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private ItemWHBalId id;
	@Column(name = "WHCode")
	private String whCode;
	@Column(name = "ITCode")
	private String itCode;
	@Column(name = "QtyCal")
	private Double qtyCal;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "AvgCost")
	private Double avgCost;
	@Transient
	private String itemType2Descr;
	@Transient
	private String descr;
	@Transient
	private String desc1;
	@Transient
	private String zwxm;
	@Transient
	private String itemType1;
	@Transient
	private String itemType2;
	@Transient
	private Double costAmount;
	@Transient
	private String sqlCode;
	//1只显示库存预警材料
	@Transient
	private String onlyWarn;
	@Transient
	private String czybh;
	@Transient
	private int tjfs;
	@Transient
	private String department2;
	@Transient
	private String constructStatus;
	@Transient
	private String containClearItem;
	@Transient
	private String percentRequirs; //需求占比
	@Transient
	private String isClearInv;
	@Transient
	private String PurchOrderAnalyse;
	@Transient
	private String itemCodes;
	
	
	public String getItemCodes() {
		return itemCodes;
	}
	public void setItemCodes(String itemCodes) {
		this.itemCodes = itemCodes;
	}
	public String getPurchOrderAnalyse() {
		return PurchOrderAnalyse;
	}
	public void setPurchOrderAnalyse(String purchOrderAnalyse) {
		PurchOrderAnalyse = purchOrderAnalyse;
	}
	public String getIsClearInv() {
		return isClearInv;
	}
	public void setIsClearInv(String isClearInv) {
		this.isClearInv = isClearInv;
	}
	public String getContainClearItem() {
		return containClearItem;
	}
	public void setContainClearItem(String containClearItem) {
		this.containClearItem = containClearItem;
	}
	public ItemWHBalId getId() {
		return id;
	}
	public void setId(ItemWHBalId id) {
		this.id = id;
	}
	public Double getQtyCal() {
		return qtyCal;
	}
	public void setQtyCal(Double qtyCal) {
		this.qtyCal = qtyCal;
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
	public Double getAvgCost() {
		return avgCost;
	}
	public void setAvgCost(Double avgCost) {
		this.avgCost = avgCost;
	}
	public String getItemType2Descr() {
		return itemType2Descr;
	}
	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	public String getZwxm() {
		return zwxm;
	}
	public void setZwxm(String zwxm) {
		this.zwxm = zwxm;
	}
	public Double getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
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
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	public String getSqlCode() {
		return sqlCode;
	}
	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}
	public String getOnlyWarn() {
		return onlyWarn;
	}
	public void setOnlyWarn(String onlyWarn) {
		this.onlyWarn = onlyWarn;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public int getTjfs() {
		return tjfs;
	}
	public void setTjfs(int tjfs) {
		this.tjfs = tjfs;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getConstructStatus() {
		return constructStatus;
	}
	public void setConstructStatus(String constructStatus) {
		this.constructStatus = constructStatus;
	}
	public String getPercentRequirs() {
		return percentRequirs;
	}
	public void setPercentRequirs(String percentRequirs) {
		this.percentRequirs = percentRequirs;
	}


}
 class ItemWHBalId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private String whCode;
	private String itCode;
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
}
