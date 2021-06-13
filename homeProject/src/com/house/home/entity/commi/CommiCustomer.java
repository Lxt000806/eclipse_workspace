package com.house.home.entity.commi;

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
 * CommiCustomer信息
 */
@Entity
@Table(name = "tCommiCustomer")
public class CommiCustomer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SignCommiNo")
	private String signCommiNo;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "InvalidNo")
	private String invalidNo;
	@Column(name = "SecondPayNo")
	private String secondPayNo;
	@Column(name = "ThirdPayNo")
	private String thirdPayNo;
	@Column(name = "CheckNo")
	private String checkNo;
	@Column(name = "LastUpdateNo")
	private String lastUpdateNo;
	@Column(name = "SignPerfPK")
	private Integer signPerfPk;
	@Column(name = "InvalidType")
	private String invalidType;
	@Column(name = "InvalidPerfPK")
	private Integer invalidPerfPk;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String address;
	@Transient
	private Integer mon;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setSignCommiNo(String signCommiNo) {
		this.signCommiNo = signCommiNo;
	}
	
	public String getSignCommiNo() {
		return this.signCommiNo;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setInvalidNo(String invalidNo) {
		this.invalidNo = invalidNo;
	}
	
	public String getInvalidNo() {
		return this.invalidNo;
	}
	public void setSecondPayNo(String secondPayNo) {
		this.secondPayNo = secondPayNo;
	}
	
	public String getSecondPayNo() {
		return this.secondPayNo;
	}
	public void setThirdPayNo(String thirdPayNo) {
		this.thirdPayNo = thirdPayNo;
	}
	
	public String getThirdPayNo() {
		return this.thirdPayNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	
	public String getCheckNo() {
		return this.checkNo;
	}
	public void setLastUpdateNo(String lastUpdateNo) {
		this.lastUpdateNo = lastUpdateNo;
	}
	
	public String getLastUpdateNo() {
		return this.lastUpdateNo;
	}
	public void setSignPerfPk(Integer signPerfPk) {
		this.signPerfPk = signPerfPk;
	}
	
	public Integer getSignPerfPk() {
		return this.signPerfPk;
	}
	public void setInvalidType(String invalidType) {
		this.invalidType = invalidType;
	}
	
	public String getInvalidType() {
		return this.invalidType;
	}
	public void setInvalidPerfPk(Integer invalidPerfPk) {
		this.invalidPerfPk = invalidPerfPk;
	}
	
	public Integer getInvalidPerfPk() {
		return this.invalidPerfPk;
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
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}

}
