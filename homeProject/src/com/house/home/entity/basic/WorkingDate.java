package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name="tDiscAmtCtrl")
public class WorkingDate extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "Date")
    private Date date;
	@Column(name = "HoliType")
    private String holiType;
	@Column(name = "LastUpdate")
    private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
	@Column(name = "Expired")
    private String expired;
	@Column(name = "ActionLog")
    private String actionLog;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getHoliType() {
		return holiType;
	}
	public void setHoliType(String holiType) {
		this.holiType = holiType;
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
