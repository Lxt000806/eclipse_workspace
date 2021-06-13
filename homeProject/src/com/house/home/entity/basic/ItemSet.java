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
 * ItemSet信息
 */
@Entity
@Table(name = "tItemSet")
public class ItemSet extends BaseEntity {
	
	private static final long serialVersionUID = 1L;	
    	@Id
	@Column(name = "No")
	private String No;
	@Column(name = "Descr")
	private String descr;	
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "CustType")
	private String CustType;
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
	
	@Column(name = "IsOutSet")
	private String isOutSet;
	
	@Transient
	private String detailJson;
	@Transient
	private String unSelected;
	@Transient
	private String descr1;
	
	public String getDescr1() {
		return descr1;
	}
	public void setDescr1(String descr1) {
		this.descr1 = descr1;
	}
	public String getUnSelected() {
		return unSelected;
	}
	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	
    public String getDetailXml() {
        String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
        return xml;
    }
	
	public String getNo() {
		return No;
	}

	public void setNo(String no) {
		No = no;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCustType() {
		return CustType;
	}

	public void setCustType(String custtype) {
		CustType = custtype;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getItemType1() {
		return this.itemType1;
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
	
	public String getItemDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
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
    public String getIsOutSet() {
        return isOutSet;
    }
    public void setIsOutSet(String isOutSet) {
        this.isOutSet = isOutSet;
    }

}
