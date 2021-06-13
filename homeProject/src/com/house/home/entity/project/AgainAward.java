package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tAgainAward")
public class AgainAward extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "No")
	private String no;

	@Column(name = "Status")
	private String status;

	@Column(name = "Date")
	private Date date;

	@Column(name = "AppCZY")
	private String appCZY;

	@Column(name = "ConfirmDate")
	private Date confirmDate;

	@Column(name = "ConfirmCZY")
	private String confirmCZY;

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
	
	@Column(name = "DocumentNo")
	private String documentNo;

	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private String custCode;
	@Transient
	private String address;
	
	@Transient
	private String m_umState;
	@Transient
	private String czybh;
	@Transient
	private String oldStatus;
	
	@Transient
	private boolean viewAll;
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAppCZY() {
		return appCZY;
	}

	public void setAppCZY(String appCZY) {
		this.appCZY = appCZY;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getConfirmCZY() {
		return confirmCZY;
	}

	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getConfirmDateFrom() {
		return confirmDateFrom;
	}

	public void setConfirmDateFrom(Date confirmDateFrom) {
		this.confirmDateFrom = confirmDateFrom;
	}

	public Date getConfirmDateTo() {
		return confirmDateTo;
	}

	public void setConfirmDateTo(Date confirmDateTo) {
		this.confirmDateTo = confirmDateTo;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getM_umState() {
		return m_umState;
	}

	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

    public boolean canViewAll() {
        return viewAll;
    }

    public void setViewAll(boolean viewAll) {
        this.viewAll = viewAll;
    }
	
	
}
