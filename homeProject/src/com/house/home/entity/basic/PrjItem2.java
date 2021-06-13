package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name="tPrjItem2")
public class PrjItem2 extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "Code")
    private String code;
	@Column(name = "Descr")
    private String descr;
	@Column(name = "PrjItem")
    private String prjItem;
	@Column(name = "MinDay")
    private Integer minDay;
	@Column(name = "Seq")
    private Integer seq;
	@Column(name = "IsAppWork")
    private String isAppWork;
	@Column(name = "LastUpdate")
    private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
	@Column(name = "Expired")
    private String expired;
	@Column(name = "ActionLog")
    private String actionLog;
	@Column(name = "worktype12")
    private String workType12;
	@Column(name = "IsUpEndDate")
    private String isUpEndDate;
	@Column(name = "OfferWorkType2")
	private String offerWorkType2; //人工工种分类2
	
	@Transient
	private String offerWorkType2Descr; //人工工种分类2描述
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public Integer getMinDay() {
		return minDay;
	}
	public void setMinDay(Integer minDay) {
		this.minDay = minDay;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getIsAppWork() {
		return isAppWork;
	}
	public void setIsAppWork(String isAppWork) {
		this.isAppWork = isAppWork;
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
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getIsUpEndDate() {
		return isUpEndDate;
	}
	public void setIsUpEndDate(String isUpEndDate) {
		this.isUpEndDate = isUpEndDate;
	}
	public String getOfferWorkType2() {
		return offerWorkType2;
	}
	public void setOfferWorkType2(String offerWorkType2) {
		this.offerWorkType2 = offerWorkType2;
	}
	public String getOfferWorkType2Descr() {
		return offerWorkType2Descr;
	}
	public void setOfferWorkType2Descr(String offerWorkType2Descr) {
		this.offerWorkType2Descr = offerWorkType2Descr;
	}


}
