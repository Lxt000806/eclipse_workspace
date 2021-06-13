package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * SupplFeeType信息
 */
@Entity
@Table(name = "tSupplFeeType")
public class SupplFeeType extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "IsDefault")
	private String isDefault;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	@Transient
	private String codes;
	@Transient
	private String isComponent;
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
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	public String getIsDefault() {
		return this.isDefault;
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

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getIsComponent() {
		return isComponent;
	}

	public void setIsComponent(String isComponent) {
		this.isComponent = isComponent;
	}

}
