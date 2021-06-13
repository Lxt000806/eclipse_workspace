package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tPrjProgTemp")
public class PrjProgTemp extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="No")
	private String no;
	@Column(name="Descr")
	private String descr;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String ActionLog;
	@Column(name="Remarks")
	private String remarks;
	@Column(name="Type")
	private String type;
	@Column(name="CustType")
	private String custType;
	
	@Transient
	private String m_umState;
	@Transient
	private Integer nowPk;
	@Transient
	private Integer nowPkTipEvent;
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
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
		return ActionLog;
	}

	public void setActionLog(String actionLog) {
		ActionLog = actionLog;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getM_umState() {
		return m_umState;
	}

	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}

	public Integer getNowPk() {
		return nowPk;
	}

	public void setNowPk(Integer nowPk) {
		this.nowPk = nowPk;
	}

	public Integer getNowPkTipEvent() {
		return nowPkTipEvent;
	}

	public void setNowPkTipEvent(Integer nowPkTipEvent) {
		this.nowPkTipEvent = nowPkTipEvent;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}
	
}



