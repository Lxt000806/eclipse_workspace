package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CustItemConfProgDt信息
 */
@Entity
@Table(name = "tCustItemConfProgDt")
public class CustItemConfProgDt extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ConfProgNo")
	private String confProgNo;
	@Column(name = "ConfItemType")
	private String confItemType;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "LastUpdate")
	private Date lastUpdate;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setConfProgNo(String confProgNo) {
		this.confProgNo = confProgNo;
	}
	
	public String getConfProgNo() {
		return this.confProgNo;
	}
	public void setConfItemType(String confItemType) {
		this.confItemType = confItemType;
	}
	
	public String getConfItemType() {
		return this.confItemType;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
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
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

}
