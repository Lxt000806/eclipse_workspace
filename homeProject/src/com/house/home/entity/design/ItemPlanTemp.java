package com.house.home.entity.design;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * ItemPlan信息
 */
@Entity
@Table(name = "tItemPlanTemp")
public class ItemPlanTemp extends BaseEntity{
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "No",nullable = false)
	private String no;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String mobile1;
	@Transient
	private String layout;
	@Transient
	private Integer area;
	@Transient
	private double qty;
	@Transient
	private String itemCode;
	@Transient
	private String itemDescr;
	@Transient
	private String detailJson;
	@Transient
	private String custCode;
	@Transient
	private String custType;
	
	
	public String getItemPlanTempDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
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
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	
}
