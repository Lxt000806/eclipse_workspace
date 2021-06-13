package com.house.home.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tSoftPerf")
public class SoftPerf extends BaseEntity{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "No")
	private String no;	
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name="EndDate")
	private Date endDate;
	@Column(name="Status")
	private String status;
	@Column(name="Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name="Mon")
	private Integer mon;
	
	@Transient
	private Date sendDateFrom;
	@Transient
	private Date sendDateTo;
	@Transient
	private String empCode;
	@Transient
	private String department2;
	@Transient
	private String countType;
	@Transient
	private String custCode;
	@Transient
	private String softPerType;
	@Transient
	private String designMan;
	@Transient
	private String buyer;
	@Transient
	private String businessMan;
	@Transient
	private String tabName;
	@Transient
	private String address;
	@Transient
	private Date endDateFrom;
	@Transient
	private Date endDateTo;
	@Transient
	private String type;
	@Transient
	private Integer beginMon;
	@Transient
	private Integer endMon;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getEndDateFrom() {
		return endDateFrom;
	}
	public void setEndDateFrom(Date endDateFrom) {
		this.endDateFrom = endDateFrom;
	}
	public Date getEndDateTo() {
		return endDateTo;
	}
	public void setEndDateTo(Date endDateTo) {
		this.endDateTo = endDateTo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getMon() {
		return mon;
	}
	public void setMon(Integer mon) {
		this.mon = mon;
	}
	public String getBusinessMan() {
		return businessMan;
	}
	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getSoftPerType() {
		return softPerType;
	}
	public void setSoftPerType(String softPerType) {
		this.softPerType = softPerType;
	}
	public String getDesignMan() {
		return designMan;
	}
	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getCountType() {
		return countType;
	}
	public void setCountType(String countType) {
		this.countType = countType;
	}
	public Date getSendDateFrom() {
		return sendDateFrom;
	}
	public void setSendDateFrom(Date sendDateFrom) {
		this.sendDateFrom = sendDateFrom;
	}
	public Date getSendDateTo() {
		return sendDateTo;
	}
	public void setSendDateTo(Date sendDateTo) {
		this.sendDateTo = sendDateTo;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
	public Integer getBeginMon() {
		return beginMon;
	}
	public void setBeginMon(Integer beginMon) {
		this.beginMon = beginMon;
	}
	public Integer getEndMon() {
		return endMon;
	}
	public void setEndMon(Integer endMon) {
		this.endMon = endMon;
	}
	
}
