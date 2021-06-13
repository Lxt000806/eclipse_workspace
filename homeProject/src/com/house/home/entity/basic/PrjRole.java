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
 * tPrjRole信息
 */
@Entity
@Table(name = "tPrjRole")
public class PrjRole extends BaseEntity {

	private static final long serialVersionUID = 1L;
    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Transient
	private String prjRoleJson;
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
	private String descr1;
	
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

	public String getPrjRoleJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(prjRoleJson);
    	return xml;
	}
	public void setPrjRoleJson(String prjRoleJson) {
		this.prjRoleJson = prjRoleJson;
	}
	public String getSalesInvoiceDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(salesInvoiceDetailJson);
    	return xml;		
	}

	public void setSalesInvoiceDetailJson(String salesInvoiceDetailJson) {
		this.salesInvoiceDetailJson = salesInvoiceDetailJson;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
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
	public String getDetailJson1() {
		return detailJson1;
	}
	public void setDetailJson1(String detailJson1) {
		this.detailJson1 = detailJson1;
	}
	public String getDescr1() {
		return descr1;
	}
	public void setDescr1(String descr1) {
		this.descr1 = descr1;
	}

}
