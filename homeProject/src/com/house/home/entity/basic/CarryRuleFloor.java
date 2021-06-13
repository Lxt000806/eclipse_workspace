package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.house.framework.commons.orm.BaseEntity;

/**
 * tCarryRuleFloor信息
 */
@Entity
@Table(name = "tCarryRuleFloor")
public class CarryRuleFloor extends BaseEntity {
	
	private static final long serialVersionUID = 1L;	
    	@Id
	@Column(name = "Pk")
	private String pk;
	@Column(name = "No")
	private String no;
	@Column(name = "BeginFloor")
	private String beginFloor;
	@Column(name = "EndFloor")
	private String endFloor;
	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getBeginFloor() {
		return beginFloor;
	}

	public void setBeginFloor(String beginFloor) {
		this.beginFloor = beginFloor;
	}

	public String getEndFloor() {
		return endFloor;
	}

	public void setEndFloor(String endFloor) {
		this.endFloor = endFloor;
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

	public String getNo1() {
		return No1;
	}

	public void setNo1(String no1) {
		No1 = no1;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "CardAmount")
	private float cardAmount;
	@Column(name = "IncValue")
	private float incValue;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;	
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String detailJson;
	
	@Transient
	private String No1;

	
	

}
