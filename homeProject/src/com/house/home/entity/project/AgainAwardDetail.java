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
@Table(name = "tAgainAwardDetail")
public class AgainAwardDetail extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;

	@Column(name = "No")
	private String no;
	
	@Column(name = "CustCode")
	private String custCode;

	@Column(name = "Amount")
	private Double amount;

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
	private Date signDateFrom;
	@Transient
	private Date signDateTo;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private String address;
	
	@Transient
	private String status;
	
    @Transient
    private boolean viewAll;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    public boolean canViewAll() {
        return viewAll;
    }
    public void setViewAll(boolean viewAll) {
        this.viewAll = viewAll;
    }

}
