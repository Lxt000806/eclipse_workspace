package com.house.home.entity.basic;

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
 * WorkSignAlarm信息
 */
@Entity
@Table(name = "tWorkSignAlarm")
public class WorkSignAlarm extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "PrjItem2")
	private String prjItem2;
	@Column(name = "IsComplete")
	private String isComplete;
	@Column(name = "IsNeedReq")
	private String isNeedReq;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "ItemType2")
	private String itemType2;
	@Column(name = "JobType")
	private String jobType;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "notExistOffWorkType12")
	private String notExistOffWorkType12;
	@Column(name="ConfirmPrjItem")
	private String confirmPrjItem;
	@Column(name="WorkerClassify")
	private String workerClassify;
	
	@Column(name = "Type")
	private String type;
	
	@Transient
	private String isConfirm;

	public String getWorkerClassify() {
		return workerClassify;
	}

	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setPrjItem2(String prjItem2) {
		this.prjItem2 = prjItem2;
	}
	
	public String getPrjItem2() {
		return this.prjItem2;
	}
	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
	
	public String getIsComplete() {
		return this.isComplete;
	}
	public void setIsNeedReq(String isNeedReq) {
		this.isNeedReq = isNeedReq;
	}
	
	public String getIsNeedReq() {
		return this.isNeedReq;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	
	public String getItemType2() {
		return this.itemType2;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	public String getJobType() {
		return this.jobType;
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

	public String getNotExistOffWorkType12() {
		return notExistOffWorkType12;
	}

	public void setNotExistOffWorkType12(String notExistOffWorkType12) {
		this.notExistOffWorkType12 = notExistOffWorkType12;
	}

	public String getConfirmPrjItem() {
		return confirmPrjItem;
	}

	public void setConfirmPrjItem(String confirmPrjItem) {
		this.confirmPrjItem = confirmPrjItem;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
	
}
