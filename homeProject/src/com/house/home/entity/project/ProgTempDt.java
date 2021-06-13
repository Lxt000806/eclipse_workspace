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

@Entity
@Table(name = "tProgTempDt")
public class ProgTempDt extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name="TempNo")
	private String tempNo;
	@Column(name="PrjItem")
	private String prjItem;
	@Column(name="PlanBegin")
	private Integer planBegin;
	@Column(name="PlanEnd")
	private Integer planEnd;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String ActionLog;
	@Column(name="SpaceDay")
	private Integer spaceDay;
	@Column(name="BefPrjItem")
	private String befPrjItem;
	@Column(name="DispSeq")
	private Integer dispSeq;
	@Column(name="Type")
	private String type;
	@Column(name="BaseConDay")
	private Integer baseConDay;
	@Column(name="BaseWork")
	private Integer baseWork;
	@Column(name="DayWork")
	private Integer dayWork;
	@Transient
	private String m_umState;
	@Transient
	private String mm_umState;
	@Transient
	private String rowId;
	@Transient
	private Integer nowPk;
	@Transient
	private String tmpType;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getTempNo() {
		return tempNo;
	}
	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public Integer getPlanBegin() {
		return planBegin;
	}
	public void setPlanBegin(Integer planBegin) {
		this.planBegin = planBegin;
	}
	public Integer getPlanEnd() {
		return planEnd;
	}
	public void setPlanEnd(Integer planEnd) {
		this.planEnd = planEnd;
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
	public Integer getSpaceDay() {
		return spaceDay;
	}
	public void setSpaceDay(Integer spaceDay) {
		this.spaceDay = spaceDay;
	}
	public String getM_umState() {
		return m_umState;
	}
	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}
	public String getMm_umState() {
		return mm_umState;
	}
	public void setMm_umState(String mm_umState) {
		this.mm_umState = mm_umState;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public Integer getNowPk() {
		return nowPk;
	}
	public void setNowPk(Integer nowPk) {
		this.nowPk = nowPk;
	}
	public String getBefPrjItem() {
		return befPrjItem;
	}
	public void setBefPrjItem(String befPrjItem) {
		this.befPrjItem = befPrjItem;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getBaseConDay() {
		return baseConDay;
	}
	public void setBaseConDay(Integer baseConDay) {
		this.baseConDay = baseConDay;
	}
	public Integer getBaseWork() {
		return baseWork;
	}
	public void setBaseWork(Integer baseWork) {
		this.baseWork = baseWork;
	}
	public Integer getDayWork() {
		return dayWork;
	}
	public void setDayWork(Integer dayWork) {
		this.dayWork = dayWork;
	}
	public String getTmpType() {
		return tmpType;
	}
	public void setTmpType(String tmpType) {
		this.tmpType = tmpType;
	}
	
}



