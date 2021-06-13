package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemCheck信息
 */
@Entity
@Table(name = "tItemCheck")
public class ItemCheck extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "Status")
	private String status;
	@Column(name = "AppRemark")
	private String appRemark;
	@Column(name = "Date")
	private Date date;
	@Column(name = "AppEmp")
	private String appEmp;
	@Column(name = "ConfirmRemark")
	private String confirmRemark;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "ConfirmEmp")
	private String confirmEmp;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "LastUpdate")	
	private Date lastUpdate;
	
	@Transient
	private String department1;
	@Transient
	private Date appDateFrom;
	@Transient
	private Date appDateTo;
	@Transient
	private String custType;
	@Transient
	private String appEmpDescr;
	@Transient
	private String confirmEmpDescr;
	@Transient
	private String address;
	@Transient
	private String ret;
	@Transient
	private Date appdate;
	@Transient
	private String isItemUp;
	@Transient
	private float discCost;
	@Transient
	private String salesInvoiceDetailJson;
	@Transient
	private String detailJson;
	@Transient
	private Date custCheckDateFrom;
	@Transient
	private Date custCheckDateTo;
	@Transient
	private Date endDateFrom;
	@Transient
	private Date endDateTo;
	@Transient
	private String custStatus;
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setAppRemark(String appRemark) {
		this.appRemark = appRemark;
	}
	
	public String getAppRemark() {
		return this.appRemark;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setAppEmp(String appEmp) {
		this.appEmp = appEmp;
	}
	
	public String getAppEmp() {
		return this.appEmp;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}
	
	public String getConfirmRemark() {
		return this.confirmRemark;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	public void setConfirmEmp(String confirmEmp) {
		this.confirmEmp = confirmEmp;
	}
	
	public String getConfirmEmp() {
		return this.confirmEmp;
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
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public Date getAppDateFrom() {
		return appDateFrom;
	}

	public void setAppDateFrom(Date appDateFrom) {
		this.appDateFrom = appDateFrom;
	}

	public Date getAppDateTo() {
		return appDateTo;
	}

	public void setAppDateTo(Date appDateTo) {
		this.appDateTo = appDateTo;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getAppEmpDescr() {
		return appEmpDescr;
	}

	public void setAppEmpDescr(String appEmpDescr) {
		this.appEmpDescr = appEmpDescr;
	}

	public String getConfirmEmpDescr() {
		return confirmEmpDescr;
	}

	public void setConfirmEmpDescr(String confirmEmpDescr) {
		this.confirmEmpDescr = confirmEmpDescr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public Date getAppdate() {
		return appdate;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	public String getIsItemUp() {
		return isItemUp;
	}

	public void setIsItemUp(String isItemUp) {
		this.isItemUp = isItemUp;
	}

	public float getDiscCost() {
		return discCost;
	}

	public void setDiscCost(float discCost) {
		this.discCost = discCost;
	}

	public String getSalesInvoiceDetailJson() {
		return salesInvoiceDetailJson;
	}

	public void setSalesInvoiceDetailJson(String salesInvoiceDetailJson) {
		this.salesInvoiceDetailJson = salesInvoiceDetailJson;
	}

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public Date getCustCheckDateFrom() {
		return custCheckDateFrom;
	}

	public void setCustCheckDateFrom(Date custCheckDateFrom) {
		this.custCheckDateFrom = custCheckDateFrom;
	}

	public Date getCustCheckDateTo() {
		return custCheckDateTo;
	}

	public void setCustCheckDateTo(Date custCheckDateTo) {
		this.custCheckDateTo = custCheckDateTo;
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

	public String getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	
}
