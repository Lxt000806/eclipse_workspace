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
@Table(name = "tReturnCarryRule")
public class ReturnCarryRule extends BaseEntity {
	
	private static final long serialVersionUID = 1L;	
    	@Id
	@Column(name = "No")
	private String No;
	@Column(name = "BeginValue")
	private float beginValue;
	@Column(name = "EndValue")
	private float endValue;	
	@Column(name = "PriceType")
	private String priceType;
	@Column(name = "Price")
	private float price;
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
	
	@Column(name = "CardAmount")
	private float cardAmount;	
	@Column(name = "IncValue")
	private float incValue;
	@Column(name = "CalType")
	private String calType;
	
	
	@Transient
	private String detailJson;
	@Transient
	private String priceTypeDescr;
	@Transient
	private String No1;
	@Transient
	private String returnCarryRuleDetailJson;
	
	public String getNo() {
		return No;
	}

	public void setNo(String no) {
		No = no;
	}

	public float getBeginValue() {
		return beginValue;
	}

	public void setBeginValue(float beginValue) {
		this.beginValue = beginValue;
	}

	public float getEndValue() {
		return endValue;
	}

	public void setEndValue(float endValue) {
		this.endValue = endValue;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public float getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(float cardAmount) {
		this.cardAmount = cardAmount;
	}

	public float getIncValue() {
		return incValue;
	}

	public void setIncValue(float incValue) {
		this.incValue = incValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPriceTypeDescr() {
		return priceTypeDescr;
	}

	public void setPriceTypeDescr(String priceTypeDescr) {
		this.priceTypeDescr = priceTypeDescr;
	}

	public String getNo1() {
		return No1;
	}
	
	public void setNo1(String no1) {
		No1 = no1;
	}
	public String getCalType() {
		return calType;
	}
	public void setCalType(String calType) {
		this.calType = calType;
	}

	public String getReturnCarryRuleDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(returnCarryRuleDetailJson);
    	return xml;
	}

	public void setReturnCarryRuleDetailJson(String returnCarryRuleDetailJson) {
		this.returnCarryRuleDetailJson = returnCarryRuleDetailJson;
	}
	
}
