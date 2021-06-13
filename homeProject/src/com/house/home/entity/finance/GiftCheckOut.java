package com.house.home.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

@Entity
@Table(name = "tGiftCheckOut")
public class GiftCheckOut extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "No")
	private String no;
	@Column(name="DocumentNo")
	private String documentNo;
	@Column(name ="WHCode")
	private String whCode;
	@Column(name="Remarks")
	private String remarks;
	@Column (name="CheckDate")
	private Date CheckDate;
	@Column(name="AppCzy")
	private String appCzy;
	@Column(name="Date")
	private Date date;
	@Column(name="ConfirmDate")
	private Date confirmDate;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="Status")
	private String status;
	@Column(name="ConfirmCzy")
	private String confirmCzy;
	 
	
	@Transient
	private String appCzyDescr;
	@Transient
	private String confirmCzyDescr;
	@Transient
	private String whCodeDescr;
	@Transient
	private int temp;
	@Transient
	private String detailJson;
	@Transient
	private String address;
	@Transient
	private String allDetail;
	@Transient
	private Date checkDateFrom;
	@Transient 
	private Date checkDateTo;
	
	
	
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
	public String getAllDetail() {
		return allDetail;
	}
	public void setAllDetail(String allDetail) {
		this.allDetail = allDetail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGiftAppDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public String getWhCodeDescr() {
		return whCodeDescr;
	}
	public void setWhCodeDescr(String whCodeDescr) {
		this.whCodeDescr = whCodeDescr;
	}
	public String getAppCzyDescr() {
		return appCzyDescr;
	}
	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}
	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}
	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}
	public String getConfirmCzy() {
		return confirmCzy;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getCheckDate() {
		return CheckDate;
	}
	public void setCheckDate(Date checkDate) {
		CheckDate = checkDate;
	}
	public String getAppCzy() {
		return appCzy;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	
	 
	 
	 
}
