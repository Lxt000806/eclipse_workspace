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
 * SendTime信息
 */
@Entity
@Table(name = "tSendTime")
public class SendTime extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "ProductType")
	private String productType;
	@Column(name = "IsSetItem")
	private String isSetItem;
	@Column(name = "SendDay")
	private Integer sendDay;
	@Column(name = "Prior")
	private Integer prior;
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
	@Transient
	String itemType2;
	@Transient
	String itemDesc;
	@Transient
	String itemType2Descr;
	@Transient
	String sendTimeDetailJson;
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getProductType() {
		return this.productType;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	
	public String getIsSetItem() {
		return this.isSetItem;
	}
	public void setSendDay(Integer sendDay) {
		this.sendDay = sendDay;
	}
	
	public Integer getSendDay() {
		return this.sendDay;
	}
	public void setPrior(Integer prior) {
		this.prior = prior;
	}
	
	public Integer getPrior() {
		return this.prior;
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

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemType2Descr() {
		return itemType2Descr;
	}

	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}

	public String getSendTimeDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(sendTimeDetailJson);
    	return xml;
	}

	public void setSendTimeDetailJson(String sendTimeDetailJson) {
		this.sendTimeDetailJson = sendTimeDetailJson;
	}

}
