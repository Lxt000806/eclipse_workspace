package com.house.home.entity.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * MainCommi信息
 */
@Entity
@Table(name = "tMainCommi")
public class MainCommi extends BaseEntity {

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
	@Column(name = "PrjPerfNo")
	private String prjPerfNo;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	@Transient
	private String address;
	@Transient
	private String custType;
	@Transient
	private String cmpCode;
	
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
	public void setPrjPerfNo(String prjPerfNo) {
		this.prjPerfNo = prjPerfNo;
	}
	
	public String getPrjPerfNo() {
		return this.prjPerfNo;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCmpCode() {
		return cmpCode;
	}

	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}
	
}
