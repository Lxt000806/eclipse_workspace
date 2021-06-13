package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * BankPos信息
 */
@Entity
@Table(name = "tBankPos")
public class BankPos extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "RcvAct")
	private String rcvAct;
	@Column(name = "PosID")
	private String posId;
	@Column(name = "CompName")
	private String compName;
	@Column(name = "MinFee")
	private Double minFee;
	@Column(name = "MaxFee")
	private Double maxFee;
	@Column(name = "FeePerc")
	private Double feePerc;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "AcquireFeePerc")
	private Double acquireFeePerc;
	@Column(name = "CompCode")
	private String compCode;
	@Column(name = "CardAttr")
	private String cardAttr;//卡性质
	@Column(name = "CardType")
	private String cardType;//卡类型
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
	public void setRcvAct(String rcvAct) {
		this.rcvAct = rcvAct;
	}
	
	public String getRcvAct() {
		return this.rcvAct;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	
	public String getPosId() {
		return this.posId;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	public String getCompName() {
		return this.compName;
	}
	public void setMinFee(Double minFee) {
		this.minFee = minFee;
	}
	
	public Double getMinFee() {
		return this.minFee;
	}
	public void setMaxFee(Double maxFee) {
		this.maxFee = maxFee;
	}
	
	public Double getMaxFee() {
		return this.maxFee;
	}
	public void setFeePerc(Double feePerc) {
		this.feePerc = feePerc;
	}
	
	public Double getFeePerc() {
		return this.feePerc;
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
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setAcquireFeePerc(Double acquireFeePerc) {
		this.acquireFeePerc = acquireFeePerc;
	}
	
	public Double getAcquireFeePerc() {
		return this.acquireFeePerc;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public String getCardAttr() {
		return cardAttr;
	}

	public void setCardAttr(String cardAttr) {
		this.cardAttr = cardAttr;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	
}
