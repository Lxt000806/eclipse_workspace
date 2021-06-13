package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * tCarryRule信息
 */
@Entity
@Table(name = "tCarryRule")
public class CarryRule extends BaseEntity {
	
	private static final long serialVersionUID = 1L;	
    	@Id
	@Column(name = "No")
	private String No;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "ItemType2")
	private String itemType2;
	@Column(name = "CarryType")
	private String carryType;
	@Column(name = "DistanceType")
	private String distanceType;
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
	@Column(name="CalType")
	private String CalType;
	@Column(name ="SendType")
	private String sendType;
	@Column(name ="SupplCode")
	private String supplCode;
	
	@Column(name = "WHCode")
	private String wHCode;
	
	@Transient
	private String itemAppsendDetailJson;
	@Transient
	private String salesInvoiceDetailJson;
	@Transient
	private String detailJson;
	@Transient
	private String unSelected;
	
	@Transient
	private String fieldJson; 
	
	@Transient
	private String detailJson1;
	
	@Transient
	private String No1;
	
	@Transient
	private String supplDescr;

	public String getNo() {
		return No;
	}

	public void setNo(String no) {
		No = no;
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

	public String getCarryType() {
		return carryType;
	}

	public void setCarryType(String carryType) {
		this.carryType = carryType;
	}

	public String getDistanceType() {
		return distanceType;
	}

	public void setDistanceType(String distanceType) {
		this.distanceType = distanceType;
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

	public String getDetailJson() {
		return detailJson;
	}
	
	public String getcarryRuleFloorXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getNo1() {
		return No1;
	}

	public void setNo1(String no1) {
		No1 = no1;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDetailJson1() {
		return detailJson1;
	}
	
	public String getcarryRuleItemXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson1);
		return xml;
	}
	public void setDetailJson1(String detailJson1) {
		this.detailJson1 = detailJson1;
	}
	
	
    public String getItemAppsendDetailJson() {
    	String xml = XmlConverUtil.jsonToXmlNoHead(itemAppsendDetailJson);
    	return xml;
	}

	public void setItemAppsendDetailJson(String itemAppsendDetailJson) {
		this.itemAppsendDetailJson = itemAppsendDetailJson;
	}

	public String getSalesInvoiceDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(salesInvoiceDetailJson);
    	return xml;		
	}

	public void setSalesInvoiceDetailJson(String salesInvoiceDetailJson) {
		this.salesInvoiceDetailJson = salesInvoiceDetailJson;
	}

	public String getUnSelected() {
		return unSelected;
	}

	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}

	public String getFieldJson() {
		return fieldJson;
	}

	public void setFieldJson(String fieldJson) {
		this.fieldJson = fieldJson;
	}

	public String getCalType() {
		return CalType;
	}

	public void setCalType(String calType) {
		CalType = calType;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getSupplCode() {
		return supplCode;
	}

	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}

	public String getSupplDescr() {
		return supplDescr;
	}

	public void setSupplDescr(String supplDescr) {
		this.supplDescr = supplDescr;
	}

    public String getwHCode() {
        return wHCode;
    }

    public void setwHCode(String wHCode) {
        this.wHCode = wHCode;
    }
	
	
}
