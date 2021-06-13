package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * BuilderRep信息
 */
@Entity
@Table(name = "tBuilderRep")
public class BuilderRep extends BaseEntity {

	private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Type")
	private String type;
	@Column(name = "BuildStatus")
	private String buildStatus;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "DealRemark")
	private String dealRemark;
	@Transient
	private String ProjectManDescr;
	@Transient
	private String Department2;
	@Transient
	private String Department2jc;
	@Transient
	private Date dangqiandate;
	@Transient
	private String address;
	@Transient
	private String tgyy;
	@Transient
	private String brtg;//本日停工
	@Transient
	private String zcHousekeeper; //主材管家

	
	public String getProjectManDescr() {
		return ProjectManDescr;
	}

	public void setProjectManDescr(String projectManDescr) {
		ProjectManDescr = projectManDescr;
	}

	public String getDepartment2() {
		return Department2;
	}

	public void setDepartment2(String department2) {
		Department2 = department2;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getDepartment2jc() {
		return Department2jc;
	}

	public void setDepartment2jc(String department2jc) {
		Department2jc = department2jc;
	}

	public Date getDangqiandate() {
		return dangqiandate;
	}

	public void setDangqiandate(Date dangqiandate) {
		this.dangqiandate = dangqiandate;
	}	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTgyy() {
		return tgyy;
	}

	public void setTgyy(String tgyy) {
		this.tgyy = tgyy;
	}

	public String getBrtg() {
		return brtg;
	}

	public void setBrtg(String brtg) {
		this.brtg = brtg;
	}

	public String getDealRemark() {
		return dealRemark;
	}

	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
	}

	public String getZcHousekeeper() {
		return zcHousekeeper;
	}

	public void setZcHousekeeper(String zcHousekeeper) {
		this.zcHousekeeper = zcHousekeeper;
	}
	

}
