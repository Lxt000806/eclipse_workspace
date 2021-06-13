package com.house.home.entity.driver;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name="tItemSendBatch")
public class ItemSendBatch extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="No",nullable=false)
	private String no;
	@Column(name="AppCZY")
	private String appCZY;
	@Column(name="Date")
	private Date date;
	@Column(name="DriverCode")
	private String driverCode;
	@Column(name="Status")
	private String status;
	@Column(name="Remarks")
	private String remarks;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="FollowMan")
	private String followMan;
	@Transient
	private String driverCodeDescr;
	@Transient
	private String custCode;
	@Transient
	private String requestPage;
	@Transient
	private String appCZYDescr;
	@Transient
	private String itemAppSendJson;
	@Transient
	private String itemReturnJson;
	@Transient
	private String itemAppSendBackJson;
	@Transient
	private String followManDescr;
	@Transient
	private String costRight;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getAppCZY() {
		return appCZY;
	}
	public void setAppCZY(String appCZY) {
		this.appCZY = appCZY;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getDriverCodeDescr() {
		return driverCodeDescr;
	}
	public void setDriverCodeDescr(String driverCodeDescr) {
		this.driverCodeDescr = driverCodeDescr;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getRequestPage() {
		return requestPage;
	}
	public void setRequestPage(String requestPage) {
		this.requestPage = requestPage;
	}
	public String getAppCZYDescr() {
		return appCZYDescr;
	}
	public void setAppCZYDescr(String appCZYDescr) {
		this.appCZYDescr = appCZYDescr;
	}
	public String getItemAppSendJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(itemAppSendJson);
    	return xml;
	}
	public void setItemAppSendJson(String itemAppSendJson) {
		this.itemAppSendJson = itemAppSendJson;
	}
	public String getItemReturnJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(itemReturnJson);
    	return xml;
	}
	public void setItemReturnJson(String itemReturnJson) {
		this.itemReturnJson = itemReturnJson;
	}
	public String getItemAppSendBackJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(itemAppSendBackJson);
    	return xml;
	}
	public void setItemAppSendBackJson(String itemAppSendBackJson) {
		this.itemAppSendBackJson = itemAppSendBackJson;
	}
	public String getFollowMan() {
		return followMan;
	}
	public void setFollowMan(String followMan) {
		this.followMan = followMan;
	}
	public String getFollowManDescr() {
		return followManDescr;
	}
	public void setFollowManDescr(String followManDescr) {
		this.followManDescr = followManDescr;
	}
	public String getCostRight() {
		return costRight;
	}
	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}
	
}
