package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * OrgSeal信息
 */
@Entity
@Table(name = "tOrgSeal")
public class OrgSeal extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "OrgId")
	private String orgId;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "SealId")
	private String sealId;
	@Column(name = "Htext")
	private String htext;
	@Column(name = "Qtext")
	private String qtext;
	@Column(name = "Color")
	private String color;
	@Column(name = "Central")
	private String central;
	@Column(name = "Type")
	private String type;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getOrgId() {
		return this.orgId;
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
	public void setSealId(String sealId) {
		this.sealId = sealId;
	}
	
	public String getSealId() {
		return this.sealId;
	}
	public void setHtext(String htext) {
		this.htext = htext;
	}
	
	public String getHtext() {
		return this.htext;
	}
	public void setQtext(String qtext) {
		this.qtext = qtext;
	}
	
	public String getQtext() {
		return this.qtext;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
	public void setCentral(String central) {
		this.central = central;
	}
	
	public String getCentral() {
		return this.central;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}

}
