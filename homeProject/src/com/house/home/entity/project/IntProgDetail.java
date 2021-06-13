package com.house.home.entity.project;

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
 * IntProgDetail信息
 */
@Entity
@Table(name = "tIntProgDetail")
public class IntProgDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Type")
	private String type;
	@Column(name = "Date")
	private Date date;
	@Column(name = "ResPart")
	private String resPart;
	@Column(name = "CancelReson")
	private String cancelReson;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IsCupboard")
	private String isCupboard;

	@Transient
	private String typedescr;
	@Transient
	private String ResPartDescr;
	@Transient
	private String CancelResonDescr;
	@Transient
	private String IsCup;
	@Transient
	private String address;
	
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
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setResPart(String resPart) {
		this.resPart = resPart;
	}
	
	public String getResPart() {
		return this.resPart;
	}
	public void setCancelReson(String cancelReson) {
		this.cancelReson = cancelReson;
	}
	
	public String getCancelReson() {
		return this.cancelReson;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	
	public String getIsCupboard() {
		return this.isCupboard;
	}

	public String getTypedescr() {
		return typedescr;
	}

	public void setTypedescr(String typedescr) {
		this.typedescr = typedescr;
	}

	public String getResPartDescr() {
		return ResPartDescr;
	}

	public void setResPartDescr(String resPartDescr) {
		ResPartDescr = resPartDescr;
	}

	public String getCancelResonDescr() {
		return CancelResonDescr;
	}

	public void setCancelResonDescr(String cancelResonDescr) {
		CancelResonDescr = cancelResonDescr;
	}

	public String getIsCup() {
		return IsCup;
	}

	public void setIsCup(String isCup) {
		IsCup = isCup;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
