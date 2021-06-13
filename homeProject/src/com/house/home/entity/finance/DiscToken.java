package com.house.home.entity.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * DiscToken信息
 */
@Entity
@Table(name = "tDiscToken")
public class DiscToken extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "GiftPK")
	private Integer giftPk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "DiscType")
	private String discType;
	@Column(name = "Mode")
	private String mode;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "ProvideDate")
	private Date provideDate;
	@Column(name = "CrtDate")
	private Date crtDate;
	@Column(name = "Status")
	private String status;
	@Column(name = "ChgNo")
	private String chgNo;
	@Column(name = "UseCustCode")
	private String useCustCode;
	@Column(name = "CheckDesDate")
	private Date checkDesDate;
	@Column(name = "CheckDesAmount")
	private Double checkDesAmount;
	@Column(name = "ExpiredDate")
	private Date expiredDate;
	@Column(name = "UseStep")
	private String useStep;
	@Column(name = "EnableDate")
	private Date enableDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String oldCustCode;
	@Transient
	private String hasSelectNo; //已选券号
	@Transient
	private String unSelectNo;
	@Transient
	private String Address;
	@Transient
	private String dateType;
	@Transient
	private String containOldCustCode;
	@Transient
	private String containHasSelect;
	@Transient
	private String wfProcStatus;
	
	public String getWfProcStatus() {
		return wfProcStatus;
	}

	public void setWfProcStatus(String wfProcStatus) {
		this.wfProcStatus = wfProcStatus;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setGiftPk(Integer giftPk) {
		this.giftPk = giftPk;
	}
	
	public Integer getGiftPk() {
		return this.giftPk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setDiscType(String discType) {
		this.discType = discType;
	}
	
	public String getDiscType() {
		return this.discType;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getMode() {
		return this.mode;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setProvideDate(Date provideDate) {
		this.provideDate = provideDate;
	}
	
	public Date getProvideDate() {
		return this.provideDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	
	public Date getCrtDate() {
		return this.crtDate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}
	
	public String getChgNo() {
		return this.chgNo;
	}
	public void setUseCustCode(String useCustCode) {
		this.useCustCode = useCustCode;
	}
	
	public String getUseCustCode() {
		return this.useCustCode;
	}
	public void setCheckDesDate(Date checkDesDate) {
		this.checkDesDate = checkDesDate;
	}
	
	public Date getCheckDesDate() {
		return this.checkDesDate;
	}
	public void setCheckDesAmount(Double checkDesAmount) {
		this.checkDesAmount = checkDesAmount;
	}
	
	public Double getCheckDesAmount() {
		return this.checkDesAmount;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	public Date getExpiredDate() {
		return this.expiredDate;
	}
	public void setUseStep(String useStep) {
		this.useStep = useStep;
	}
	
	public String getUseStep() {
		return this.useStep;
	}
	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}
	
	public Date getEnableDate() {
		return this.enableDate;
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

	public String getHasSelectNo() {
		return hasSelectNo;
	}

	public void setHasSelectNo(String hasSelectNo) {
		this.hasSelectNo = hasSelectNo;
	}

	public String getUnSelectNo() {
		return unSelectNo;
	}

	public void setUnSelectNo(String unSelectNo) {
		this.unSelectNo = unSelectNo;
	}

	public String getOldCustCode() {
		return oldCustCode;
	}

	public void setOldCustCode(String oldCustCode) {
		this.oldCustCode = oldCustCode;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getContainOldCustCode() {
		return containOldCustCode;
	}

	public void setContainOldCustCode(String containOldCustCode) {
		this.containOldCustCode = containOldCustCode;
	}

	public String getContainHasSelect() {
		return containHasSelect;
	}

	public void setContainHasSelect(String containHasSelect) {
		this.containHasSelect = containHasSelect;
	}
	
	
}
