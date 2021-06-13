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
 * ConDayCalcRule信息
 */
@Entity
@Table(name = "tConDayCalcRule")
public class ConDayCalcRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "WorkerClassify")
	private String workerClassify;
	@Column(name = "WorkType12")
	private String workType12;
	@Column(name = "Type")
	private String type;
	@Column(name = "BaseConDay")
	private Double baseConDay;
	@Column(name = "BaseWork")
	private Double baseWork;
	@Column(name = "DayWork")
	private Double dayWork;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}
	
	public String getWorkerClassify() {
		return this.workerClassify;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	
	public String getWorkType12() {
		return this.workType12;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setBaseConDay(Double baseConDay) {
		this.baseConDay = baseConDay;
	}
	
	public Double getBaseConDay() {
		return this.baseConDay;
	}
	public void setBaseWork(Double baseWork) {
		this.baseWork = baseWork;
	}
	
	public Double getBaseWork() {
		return this.baseWork;
	}
	public void setDayWork(Double dayWork) {
		this.dayWork = dayWork;
	}
	
	public Double getDayWork() {
		return this.dayWork;
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
