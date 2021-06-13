package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

/**
 * FixAreaTypeItemType12Attr信息
 */
@Entity
@Table(name = "tFixAreaTypeItemType12Attr")
public class FixAreaTypeItemType12Attr extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "PK")
	private String pk;
	@Column(name = "FixAreaType")
	private String fixAreaType;
	@Column(name = "ItemType12")
	private String itemType12;
	@Column(name = "Attr")
	private String attr;
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
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getFixAreaType() {
		return fixAreaType;
	}
	public void setFixAreaType(String fixAreaType) {
		this.fixAreaType = fixAreaType;
	}
	public String getItemType12() {
		return itemType12;
	}
	public void setItemType12(String itemType12) {
		this.itemType12 = itemType12;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
