package com.house.home.entity.design;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * CustPayPre信息
 */
@Entity
@Table(name = "tCustPayPre")
public class CustPayPre extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "PayType")
	private String payType;
	@Column(name = "BasePer")
	private Double basePer;
	@Column(name = "ItemPer")
	private Double itemPer;
	@Column(name = "DesignFee")
	private Double designFee;
	@Column(name = "PrePay")
	private Double prePay;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "BasePay")
	private Double basePay;
	@Column(name = "ItemPay")
	private Double itemPay;
	@Transient
	private int sumAllPay;
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public String getPayType() {
		return this.payType;
	}
	public void setBasePer(Double basePer) {
		this.basePer = basePer;
	}
	
	public Double getBasePer() {
		return this.basePer;
	}
	public void setItemPer(Double itemPer) {
		this.itemPer = itemPer;
	}
	
	public Double getItemPer() {
		return this.itemPer;
	}
	public void setDesignFee(Double designFee) {
		this.designFee = designFee;
	}
	
	public Double getDesignFee() {
		return this.designFee;
	}
	public void setPrePay(Double prePay) {
		this.prePay = prePay;
	}
	
	public Double getPrePay() {
		return this.prePay;
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
	public void setBasePay(Double basePay) {
		this.basePay = basePay;
	}
	
	public Double getBasePay() {
		return this.basePay;
	}
	public void setItemPay(Double itemPay) {
		this.itemPay = itemPay;
	}
	
	public Double getItemPay() {
		return this.itemPay;
	}

	public int getSumAllPay() {
		return  (int) Math.round(this.basePay+this.itemPay+this.designFee-this.prePay);
	}

	public void setSumAllPay(int sumAllPay) {
		this.sumAllPay = sumAllPay;
	}

}
