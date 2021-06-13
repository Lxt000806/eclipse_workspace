package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * WorkCostImportItem信息
 */
@Entity
@Table(name = "tWorkCostImportItem")
public class WorkCostImportItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "WorkCostType")
	private String workCostType;
	@Column(name = "Type")
	private String type;
	@Column(name = "PrjItem")
	private String prjItem;
	@Column(name = "WorkType12")
	private String workType12;
	@Column(name = "CalcType")
	private String calcType;
	@Column(name = "Price")
	private Double price;
	@Column(name = "WorkType2")
	private String workType2;
	@Column(name = "OfferWorkType12")
	private String offerWorkType12;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "IsRepeatImport")
	private String isRepeatImport;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setWorkCostType(String workCostType) {
		this.workCostType = workCostType;
	}
	
	public String getWorkCostType() {
		return this.workCostType;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	
	public String getPrjItem() {
		return this.prjItem;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	
	public String getWorkType12() {
		return this.workType12;
	}
	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}
	
	public String getCalcType() {
		return this.calcType;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPrice() {
		return this.price;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	
	public String getWorkType2() {
		return this.workType2;
	}
	public void setOfferWorkType12(String offerWorkType12) {
		this.offerWorkType12 = offerWorkType12;
	}
	
	public String getOfferWorkType12() {
		return this.offerWorkType12;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setIsRepeatImport(String isRepeatImport) {
		this.isRepeatImport = isRepeatImport;
	}
	
	public String getIsRepeatImport() {
		return this.isRepeatImport;
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
