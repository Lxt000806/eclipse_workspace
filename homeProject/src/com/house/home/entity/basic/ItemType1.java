package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemType1信息
 */
@Entity
@Table(name = "tItemType1")
public class ItemType1 extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name="IsSpecReq")
	private String isSpecReq;
	@Column(name="ProfitPer")
	private Double profitPer;
	@Column(name="WHCode")
	private String whCode;
	@Transient
	private String whDescr;
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
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

	public String getIsSpecReq() {
		return isSpecReq;
	}

	public void setIsSpecReq(String isSpecReq) {
		this.isSpecReq = isSpecReq;
	}

	public Double getProfitPer() {
		return profitPer;
	}

	public void setProfitPer(Double profitPer) {
		this.profitPer = profitPer;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getWhDescr() {
		return whDescr;
	}

	public void setWhDescr(String whDescr) {
		this.whDescr = whDescr;
	}
	
}
