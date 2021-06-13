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
 *PrjProvide信息
 */
@Entity
@Table(name = "tPrjProvide")
public class PrjProvide extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "No")
	private String no;
	@Column(name = "Status")
	private String status;
	@Column(name = "AppCZY")
	private String appCZY;
	@Column(name = "Date")
	private Date date;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "ConfirmCZY")
	private String confirmCZY;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	@Transient
	private String appCZYDescr;
	@Transient
	private String confirmCZYDescr;
	@Transient
	private String prjproCheckDetailJson;
	@Transient
	private String statusName;
	@Transient
	private String documentNo;
	
	public String getPrjproCheckDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(prjproCheckDetailJson);
		return xml;
	}
	public void setPrjproCheckDetailJson(String prjproCheckDetailJson) {
		this.prjproCheckDetailJson = prjproCheckDetailJson;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	
}
