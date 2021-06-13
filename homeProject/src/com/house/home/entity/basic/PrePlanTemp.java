package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * PrePlanTemp信息
 */
@Entity
@Table(name = "tPrePlanTemp")
public class PrePlanTemp extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "CustType")
	private String custType;
	@Transient
	String prePlanTempDetailJson;
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}

	public String getPrePlanTempDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(prePlanTempDetailJson);
    	return xml;
	}

	public void setPrePlanTempDetailJson(String prePlanTempDetailJson) {
		this.prePlanTempDetailJson = prePlanTempDetailJson;
	}
	
}
