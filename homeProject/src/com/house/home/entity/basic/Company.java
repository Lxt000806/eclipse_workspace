package com.house.home.entity.basic;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * 公司信息
 */
@Entity
@Table(name = "tCompany")
public class Company extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Code",nullable = false)
	private String code;
	@Column(name = "Desc1")
	private String desc1;
	@Column(name = "Desc2")
	private String desc2;
	@Column(name = "Type")
	private String type;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "CmpnyName")
	private String cmpnyName;
	@Column(name = "CmpnyAddress")
	private String cmpnyAddress;
	@Column(name = "CmpnyPhone")
	private String cmpnyPhone;
	@Column(name = "CmpnyFax")
	private String cmpnyFax;
	@Column(name = "CmpnyURL")
	private String cmpnyURL;
	@Column(name = "PricRemark")
	private String pricRemark;
	@Column(name = "ItemRemark")
	private String itemRemark;
	@Column(name = "SoftPhone")
	private String softPhone;
	@Column(name = "MainItemRem")
	private String mainItemRem;
	@Column(name = "ServiceRem")
	private String serviceRem;
	
	@Transient
	private Double longitude;//经度（app用）
	@Transient
	private Double latitude;//纬度
	
	// Constructors

	/** default constructor */
	public Company() {
	}

	/** minimal constructor */
	public Company(String code) {
		this.code = code;
	}

	/** full constructor */
	public Company(String code, String desc1, String desc2, String type,
			Timestamp lastUpdate, String lastUpdatedBy, String expired,
			String actionLog) {
		this.code = code;
		this.desc1 = desc1;
		this.desc2 = desc2;
		this.type = type;
		this.lastUpdate = lastUpdate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.expired = expired;
		this.actionLog = actionLog;
	}

	// Property accessors

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc1() {
		return this.desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return this.desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getExpired() {
		return this.expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public String getActionLog() {
		return this.actionLog;
	}

	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}

	public String getCmpnyName() {
		return cmpnyName;
	}

	public void setCmpnyName(String cmpnyName) {
		this.cmpnyName = cmpnyName;
	}

	public String getCmpnyAddress() {
		return cmpnyAddress;
	}

	public void setCmpnyAddress(String cmpnyAddress) {
		this.cmpnyAddress = cmpnyAddress;
	}

	public String getCmpnyPhone() {
		return cmpnyPhone;
	}

	public void setCmpnyPhone(String cmpnyPhone) {
		this.cmpnyPhone = cmpnyPhone;
	}

	public String getCmpnyFax() {
		return cmpnyFax;
	}

	public void setCmpnyFax(String cmpnyFax) {
		this.cmpnyFax = cmpnyFax;
	}

	public String getCmpnyURL() {
		return cmpnyURL;
	}

	public void setCmpnyURL(String cmpnyURL) {
		this.cmpnyURL = cmpnyURL;
	}

	public String getPricRemark() {
		return pricRemark;
	}

	public void setPricRemark(String pricRemark) {
		this.pricRemark = pricRemark;
	}

	public String getItemRemark() {
		return itemRemark;
	}

	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark;
	}

	public String getSoftPhone() {
		return softPhone;
	}

	public void setSoftPhone(String softPhone) {
		this.softPhone = softPhone;
	}

	public String getMainItemRem() {
		return mainItemRem;
	}

	public void setMainItemRem(String mainItemRem) {
		this.mainItemRem = mainItemRem;
	}

	public String getServiceRem() {
		return serviceRem;
	}

	public void setServiceRem(String serviceRem) {
		this.serviceRem = serviceRem;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

}
