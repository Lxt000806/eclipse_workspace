package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * BaseItemType2信息
 */
@Entity
@Table(name = "tBaseItemType2")
public class BaseItemType2 extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "BaseItemType1")
	private String baseItemType1;
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
	@Column(name = "OfferWorkType2")
	private String offerWorkType2;
	@Column(name = "MaterWorkType2")
	private String materWorkType2;
	
	@Transient
	private String offerWorkType1;
	@Transient 
	private String materWorkType1;

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
	public void setBaseItemType1(String baseItemType1) {
		this.baseItemType1 = baseItemType1;
	}
	
	public String getBaseItemType1() {
		return this.baseItemType1;
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
	public void setOfferWorkType2(String offerWorkType2) {
		this.offerWorkType2 = offerWorkType2;
	}
	
	public String getOfferWorkType2() {
		return this.offerWorkType2;
	}
	public void setMaterWorkType2(String materWorkType2) {
		this.materWorkType2 = materWorkType2;
	}
	
	public String getMaterWorkType2() {
		return this.materWorkType2;
	}

	public String getOfferWorkType1() {
		return offerWorkType1;
	}

	public void setOfferWorkType1(String offerWorkType1) {
		this.offerWorkType1 = offerWorkType1;
	}

	public String getMaterWorkType1() {
		return materWorkType1;
	}

	public void setMaterWorkType1(String materWorkType1) {
		this.materWorkType1 = materWorkType1;
	}
}
