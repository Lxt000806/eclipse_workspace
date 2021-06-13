package com.house.home.entity.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

@Entity
@Table(name = "tConfItemType")
public class ItemTypeConfirm extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Code",nullable = false)
	private String code;
	@Column(name="Descr")
	private String  descr;
	@Column(name="ItemTimeCode")
	private String itemTimeCode;
	@Column(name="LastUpdate")
	private String lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="Expired")
	private String expired;
	@Column(name="DispSeq")
	private String dispSeq;
	@Column(name= "PrjItem")
	private String prjItem;
	@Column(name ="AvgSendDay")
	private Integer avgSendDay;
	@Column(name ="ItemType1")
	private String itemType1;
	@Column(name = "DispatchOrder")
	private String dispatchOrder;
	
	@Transient
	private String itemType2;
	@Transient
	private String itemType3;
	@Transient
	private String detailJson;
	
	public String getDispatchOrder() {
		return dispatchOrder;
	}
	public void setDispatchOrder(String dispatchOrder) {
		this.dispatchOrder = dispatchOrder;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public Integer getAvgSendDay() {
		return avgSendDay;
	}
	public void setAvgSendDay(Integer avgSendDay) {
		this.avgSendDay = avgSendDay;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
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

	public String getItemType3() {
		return itemType3;
	}

	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getItemTimeCode() {
		return itemTimeCode;
	}

	public void setItemTimeCode(String itemTimeCode) {
		this.itemTimeCode = itemTimeCode;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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

	public String getDispSeq() {
		return dispSeq;
	}

	public void setDispSeq(String dispSeq) {
		this.dispSeq = dispSeq;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	
	
	
}
