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

/**
 * tCustProblem信息
 */
@Entity
@Table(name = "tCustProblem")
public class CustProblem extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "PromSource")
	private String promSource;

	@Column(name = "Status")
	private String status;
	@Column(name = "PromType1")
	private String promType1;
	@Column(name = "PromType2")
	private String promType2;
	@Column(name = "PromRsn")
	private String promRsn;
	@Column(name = "SupplCode")
	private String supplCode;

	@Column(name = "CrtDate")
	private Date crtDate;
	@Column(name = "InfoDate")
	private Date infoDate;
	@Column(name = "DealCZY")
	private String dealCZY;
	@Column(name = "PlanDealDate")
	private Date planDealDate;
	@Column(name = "DealRemarks")
	private String dealRemarks;
	@Column(name = "DealDate")
	private Date dealDate;
	@Column(name = "RcvDate")
	private Date rcvDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	@Transient
	private String Address;
	@Transient
	private String PromType1Descr;
	@Transient
	private String PromRsnDescr;
	@Transient
	private String PromType2Descr;
	@Transient
	private String SupplDescr;
	@Transient
	private String DealCZYDescr;
	@Transient
	private String StatusDescr;
	@Transient
	private String ProjectManDescr;
	@Transient
	private String Source;
	@Transient
	private Date JsCrtDate;
	@Transient
	private String ProjectDept2;
	@Transient
	private String CustDescr;
	@Transient
	private String CustCode;
	@Transient
	private String CrtCZY;
	@Transient
	private Date RcvDateFrom;	
	@Transient
	private Date RcvDateTo;
	@Transient
	private Date InfoDateFrom;
	@Transient
	private Date InfoDateTo;
	@Transient
	private String AddessStatus;
	@Transient
	private String CustType;
	@Transient
	private String Mobile1;
	@Transient
	private String ProjectDept3;
	@Transient
	private String DesignManDescr;
	@Transient
	private String DesignManPhone;
	@Transient
	private String ProjectManPhone;
	@Transient
	private Date CheckOutDate;
	@Transient
	private String CompStatus;
	@Transient
	private String Remarks;
	@Transient
	private String department1;
	@Transient
	private String department2;
	@Transient
	private String appQuery;
	@Transient
	private String appQueryType;
	
	public String getAppQueryType() {
		return appQueryType;
	}

	public void setAppQueryType(String appQueryType) {
		this.appQueryType = appQueryType;
	}

	public String getAppQuery() {
		return appQuery;
	}

	public void setAppQuery(String appQuery) {
		this.appQuery = appQuery;
	}

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

	public String getPromSource() {
		return promSource;
	}

	public void setPromSource(String promSource) {
		this.promSource = promSource;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPromType1() {
		return promType1;
	}

	public void setPromType1(String promType1) {
		this.promType1 = promType1;
	}

	

	public String getPromType2() {
		return promType2;
	}

	public void setPromType2(String promType2) {
		this.promType2 = promType2;
	}

	public String getPromRsn() {
		return promRsn;
	}

	public void setPromRsn(String promRsn) {
		this.promRsn = promRsn;
	}

	public String getSupplCode() {
		return supplCode;
	}

	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public Date getInfoDate() {
		return infoDate;
	}

	public void setInfoDate(Date infoDate) {
		this.infoDate = infoDate;
	}

	public String getDealCZY() {
		return dealCZY;
	}

	public void setDealCZY(String dealCZY) {
		this.dealCZY = dealCZY;
	}

	public Date getPlanDealDate() {
		return planDealDate;
	}

	public void setPlanDealDate(Date planDealDate) {
		this.planDealDate = planDealDate;
	}

	public String getDealRemarks() {
		return dealRemarks;
	}

	public void setDealRemarks(String dealRemarks) {
		this.dealRemarks = dealRemarks;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public Date getRcvDate() {
		return rcvDate;
	}

	public void setRcvDate(Date rcvDate) {
		this.rcvDate = rcvDate;
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

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPromType1Descr() {
		return PromType1Descr;
	}

	public void setPromType1Descr(String promType1Descr) {
		PromType1Descr = promType1Descr;
	}

	public String getPromRsnDescr() {
		return PromRsnDescr;
	}

	public void setPromRsnDescr(String promRsnDescr) {
		PromRsnDescr = promRsnDescr;
	}

	public String getPromType2Descr() {
		return PromType2Descr;
	}

	public void setPromType2Descr(String promType2Descr) {
		PromType2Descr = promType2Descr;
	}

	public String getSupplDescr() {
		return SupplDescr;
	}

	public void setSupplDescr(String supplDescr) {
		SupplDescr = supplDescr;
	}

	public String getDealCZYDescr() {
		return DealCZYDescr;
	}

	public void setDealCZYDescr(String dealCZYDescr) {
		DealCZYDescr = dealCZYDescr;
	}

	public String getStatusDescr() {
		return StatusDescr;
	}

	public void setStatusDescr(String statusDescr) {
		StatusDescr = statusDescr;
	}

	public String getProjectManDescr() {
		return ProjectManDescr;
	}

	public void setProjectManDescr(String projectManDescr) {
		ProjectManDescr = projectManDescr;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public Date getJsCrtDate() {
		return JsCrtDate;
	}

	public void setJsCrtDate(Date jsCrtDate) {
		JsCrtDate = jsCrtDate;
	}

	public String getProjectDept2() {
		return ProjectDept2;
	}

	public void setProjectDept2(String projectDept2) {
		ProjectDept2 = projectDept2;
	}

	public String getCustDescr() {
		return CustDescr;
	}

	public void setCustDescr(String custDescr) {
		CustDescr = custDescr;
	}

	public Date getRcvDateFrom() {
		return RcvDateFrom;
	}

	public void setRcvDateFrom(Date rcvDateFrom) {
		RcvDateFrom = rcvDateFrom;
	}

	public Date getRcvDateTo() {
		return RcvDateTo;
	}

	public void setRcvDateTo(Date rcvDateTo) {
		RcvDateTo = rcvDateTo;
	}

	public Date getInfoDateFrom() {
		return InfoDateFrom;
	}

	public void setInfoDateFrom(Date infoDateFrom) {
		InfoDateFrom = infoDateFrom;
	}

	public Date getInfoDateTo() {
		return InfoDateTo;
	}

	public void setInfoDateTo(Date infoDateTo) {
		InfoDateTo = infoDateTo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAddessStatus() {
		return AddessStatus;
	}

	public void setAddessStatus(String addessStatus) {
		AddessStatus = addessStatus;
	}

	public String getMobile1() {
		return Mobile1;
	}

	public void setMobile1(String mobile1) {
		Mobile1 = mobile1;
	}

	public String getProjectDept3() {
		return ProjectDept3;
	}

	public void setProjectDept3(String projectDept3) {
		ProjectDept3 = projectDept3;
	}

	public String getDesignManDescr() {
		return DesignManDescr;
	}

	public void setDesignManDescr(String designManDescr) {
		DesignManDescr = designManDescr;
	}

	public String getDesignManPhone() {
		return DesignManPhone;
	}

	public void setDesignManPhone(String designManPhone) {
		DesignManPhone = designManPhone;
	}

	public String getProjectManPhone() {
		return ProjectManPhone;
	}

	public void setProjectManPhone(String projectManPhone) {
		ProjectManPhone = projectManPhone;
	}

	public Date getCheckOutDate() {
		return CheckOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		CheckOutDate = checkOutDate;
	}

	public String getCompStatus() {
		return CompStatus;
	}

	public void setCompStatus(String compStatus) {
		CompStatus = compStatus;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getCustCode() {
		return CustCode;
	}

	public void setCustCode(String custCode) {
		CustCode = custCode;
	}

	public String getCustType() {
		return CustType;
	}

	public void setCustType(String custType) {
		CustType = custType;
	}

	public String getCrtCZY() {
		return CrtCZY;
	}

	public void setCrtCZY(String crtCZY) {
		CrtCZY = crtCZY;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

}
