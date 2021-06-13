package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;

import com.house.framework.commons.orm.BaseEntity;

/**
 * PrjProgCheck信息
 */
@Entity
@Table(name = "tProgCheckPlanDetail")
public class ProgCheckPlanDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "PlanNo")
	private String planNo;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "AppCZY")
	private String appCZY;
	@Column(name = "AppDate")
	private Date appDate;
	@Column(name = "CheckNo")
	private String checkNo;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "AppPk")
	private Integer appPk;
	
	
	@Transient
	private Integer cigaNum;
	
	
	public Integer getCigaNum() {
		return cigaNum;
	}
	public void setCigaNum(Integer cigaNum) {
		this.cigaNum = cigaNum;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getAppCZY() {
		return appCZY;
	}
	public void setAppCZY(String appCZY) {
		this.appCZY = appCZY;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getAppPk() {
		return appPk;
	}
	public void setAppPk(Integer appPk) {
		this.appPk = appPk;
	}
	
}
