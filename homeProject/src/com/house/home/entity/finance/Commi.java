package com.house.home.entity.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * Commi信息
 */
@Entity
@Table(name = "tCommi")
public class Commi extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "Status")
	private String status;
	@Column(name = "PerfCycleNo")
	private String perfCycleNo;
	@Column(name = "PrjPerfNo")
	private String prjPerfNo;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Remarks")
	private String remarks;
	@Transient
	private String address;
	@Transient
	private String commiType;
	@Transient
	private String designMan;
	@Transient
	private String sceneDesignMan;
	@Transient
	private String custCode;
	@Transient
	private String itemCode;
	@Transient
	private String itemDescr;
	@Transient
	private Date signDateFrom;
	@Transient
	private Date signDateTo;
	@Transient
	private String itemType2;
	@Transient
	private String reqPks;
	@Transient
	private String commiPerc;
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
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
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
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

	public String getPerfCycleNo() {
		return perfCycleNo;
	}

	public void setPerfCycleNo(String perfCycleNo) {
		this.perfCycleNo = perfCycleNo;
	}

	public String getPrjPerfNo() {
		return prjPerfNo;
	}

	public void setPrjPerfNo(String prjPerfNo) {
		this.prjPerfNo = prjPerfNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCommiType() {
		return commiType;
	}

	public void setCommiType(String commiType) {
		this.commiType = commiType;
	}

	public String getDesignMan() {
		return designMan;
	}

	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}

	public String getSceneDesignMan() {
		return sceneDesignMan;
	}

	public void setSceneDesignMan(String sceneDesignMan) {
		this.sceneDesignMan = sceneDesignMan;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public Date getSignDateFrom() {
		return signDateFrom;
	}

	public void setSignDateFrom(Date signDateFrom) {
		this.signDateFrom = signDateFrom;
	}

	public Date getSignDateTo() {
		return signDateTo;
	}

	public void setSignDateTo(Date signDateTo) {
		this.signDateTo = signDateTo;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getReqPks() {
		return reqPks;
	}

	public void setReqPks(String reqPks) {
		this.reqPks = reqPks;
	}

	public String getCommiPerc() {
		return commiPerc;
	}

	public void setCommiPerc(String commiPerc) {
		this.commiPerc = commiPerc;
	}


	
}
