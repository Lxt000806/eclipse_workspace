package com.house.home.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tExpenseAdvanceTran")
public class ExpenseAdvanceTran extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "EmpCode")
	private String empCode;
	@Column(name = "ChgAmount")
	private Double chgAmount;
	@Column(name = "AftAmount")
	private Double aftAmount;
	@Column(name = "AdvanceDate")
	private Date advanceDate;
	@Column(name = "CardId")
	private String cardId;
	@Column(name = "ActName")
	private String actName;
	@Column(name = "Bank")
	private String bank;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "WfProcInstNo")
	private String wfProcInstNo;
	@Column (name="Remarks")
	private String remarks;
	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getWfProcInstNo() {
		return wfProcInstNo;
	}

	public void setWfProcInstNo(String wfProcInstNo) {
		this.wfProcInstNo = wfProcInstNo;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Double getChgAmount() {
		return chgAmount;
	}

	public void setChgAmount(Double chgAmount) {
		this.chgAmount = chgAmount;
	}

	public Double getAftAmount() {
		return aftAmount;
	}

	public void setAftAmount(Double aftAmount) {
		this.aftAmount = aftAmount;
	}

	public Date getAdvanceDate() {
		return advanceDate;
	}

	public void setAdvanceDate(Date advanceDate) {
		this.advanceDate = advanceDate;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
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

}
