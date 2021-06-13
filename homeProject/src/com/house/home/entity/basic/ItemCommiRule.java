package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemCommiRule信息
 */
@Entity
@Table(name = "tItemCommiRule")
public class ItemCommiRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCheckDateFrom")
	private Date custCheckDateFrom;
	@Column(name = "CustCheckDateTo")
	private Date custCheckDateTo;
	@Column(name = "IsAddAllInfo")
	private String isAddAllInfo;
	@Column(name = "MainCommiPer")
	private Double mainCommiPer;
	@Column(name = "ServCommiPer")
	private Double servCommiPer;
	@Column(name = "SoftCommiPer")
	private Double softCommiPer;
	@Column(name = "CurtainCommiPer")
	private Double curtainCommiPer;
	@Column(name = "FurnitureCommiPer")
	private Double furnitureCommiPer;
	@Column(name = "MarketFundPer")
	private Double marketFundPer;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCheckDateFrom(Date custCheckDateFrom) {
		this.custCheckDateFrom = custCheckDateFrom;
	}
	
	public Date getCustCheckDateFrom() {
		return this.custCheckDateFrom;
	}
	public void setCustCheckDateTo(Date custCheckDateTo) {
		this.custCheckDateTo = custCheckDateTo;
	}
	
	public Date getCustCheckDateTo() {
		return this.custCheckDateTo;
	}
	public void setIsAddAllInfo(String isAddAllInfo) {
		this.isAddAllInfo = isAddAllInfo;
	}
	
	public String getIsAddAllInfo() {
		return this.isAddAllInfo;
	}
	public void setMainCommiPer(Double mainCommiPer) {
		this.mainCommiPer = mainCommiPer;
	}
	
	public Double getMainCommiPer() {
		return this.mainCommiPer;
	}
	public void setServCommiPer(Double servCommiPer) {
		this.servCommiPer = servCommiPer;
	}
	
	public Double getServCommiPer() {
		return this.servCommiPer;
	}
	public void setSoftCommiPer(Double softCommiPer) {
		this.softCommiPer = softCommiPer;
	}
	
	public Double getSoftCommiPer() {
		return this.softCommiPer;
	}
	public void setCurtainCommiPer(Double curtainCommiPer) {
		this.curtainCommiPer = curtainCommiPer;
	}
	
	public Double getCurtainCommiPer() {
		return this.curtainCommiPer;
	}
	public void setFurnitureCommiPer(Double furnitureCommiPer) {
		this.furnitureCommiPer = furnitureCommiPer;
	}
	
	public Double getFurnitureCommiPer() {
		return this.furnitureCommiPer;
	}
	public void setMarketFundPer(Double marketFundPer) {
		this.marketFundPer = marketFundPer;
	}
	
	public Double getMarketFundPer() {
		return this.marketFundPer;
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

}
