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
 * Ticket信息
 */
@Entity
@Table(name = "tTicket")
public class Ticket extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ActNo")
	private String actNo;
	@Column(name = "TicketNo")
	private String ticketNo;
	@Column(name = "Status")
	private String status;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Address")
	private String address;
	@Column(name = "DesignMan")
	private String designMan;
	@Column(name = "BusinessMan")
	private String businessMan;
	@Column(name = "ProvideDate")
	private Date provideDate;
	@Column(name = "SignDate")
	private Date signDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	@Transient
	private String notSend;
	
	
	
	public String getNotSend() {
		return notSend;
	}

	public void setNotSend(String notSend) {
		this.notSend = notSend;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}
	
	public String getActNo() {
		return this.actNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	
	public String getTicketNo() {
		return this.ticketNo;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}
	
	public String getDesignMan() {
		return this.designMan;
	}
	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}
	
	public String getBusinessMan() {
		return this.businessMan;
	}
	public void setProvideDate(Date provideDate) {
		this.provideDate = provideDate;
	}
	
	public Date getProvideDate() {
		return this.provideDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	public Date getSignDate() {
		return this.signDate;
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
