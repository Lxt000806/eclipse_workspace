package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name="tDiscAmtCtrl")
public class DiscAmtCtrl extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PK")
    private Integer pk;
	@Column(name = "CustType")
    private String custType;
	@Column(name = "DiscAmtType")
    private String discAmtType;
	@Column(name = "MaxDiscAmtExpr")
    private String maxDiscAmtExpr;
	@Column(name = "LastUpdate")
    private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
	@Column(name = "Expired")
    private String expired;
	@Column(name = "ActionLog")
    private String actionLog;
	@Column(name = "IsCupBoard")
	private String isCupBoard;
	@Column(name = "IsService")
	private String isService;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getDiscAmtType() {
		return discAmtType;
	}
	public void setDiscAmtType(String discAmtType) {
		this.discAmtType = discAmtType;
	}
	public String getMaxDiscAmtExpr() {
		return maxDiscAmtExpr;
	}
	public void setMaxDiscAmtExpr(String maxDiscAmtExpr) {
		this.maxDiscAmtExpr = maxDiscAmtExpr;
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
	public String getIsCupBoard() {
		return isCupBoard;
	}
	public void setIsCupBoard(String isCupBoard) {
		this.isCupBoard = isCupBoard;
	}
	public String getIsService() {
		return isService;
	}
	public void setIsService(String isService) {
		this.isService = isService;
	}
	
}
