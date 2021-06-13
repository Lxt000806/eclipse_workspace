package com.house.home.entity.basic;

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
 * SendToCmpWh信息
 */
@Entity
@Table(name = "tSendToCmpWh")
public class SendToCmpWh extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "SupplCode")
	private String supplCode;
	@Column(name = "RegionCode")
	private String regionCode;
	@Column(name = "AmountFrom")
	private Double amountFrom;
	@Column(name = "AmountTo")
	private Double amountTo;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "ItemAppType")
	private String itemAppType; //领料单类型
	
	@Transient
	private String  supplDescr;
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	
	public String getSupplCode() {
		return this.supplCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	public String getRegionCode() {
		return this.regionCode;
	}
	public void setAmountFrom(Double amountFrom) {
		this.amountFrom = amountFrom;
	}
	
	public Double getAmountFrom() {
		return this.amountFrom;
	}
	public void setAmountTo(Double amountTo) {
		this.amountTo = amountTo;
	}
	
	public Double getAmountTo() {
		return this.amountTo;
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

	public String getSupplDescr() {
		return supplDescr;
	}

	public void setSupplDescr(String supplDescr) {
		this.supplDescr = supplDescr;
	}

	public String getItemAppType() {
		return itemAppType;
	}

	public void setItemAppType(String itemAppType) {
		this.itemAppType = itemAppType;
	}

}
