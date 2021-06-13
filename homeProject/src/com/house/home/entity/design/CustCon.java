package com.house.home.entity.design;

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
 * CustCon信息
 */
@Entity
@Table(name = "tCustCon")
public class CustCon extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "ConDate")
	private Date conDate;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "ConMan")
	private String conMan;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Type")
	private String type;
	@Column(name = "ResrCustCode")
	private String resrCustCode;
	@Column(name = "ConDuration")
	private Integer conDuration;
	@Column(name = "CallType")
	private String callType;
	@Column(name = "nextConDate")
	private Date nextConDate;
	@Column(name = "ConWay")
	private String conWay;
	
	@Transient
	private String custAddress;
	@Transient
	private String designMan;
	@Transient
	private String businessMan;
	@Transient
	private String descr;
	@Transient
	private String designManDescr;
	@Transient
	private String businessManDescr;
	@Transient
	private String conManDescr;
	@Transient
	private String status;
	@Transient
	private String statusDescr;
	@Transient
	private String department;
	@Transient
	private String phone;
	@Transient
	private String isGetCallRecord;
	@Transient
	private String statistcsMethod; //统计方式
	@Transient
	private String resrAddress;
	@Transient
	private String custType;
	@Transient
	private String conWayDescr;
	@Transient
	private Boolean hasAllConAuth;
	
	public String getDesignMan() {
		return designMan;
	}

	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}

	public String getBusinessMan() {
		return businessMan;
	}

	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setConDate(Date conDate) {
		this.conDate = conDate;
	}
	
	public Date getConDate() {
		return this.conDate;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setConMan(String conMan) {
		this.conMan = conMan;
	}
	
	public String getConMan() {
		return this.conMan;
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

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDesignManDescr() {
		return designManDescr;
	}

	public void setDesignManDescr(String designManDescr) {
		this.designManDescr = designManDescr;
	}

	public String getBusinessManDescr() {
		return businessManDescr;
	}

	public void setBusinessManDescr(String businessManDescr) {
		this.businessManDescr = businessManDescr;
	}

	public String getConManDescr() {
		return conManDescr;
	}

	public void setConManDescr(String conManDescr) {
		this.conManDescr = conManDescr;
	}

	public String getStatusDescr() {
		return statusDescr;
	}

	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResrCustCode() {
		return resrCustCode;
	}

	public void setResrCustCode(String resrCustCode) {
		this.resrCustCode = resrCustCode;
	}

	public Integer getConDuration() {
		return conDuration;
	}

	public void setConDuration(Integer conDuration) {
		this.conDuration = conDuration;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getIsGetCallRecord() {
		return isGetCallRecord;
	}

	public void setIsGetCallRecord(String isGetCallRecord) {
		this.isGetCallRecord = isGetCallRecord;
	}

	public String getStatistcsMethod() {
		return statistcsMethod;
	}

	public void setStatistcsMethod(String statistcsMethod) {
		this.statistcsMethod = statistcsMethod;
	}

	public String getResrAddress() {
		return resrAddress;
	}

	public void setResrAddress(String resrAddress) {
		this.resrAddress = resrAddress;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public Date getNextConDate() {
		return nextConDate;
	}

	public void setNextConDate(Date nextConDate) {
		this.nextConDate = nextConDate;
	}

	public String getConWay() {
		return conWay;
	}

	public void setConWay(String conWay) {
		this.conWay = conWay;
	}

	public String getConWayDescr() {
		return conWayDescr;
	}

	public void setConWayDescr(String conWayDescr) {
		this.conWayDescr = conWayDescr;
	}

	public Boolean getHasAllConAuth() {
		return hasAllConAuth;
	}

	public void setHasAllConAuth(Boolean hasAllConAuth) {
		this.hasAllConAuth = hasAllConAuth;
	}

}
