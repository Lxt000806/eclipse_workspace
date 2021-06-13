package com.house.home.entity.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ManualPerfCust信息
 */
@Entity
@Table(name = "tManualPerfCust")
public class ManualPerfCust extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "PerfCycleNo")
	private String perfCycleNo;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "IsCalcPerf")
	private String isCalcPerf;
	@Column(name = "AchieveDate")
	private Date achieveDate;
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

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setPerfCycleNo(String perfCycleNo) {
		this.perfCycleNo = perfCycleNo;
	}
	
	public String getPerfCycleNo() {
		return this.perfCycleNo;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setIsCalcPerf(String isCalcPerf) {
		this.isCalcPerf = isCalcPerf;
	}
	
	public String getIsCalcPerf() {
		return this.isCalcPerf;
	}
	public void setAchieveDate(Date achieveDate) {
		this.achieveDate = achieveDate;
	}
	
	public Date getAchieveDate() {
		return this.achieveDate;
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
