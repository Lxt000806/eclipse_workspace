package com.house.home.entity.insales;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

/**
 * WHPosiBal信息
 */
@Entity
@Table(name = "tWHPosiBal")
public class WHPosiBal extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "WHPPk")
	private Integer whppk;
	@Column(name = "ITCode")
	private String itcode;
	@Column(name = "QtyCal")
	private Double qtyCal;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	public void setWhppk(Integer whppk) {
		this.whppk = whppk;
	}
	
	public Integer getWhppk() {
		return this.whppk;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	
	public String getItcode() {
		return this.itcode;
	}
	public void setQtyCal(Double qtyCal) {
		this.qtyCal = qtyCal;
	}
	
	public Double getQtyCal() {
		return this.qtyCal;
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

}
