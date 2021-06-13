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
 * MtRegion信息
 */
@Entity
@Table(name = "tMtRegion")
public class MtRegion extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RegionCode",nullable = false)
	private String regionCode;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "BelongRegionCode")
	private String belongRegionCode;
	
	@Transient
	private String belongRegionDescr;
	
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	public String getRegionCode() {
		return this.regionCode;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
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

	public String getBelongRegionCode() {
		return belongRegionCode;
	}

	public void setBelongRegionCode(String belongRegionCode) {
		this.belongRegionCode = belongRegionCode;
	}

	public String getBelongRegionDescr() {
		return belongRegionDescr;
	}

	public void setBelongRegionDescr(String belongRegionDescr) {
		this.belongRegionDescr = belongRegionDescr;
	}

	
}
