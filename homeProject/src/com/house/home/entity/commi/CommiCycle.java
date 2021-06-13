package com.house.home.entity.commi;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CommiCycle信息
 */
@Entity
@Table(name = "tCommiCycle")
public class CommiCycle extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Mon")
	private Integer mon;
	@Column(name = "Status")
	private String status;
	@Column(name = "FloatBeginMon")
	private Integer floatBeginMon;
	@Column(name = "FloatEndMon")
	private Integer floatEndMon;
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

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setMon(Integer mon) {
		this.mon = mon;
	}
	
	public Integer getMon() {
		return this.mon;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setFloatBeginMon(Integer floatBeginMon) {
		this.floatBeginMon = floatBeginMon;
	}
	
	public Integer getFloatBeginMon() {
		return this.floatBeginMon;
	}
	public void setFloatEndMon(Integer floatEndMon) {
		this.floatEndMon = floatEndMon;
	}
	
	public Integer getFloatEndMon() {
		return this.floatEndMon;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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
