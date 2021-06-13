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
 * SoftPerfPrePer信息
 */
@Entity
@Table(name = "tSoftPerfPrePer")
public class SoftPerfPrePer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "IsOutSideEmp")
	private String isOutSideEmp;
	@Column(name = "Per")
	private Double per;
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
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	
	public String getDepartment1() {
		return this.department1;
	}
	public void setIsOutSideEmp(String isOutSideEmp) {
		this.isOutSideEmp = isOutSideEmp;
	}
	
	public String getIsOutSideEmp() {
		return this.isOutSideEmp;
	}
	public void setPer(Double per) {
		this.per = per;
	}
	
	public Double getPer() {
		return this.per;
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
