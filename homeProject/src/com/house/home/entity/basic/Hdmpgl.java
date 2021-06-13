package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name="tTicket")
public class Hdmpgl extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name="PK")
	private Integer pk;
	@Column(name="ActNo")
	private String actNo;
	@Column (name="TicketNo")
	private String ticketNo;
	@Column (name="Status")
	private String status;
	@Column(name="Descr")
	private String descr;
	@Column (name="Address")
	private String address;
	@Column (name="DesignMan")
	private String designMan;
	@Column (name="BusinessMan")
	private String businessMan;
	@Column (name="ProvideDate")
	private Date provideDate;
	@Column (name="SignDate")
	private Date signDate;
	@Column(name="LastUpdate")
	private  Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column (name="ActionLog")
	private String actionLog;
	@Column(name="ProvideType")
	private String provideType;
	@Column(name="ProvideCZY")
	private String provideCZY;
	@Column (name="BusiManName")
	private String busiManName;
	@Column (name="BusiManPhone")
	private String busiManPhone;
	@Column (name="Phone")
	private String phone;
	@Column (name="PlanSupplType")
	private String planSupplType;
	
	@Transient 
	private String provideDescr;
	@Transient
	private String actDescr;
	@Transient
	private Integer numFrom;
	@Transient 
	private Integer ticketNum;
	@Transient
	private Integer numTo;
	@Transient
	private String businessDescr;
	@Transient
	private String designDescr;
	@Transient
	private Date signDateFrom;
	@Transient
	private Date signDateTo;
	@Transient
	private String department1;
	@Transient
	private String department2;
	@Transient
	private String validAct;
	@Transient
	private String supplCode;
	@Transient
	private String itemCode;
	
	
	
	public String getPlanSupplType() {
		return planSupplType;
	}
	public void setPlanSupplType(String planSupplType) {
		this.planSupplType = planSupplType;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	public String getValidAct() {
		return validAct;
	}
	public void setValidAct(String validAct) {
		this.validAct = validAct;
	}
	public String getDepartment1() {
		return department1;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public Date getSignDateFrom() {
		return signDateFrom;
	}
	public void setSignDateFrom(Date signDateFrom) {
		this.signDateFrom = signDateFrom;
	}
	public Date getSignDateTo() {
		return signDateTo;
	}
	public void setSignDateTo(Date signDateTo) {
		this.signDateTo = signDateTo;
	}
	public String getDesignDescr() {
		return designDescr;
	}
	public void setDesignDescr(String designDescr) {
		this.designDescr = designDescr;
	}
	public String getBusinessDescr() {
		return businessDescr;
	}
	public void setBusinessDescr(String businessDescr) {
		this.businessDescr = businessDescr;
	}
	public Integer getNumTo() {
		return numTo;
	}
	public void setNumTo(Integer numTo) {
		this.numTo = numTo;
	}
	public Integer getNumFrom() {
		return numFrom;
	}
	public void setNumFrom(Integer numFrom) {
		this.numFrom = numFrom;
	}
	public Integer getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(Integer ticketNum) {
		this.ticketNum = ticketNum;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getActDescr() {
		return actDescr;
	}
	public void setActDescr(String actDescr) {
		this.actDescr = actDescr;
	}
	public String getProvideDescr() {
		return provideDescr;
	}
	public void setProvideDescr(String provideDescr) {
		this.provideDescr = provideDescr;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDesignMan() {
		return designMan;
	}
	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}
	public String getBusinessMan() {
		return businessMan;
	}
	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}
	public Date getProvideDate() {
		return provideDate;
	}
	public void setProvideDate(Date provideDate) {
		this.provideDate = provideDate;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
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
	public String getProvideType() {
		return provideType;
	}
	public void setProvideType(String provideType) {
		this.provideType = provideType;
	}
	public String getProvideCZY() {
		return provideCZY;
	}
	public void setProvideCZY(String provideCZY) {
		this.provideCZY = provideCZY;
	}
	public String getBusiManName() {
		return busiManName;
	}
	public void setBusiManName(String busiManName) {
		this.busiManName = busiManName;
	}
	public String getBusiManPhone() {
		return busiManPhone;
	}
	public void setBusiManPhone(String busiManPhone) {
		this.busiManPhone = busiManPhone;
	}
	
	
	
	
	
}
