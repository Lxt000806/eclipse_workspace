package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tPrjProblem")
public class PrjProblem extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "PromDeptCode")
	private String promDeptCode;
	@Column(name = "PromTypeCode")
	private String promTypeCode;
	@Column(name = "PromPropCode")
	private String promPropCode;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "AppCZY")
	private String appCZY;
	@Column(name = "AppDate")
	private Date appDate;
	@Column(name = "ConfirmCZY")
	private String confirmCZY;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "FeedbackCZY")
	private String feedbackCZY;
	@Column(name = "FeedbackDate")
	private Date feedbackDate;
	@Column(name = "DealRemarks")
	private String dealRemarks;
	@Column(name = "DealCZY")
	private String dealCZY;
	@Column(name = "DealDate")
	private Date dealDate;
	@Column(name = "CancelCZY")
	private String cancelCZY;
	@Column(name = "CancelDate")
	private Date cancelDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name="PlanDealDate")
	private Date planDealDate;
	@Column(name="IsDeal")
	private String isDeal;
	@Column(name="IsBringStop")
	private String isBringStop;
	@Column(name="StopDays")
	private Integer stopDays;
	@Column(name="FixDutyMan")
	private String fixDutyMan;
	@Column(name = "FixDutyDate")
	private Date fixDutyDate;
	
	@Transient
	private String address;//楼盘
	@Transient
	private String custDescr;//客户名称
	@Transient
	private String DepDescr;//部门名称
	@Transient
	private Date appDateFrom;
	@Transient
	private Date appDateTo;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private Date feedbackDateFrom;
	@Transient
	private Date feedbackDateTo;
	@Transient
	private Date dealDateTo;
	@Transient
	private Date dealDateFrom;
	@Transient
	private String opSign;
	@Transient
	private String prjDepartment2;
	@Transient
	private String jcDepartment2;
	@Transient
	private String materialSteward;//主材管家
	@Transient
	private String confirmCZYDescr;
	@Transient
	private String statistcsMethod; // 工地问题分析统计方式
	@Transient
	private String empCode; //干系人
	@Transient
	private Date planDealDateFrom;
	@Transient
	private Date planDealDateTo;
	@Transient
	private String photoPath; //图片路径
	@Transient
	private String photoName; //图片名称
	
	@Transient
	private String photoString;
	
	@Transient
    private Date fixDutyDateFrom;
	
    @Transient
    private Date fixDutyDateTo;
	
	public String getPhotoString() {
		return photoString;
	}
	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}
	public String getMaterialSteward() {
		return materialSteward;
	}
	public void setMaterialSteward(String materialSteward) {
		this.materialSteward = materialSteward;
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
	public Date getFeedbackDateFrom() {
		return feedbackDateFrom;
	}
	public void setFeedbackDateFrom(Date feedbackDateFrom) {
		this.feedbackDateFrom = feedbackDateFrom;
	}
	public Date getFeedbackDateTo() {
		return feedbackDateTo;
	}
	public void setFeedbackDateTo(Date feedbackDateTo) {
		this.feedbackDateTo = feedbackDateTo;
	}
	public Date getDealDateTo() {
		return dealDateTo;
	}
	public void setDealDateTo(Date dealDateTo) {
		this.dealDateTo = dealDateTo;
	}
	public Date getDealDateFrom() {
		return dealDateFrom;
	}
	public void setDealDateFrom(Date dealDateFrom) {
		this.dealDateFrom = dealDateFrom;
	}
	public Date getPlanDealDate() {
		return planDealDate;
	}
	public void setPlanDealDate(Date planDealDate) {
		this.planDealDate = planDealDate;
	}
	public String getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
	}
	public String getDepDescr() {
		return DepDescr;
	}
	public void setDepDescr(String depDescr) {
		DepDescr = depDescr;
	}
	public String getCustDescr() {
		return custDescr;
	}
	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPromDeptCode() {
		return promDeptCode;
	}
	public void setPromDeptCode(String promDeptCode) {
		this.promDeptCode = promDeptCode;
	}
	public String getPromTypeCode() {
		return promTypeCode;
	}
	public void setPromTypeCode(String promTypeCode) {
		this.promTypeCode = promTypeCode;
	}
	public String getPromPropCode() {
		return promPropCode;
	}
	public void setPromPropCode(String promPropCode) {
		this.promPropCode = promPropCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAppCZY() {
		return appCZY;
	}
	public void setAppCZY(String appCZY) {
		this.appCZY = appCZY;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getConfirmCZY() {
		return confirmCZY;
	}
	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getFeedbackCZY() {
		return feedbackCZY;
	}
	public void setFeedbackCZY(String feedbackCZY) {
		this.feedbackCZY = feedbackCZY;
	}
	public Date getFeedbackDate() {
		return feedbackDate;
	}
	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	public String getDealRemarks() {
		return dealRemarks;
	}
	public void setDealRemarks(String dealRemarks) {
		this.dealRemarks = dealRemarks;
	}
	public String getDealCZY() {
		return dealCZY;
	}
	public void setDealCZY(String dealCZY) {
		this.dealCZY = dealCZY;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public String getCancelCZY() {
		return cancelCZY;
	}
	public void setCancelCZY(String cancelCZY) {
		this.cancelCZY = cancelCZY;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
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
	public String getOpSign() {
		return opSign;
	}
	public void setOpSign(String opSign) {
		this.opSign = opSign;
	}
	public String getPrjDepartment2() {
		return prjDepartment2;
	}
	public void setPrjDepartment2(String prjDepartment2) {
		this.prjDepartment2 = prjDepartment2;
	}
	public String getJcDepartment2() {
		return jcDepartment2;
	}
	public void setJcDepartment2(String jcDepartment2) {
		this.jcDepartment2 = jcDepartment2;
	}
	public String getIsBringStop() {
		return isBringStop;
	}
	public void setIsBringStop(String isBringStop) {
		this.isBringStop = isBringStop;
	}
	public Integer getStopDays() {
		return stopDays;
	}
	public void setStopDays(Integer stopDays) {
		this.stopDays = stopDays;
	}
	public String getConfirmCZYDescr() {
		return confirmCZYDescr;
	}
	public void setConfirmCZYDescr(String confirmCZYDescr) {
		this.confirmCZYDescr = confirmCZYDescr;
	}
	public String getStatistcsMethod() {
		return statistcsMethod;
	}
	public void setStatistcsMethod(String statistcsMethod) {
		this.statistcsMethod = statistcsMethod;
	}
	public String getFixDutyMan() {
		return fixDutyMan;
	}
	public void setFixDutyMan(String fixDutyMan) {
		this.fixDutyMan = fixDutyMan;
	}
	public Date getFixDutyDate() {
		return fixDutyDate;
	}
	public void setFixDutyDate(Date fixDutyDate) {
		this.fixDutyDate = fixDutyDate;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Date getPlanDealDateFrom() {
		return planDealDateFrom;
	}
	public void setPlanDealDateFrom(Date planDealDateFrom) {
		this.planDealDateFrom = planDealDateFrom;
	}
	public Date getPlanDealDateTo() {
		return planDealDateTo;
	}
	public void setPlanDealDateTo(Date planDealDateTo) {
		this.planDealDateTo = planDealDateTo;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
    public Date getFixDutyDateFrom() {
        return fixDutyDateFrom;
    }
    public void setFixDutyDateFrom(Date fixDutyDateFrom) {
        this.fixDutyDateFrom = fixDutyDateFrom;
    }
    public Date getFixDutyDateTo() {
        return fixDutyDateTo;
    }
    public void setFixDutyDateTo(Date fixDutyDateTo) {
        this.fixDutyDateTo = fixDutyDateTo;
    }
	
}
