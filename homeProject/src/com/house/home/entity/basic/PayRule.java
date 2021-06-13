package com.house.home.entity.basic;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name = "tPayRule")
public class PayRule extends BaseEntity{

	private static final long serialVersionUID = 1L;
@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "PayType")
	private String payType;
	@Column(name = "DesignFeeType")
	private String designFeeType;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "ContractFeeRepType")
	private String contractFeeRepType;
	@Transient
	private String detailJson;
	
	@Transient
	private String m_umState;
	
	
	
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getDesignFeeType() {
		return designFeeType;
	}
	public void setDesignFeeType(String designFeeType) {
		this.designFeeType = designFeeType;
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
	
	public String getContractFeeRepType() {
		return contractFeeRepType;
	}
	public void setContractFeeRepType(String contractFeeRepType) {
		this.contractFeeRepType = contractFeeRepType;
	}
	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getM_umState() {
		return m_umState;
	}
	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}

	
}
