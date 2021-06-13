package com.house.home.entity.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * WHCheckOut信息
 */
@Entity
@Table(name = "tWHCheckOut")
public class WHCheckOut extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "No")
	private String no;
	@Column(name = "Status")
	private String status;
	@Column(name = "DocumentNo")
	private String documentNo;
	@Column(name = "CheckDate")
	private Date checkDate;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "AppCZY")
	private String appCZY;
	@Column(name = "Date")
	private Date date;
	@Column(name = "ConfirmCZY")
	private String confirmCZY;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "WHCode")
	private String whCode;
    @Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String whDescr;
	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	@Transient
	private Date checkDateFrom;
	@Transient
	private Date checkDateTo;
	@Transient
	private String itemAppsendDetailJson;
	@Transient
	private String salesInvoiceDetailJson;
	@Transient
	private String appCZYDescr;
	@Transient
	private String confirmCZYDescr;
	@Transient
	private String iaNo;
	@Transient
	private String address;
	@Transient
	private String itemAppSendNo;
	@Transient
	private String itemType1;
	@Transient
	private String czybh;
	
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
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getConfirmCZY() {
		return confirmCZY;
	}
	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
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

	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public Date getCheckDateFrom() {
		return checkDateFrom;
	}
	public void setCheckDateFrom(Date checkDateFrom) {
		this.checkDateFrom = checkDateFrom;
	}
	public Date getCheckDateTo() {
		return checkDateTo;
	}
	public void setCheckDateTo(Date checkDateTo) {
		this.checkDateTo = checkDateTo;
	}
	public String getWhDescr() {
		return whDescr;
	}
	public void setWhDescr(String whDescr) {
		this.whDescr = whDescr;
	}
	public String getAppCZYDescr() {
		return appCZYDescr;
	}
	public void setAppCZYDescr(String appCZYDescr) {
		this.appCZYDescr = appCZYDescr;
	}
	public String getConfirmCZYDescr() {
		return confirmCZYDescr;
	}
	public void setConfirmCZYDescr(String confirmCZYDescr) {
		this.confirmCZYDescr = confirmCZYDescr;
	}

	public String getIaNo() {
		return iaNo;
	}

	public void setIaNo(String iaNo) {
		this.iaNo = iaNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getItemAppSendNo() {
		return itemAppSendNo;
	}

	public void setItemAppSendNo(String itemAppSendNo) {
		this.itemAppSendNo = itemAppSendNo;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
	

}
