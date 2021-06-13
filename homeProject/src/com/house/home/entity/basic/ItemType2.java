package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemType2信息
 */
@Entity
@Table(name = "tItemType2")
public class ItemType2 extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name= "Code")
	private String code;
	@Column(name= "Descr")
	private String descr;
	@Column(name= "ItemType1")
	private String itemType1;
	@Column(name= "DispSeq")
	private Integer dispSeq;
	@Column(name= "Length")
	private Integer length;
	@Column(name= "LastNumber")
	private Integer lastNumber;
	@Column(name= "LastUpdate")
	private Date lastUpdate;
	@Column(name= "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name= "Expired")
	private String expired;
	@Column(name= "ActionLog")
	private String actionLog;
	@Column(name= "ArriveDay")
	private Integer arriveDay;
	@Column(name= "OtherCostPer_Sale")
	private Double otherCostPer_Sale;
	@Column(name= "OtherCostPer_Cost")
	private Double otherCostPer_Cost;
	@Column(name= "ItemType12")
	private String itemType12;
	@Column(name="AppPrjItem")
	private String appPrjItem;
	@Column(name="materWorkType2")
	private String materWorkType2;
	@Column(name="OrderType")
	private String orderType;
	
	@Transient
	private String workerCode;
	@Transient
	private String itemType1Descr;
	@Transient
	private String itemType12Descr;
	@Transient
	private String workType1;
	@Transient
	private String workType2;
	@Transient
	private String prjDescr;
	@Transient
	private Integer custWkPk;
	@Transient
	private String workType12;
	
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public String getPrjDescr() {
		return prjDescr;
	}
	public void setPrjDescr(String prjDescr) {
		this.prjDescr = prjDescr;
	}
	public String getMaterWorkType2() {
		return materWorkType2;
	}
	public void setMaterWorkType2(String materWorkType2) {
		this.materWorkType2 = materWorkType2;
	}
	
	public String getWorkType2() {
		return workType2;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	public String getWorkType1() {
		return workType1;
	}
	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
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
	public String getAppPrjItem() {
		return appPrjItem;
	}
	public void setAppPrjItem(String appPrjItem) {
		this.appPrjItem = appPrjItem;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public void setItemType1(String itemType1Descr) {
		this.itemType1 = itemType1Descr;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getLastNumber() {
		return lastNumber;
	}
	public void setLastNumber(Integer lastNumber) {
		this.lastNumber = lastNumber;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
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
	public Integer getArriveDay() {
		return arriveDay;
	}
	public void setArriveDay(Integer arriveDay) {
		this.arriveDay = arriveDay;
	}
	public Double getOtherCostPer_Sale() {
		return otherCostPer_Sale;
	}
	public void setOtherCostPer_Sale(Double otherCostPer_Sale) {
		this.otherCostPer_Sale = otherCostPer_Sale;
	}
	public Double getOtherCostPer_Cost() {
		return otherCostPer_Cost;
	}
	public void setOtherCostPer_Cost(Double otherCostPer_Cost) {
		this.otherCostPer_Cost = otherCostPer_Cost;
	}
	public String getItemType12() {
		return itemType12;
	}
	public void setItemType12(String itemType12) {
		this.itemType12 = itemType12;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
