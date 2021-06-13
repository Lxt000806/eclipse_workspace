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
 * CustTypePerfExpr信息
 */
@Entity
@Table(name = "tCustTypePerfExpr")
public class CustTypePerfExpr extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "PerfExpr")
	private String perfExpr;
	@Column(name = "PerfExprRemarks")
	private String perfExprRemarks;
	@Column(name = "ChgPerfExpr")
	private String chgPerfExpr;
	@Column(name = "ChgPerfExprRemarks")
	private String chgPerfExprRemarks;
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
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getBeginDate() {
		return this.beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setPerfExpr(String perfExpr) {
		this.perfExpr = perfExpr;
	}
	
	public String getPerfExpr() {
		return this.perfExpr;
	}
	public void setPerfExprRemarks(String perfExprRemarks) {
		this.perfExprRemarks = perfExprRemarks;
	}
	
	public String getPerfExprRemarks() {
		return this.perfExprRemarks;
	}
	public void setChgPerfExpr(String chgPerfExpr) {
		this.chgPerfExpr = chgPerfExpr;
	}
	
	public String getChgPerfExpr() {
		return this.chgPerfExpr;
	}
	public void setChgPerfExprRemarks(String chgPerfExprRemarks) {
		this.chgPerfExprRemarks = chgPerfExprRemarks;
	}
	
	public String getChgPerfExprRemarks() {
		return this.chgPerfExprRemarks;
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
