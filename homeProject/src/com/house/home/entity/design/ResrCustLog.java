package com.house.home.entity.design;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tResrCustLog")
public class ResrCustLog extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK")
	private Integer pk;
	@Column(name="ResrCode")
	private String resrCode;
	@Column(name="BuilderCode")
	private String builderCode;
	@Column(name="Address")
	private String address;
	@Column(name="Status")
	private String status;
	@Column(name="Descr")
	private String descr;
	@Column(name="Gender")
	private String gender;
	@Column(name="BusinessMan")
	private String businessMan;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="Mobile1")
	private String mobile1;
	
	
	
	

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getResrCode() {
		return resrCode;
	}

	public void setResrCode(String resrCode) {
		this.resrCode = resrCode;
	}

	public String getBuilderCode() {
		return builderCode;
	}

	public void setBuilderCode(String builderCode) {
		this.builderCode = builderCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBusinessMan() {
		return businessMan;
	}

	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
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

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	
	
}
