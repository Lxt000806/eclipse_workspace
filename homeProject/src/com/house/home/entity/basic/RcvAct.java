package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name="tRcvAct")
public class RcvAct extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Code", nullable=false)
	private String code;

	@Column(name="Descr")
	private String descr;
	
	@Column(name="LastUpdate")
	private Date lastUpdate;
	
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	
	@Column(name="Expired")
	private String expired;
	
	@Column(name="ActionLog")
	private String actionLog;
	
	@Column(name="CardID")
	private String cardId;
	
	@Column(name="PayeeCode")
	private String payeeCode;
	
	@Column(name = "BankCode")
	private String bankCode;//银行编号
	
	@Column(name = "Admin")
	private String admin;

    @Column(name = "AllowTrans")
	private String allowTrans;
	
	@Override
	public String toString() {
		return "RcvAct [code=" + code + ", descr=" + descr + ", lastUpdate="
				+ lastUpdate + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", expired=" + expired + ", actionLog=" + actionLog + "]";
	}

	/** default constructor */
	public RcvAct() {	
	}

	/** minimal constructor */
	public RcvAct(String code) {
		this.code = code;
	}

	/** full constructor */
	public RcvAct(String code, String descr, Date lastUpdate,
			String lastUpdatedBy, String expired, String actionLog) {
		this.code = code;
		this.descr = descr;
		this.lastUpdate = lastUpdate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.expired = expired;
		this.actionLog = actionLog;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getDescr() {
		return descr;
	}


	public void setDescr(String descr) {
		this.descr = descr;
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

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAllowTrans() {
        return allowTrans;
    }

    public void setAllowTrans(String allowTrans) {
        this.allowTrans = allowTrans;
    }
	
}
