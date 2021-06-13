package com.house.home.entity.design;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.annotation.DescrAnnotation;
import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tResrCustMapper")
public class ResrCustMapper extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PK")
	private int pk;
	@Column(name="ResrCustCode")
	private String resrCustCode;
	@Column(name="CustCode")
	private String custCode;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getResrCustCode() {
		return resrCustCode;
	}
	public void setResrCustCode(String resrCustCode) {
		this.resrCustCode = resrCustCode;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
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
