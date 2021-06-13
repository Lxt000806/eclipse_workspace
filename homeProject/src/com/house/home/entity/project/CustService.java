package com.house.home.entity.project;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tCustService")
public class CustService extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "Type")
	private String type;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "RepMan")
	private String repMan;
	@Column(name = "RepDate")
	private Date repDate;
	@Column(name = "ServiceMan")
	private String serviceMan;
	@Column(name = "DealMan")
	private String dealMan;
	@Column(name = "DealDate")
	private Date dealDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Undertaker")
	private String undertaker; //新增字段承担方
	@Column(name = "Address")
	private String address;//楼盘地址
	@Column(name = "CustProblemPK")
	private Integer custProblemPK; //投诉明细PK
	@Column(name = "FeedBackRemark")
	private String feedBackRemark; //售后反馈

	
	
	@Transient
	private Date repDateFrom;
	@Transient
	private Date repDateTo;//报备日期从..至..
	@Transient
	private Date dealDateFrom;
	@Transient
	private Date dealDateTo;//处理日期从..至..
	@Transient
	private String custAccountMobile1;
	@Transient
	private String isFromCustProblem;
	@Transient
	private String appStatus;//app上的客户状态

	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRepMan() {
		return repMan;
	}
	public void setRepMan(String repMan) {
		this.repMan = repMan;
	}
	public Date getRepDate() {
		return repDate;
	}
	public void setRepDate(Date repDate) {
		this.repDate = repDate;
	}
	public String getServiceMan() {
		return serviceMan;
	}
	public void setServiceMan(String serviceMan) {
		this.serviceMan = serviceMan;
	}
	public String getDealMan() {
		return dealMan;
	}
	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
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
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getRepDateFrom() {
		return repDateFrom;
	}
	public void setRepDateFrom(Date repDateFrom) {
		this.repDateFrom = repDateFrom;
	}
	public Date getRepDateTo() {
		return repDateTo;
	}
	public void setRepDateTo(Date repDateTo) {
		this.repDateTo = repDateTo;
	}
	public Date getDealDateFrom() {
		return dealDateFrom;
	}
	public void setDealDateFrom(Date dealDateFrom) {
		this.dealDateFrom = dealDateFrom;
	}
	public Date getDealDateTo() {
		return dealDateTo;
	}
	public void setDealDateTo(Date dealDateTo) {
		this.dealDateTo = dealDateTo;
	}
	public String getUndertaker() {
		return undertaker;
	}
	public void setUndertaker(String undertaker) {
		this.undertaker = undertaker;
	}
	public String getCustAccountMobile1() {
		return custAccountMobile1;
	}
	public void setCustAccountMobile1(String custAccountMobile1) {
		this.custAccountMobile1 = custAccountMobile1;
	}
	public Integer getCustProblemPK() {
		return custProblemPK;
	}
	public void setCustProblemPK(Integer custProblemPK) {
		this.custProblemPK = custProblemPK;
	}
	public String getIsFromCustProblem() {
		return isFromCustProblem;
	}
	public void setIsFromCustProblem(String isFromCustProblem) {
		this.isFromCustProblem = isFromCustProblem;
	}
	public String getFeedBackRemark() {
		return feedBackRemark;
	}
	public void setFeedBackRemark(String feedBackRemark) {
		this.feedBackRemark = feedBackRemark;
	}
	public String getAppStatus(){
		return appStatus;
	}
	public void setAppStatus(String appStatus){
		this.appStatus = appStatus;
	}

	
}
