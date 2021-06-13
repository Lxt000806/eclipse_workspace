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
 * CustLoan信息
 */
@Entity
@Table(name = "tCustLoan")
public class CustLoan extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "AgreeDate")
	private Date agreeDate;
	@Column(name = "Bank")
	private String bank;
	@Column(name = "FollowRemark")
	private String followRemark;
	@Column(name = "SignRemark")
	private String signRemark;
	@Column(name = "ConfuseRemark")
	private String confuseRemark;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "FirstAmount")
	private Double firstAmount;
	@Column(name = "FirstDate")
	private Date firstDate;
	@Column(name = "SecondAmount")
	private Double secondAmount;
	@Column(name = "SecondDate")
	private Date secondDate;
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
	private String custDescr ;
	@Transient
	private String address ;
	@Transient
	private String statusDescr;
	@Transient
	private String detailJson;
	
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setAgreeDate(Date agreeDate) {
		this.agreeDate = agreeDate;
	}
	
	public Date getAgreeDate() {
		return this.agreeDate;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getBank() {
		return this.bank;
	}
	public void setFollowRemark(String followRemark) {
		this.followRemark = followRemark;
	}
	
	public String getFollowRemark() {
		return this.followRemark;
	}
	public void setSignRemark(String signRemark) {
		this.signRemark = signRemark;
	}
	
	public String getSignRemark() {
		return this.signRemark;
	}
	public void setConfuseRemark(String confuseRemark) {
		this.confuseRemark = confuseRemark;
	}
	
	public String getConfuseRemark() {
		return this.confuseRemark;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setFirstAmount(Double firstAmount) {
		this.firstAmount = firstAmount;
	}
	
	public Double getFirstAmount() {
		return this.firstAmount;
	}
	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}
	
	public Date getFirstDate() {
		return this.firstDate;
	}
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}
	
	public Double getSecondAmount() {
		return this.secondAmount;
	}
	public void setSecondDate(Date secondDate) {
		this.secondDate = secondDate;
	}
	
	public Date getSecondDate() {
		return this.secondDate;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
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

	public String getCustDescr() {
		return custDescr;
	}

	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatusDescr() {
		return statusDescr;
	}

	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}	

}
