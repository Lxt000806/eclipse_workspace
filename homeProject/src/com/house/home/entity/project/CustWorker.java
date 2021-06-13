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
import com.house.framework.commons.utils.DateUtil;

@Entity
@Table(name = "tCustWorker")
public class CustWorker extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PK")
	private Integer pk;
	@Column(name="WorkerCode")
	private String workerCode;
	@Column(name="WorkType12")
	private String workType12;
	@Column(name="CustCode")
	private String custCode;
	@Column(name="ComeDate")
	private Date comeDate;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Remarks")
	private String remarks;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String aciontLog;
	@Column(name="IsSysArrange")
	private String isSysArrange;
	@Column(name="ConstructDay")
	private Integer constructDay;
	@Column(name="PlanEnd")
	private Date PlanEnd;
	@Column(name="EndDate")
	private Date endDate;
	@Column(name="Status")
	private String status;
	@Column(name="ConPlanEnd")
	private Date conPlanEnd;
	@Column(name="ComeDelayType")
	private String comeDelayType;
	@Column(name="EndDelayType")
	private String endDelayType;
	@Column(name="SignDelayType")
	private String signDelayType;
	
	@Transient
	private String region;
	@Transient
	private String address;
	@Transient
	private String projectMan;
	@Transient
	private String workType12Dept;
	@Transient
	private String department2;
	@Transient
	private String constructType;
	@Transient
	private String custType;
	@Transient
	private String isDoExcel;
	@Transient
	private Date endDateFrom;
	@Transient 
	private Date endDateTo;
	
	@Transient
	private String prjRegionCode;
	@Transient
	private Date planEndDateFrom;
	@Transient
	private Date planEndDateTo;
	@Transient
	private String workType2;
	@Transient
	private String isWaterItemCtrl;
	
	@Transient
	private String workSignNo;
	@Transient
	private String isPushCust;
	@Transient
	private String workerName;
	@Transient
	private String sourceType;
	
	@Transient
	private String checkArrFlag;
	@Transient
	private String workerClassify; //工人分类 add by zb on 20190827
	
	@Transient
	private String czybh;
	@Transient
	private String isAddAllInfo;
	@Transient
	private String statisticalMethods; //统计方式 add by zb on 20200306
	@Transient
	private String salaryCtrlType;  //工资控制类型  add by cjm 20200411
	@Transient
	private String searchType;//查询方式
	@Transient
	private Integer actualDays;
	@Transient
	private Date comeDateFrom;
	@Transient
	private Date comeDateTo;
	@Transient
	private String itemType12;
	
	public String getItemType12() {
		return itemType12;
	}
	public void setItemType12(String itemType12) {
		this.itemType12 = itemType12;
	}
	public Date getComeDateFrom() {
		return comeDateFrom;
	}
	public void setComeDateFrom(Date comeDateFrom) {
		this.comeDateFrom = comeDateFrom;
	}
	public Date getComeDateTo() {
		return comeDateTo;
	}
	public void setComeDateTo(Date comeDateTo) {
		this.comeDateTo = comeDateTo;
	}
	public Integer getActualDays() {
		return actualDays;
	}
	public void setActualDays(Integer actualDays) {
		this.actualDays = actualDays;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSalaryCtrlType() {
		return salaryCtrlType;
	}
	public void setSalaryCtrlType(String salaryCtrlType) {
		this.salaryCtrlType = salaryCtrlType;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getIsPushCust() {
		return isPushCust;
	}
	public void setIsPushCust(String isPushCust) {
		this.isPushCust = isPushCust;
	}
	public String getWorkSignNo() {
		return workSignNo;
	}
	public void setWorkSignNo(String workSignNo) {
		this.workSignNo = workSignNo;
	}
	public String getIsWaterItemCtrl() {
		return isWaterItemCtrl;
	}
	public void setIsWaterItemCtrl(String isWaterItemCtrl) {
		this.isWaterItemCtrl = isWaterItemCtrl;
	}
	public String getWorkType2() {
		return workType2;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	public Date getPlanEndDateFrom() {
		return planEndDateFrom;
	}
	public void setPlanEndDateFrom(Date planEndDateFrom) {
		this.planEndDateFrom = planEndDateFrom;
	}
	public Date getPlanEndDateTo() {
		return planEndDateTo;
	}
	public void setPlanEndDateTo(Date planEndDateTo) {
		this.planEndDateTo = planEndDateTo;
	}
	public String getPrjRegionCode() {
		return prjRegionCode;
	}
	public void setPrjRegionCode(String prjRegionCode) {
		this.prjRegionCode = prjRegionCode;
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
	public String getIsDoExcel() {
		return isDoExcel;
	}
	public void setIsDoExcel(String isDoExcel) {
		this.isDoExcel = isDoExcel;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getConstructType() {
		return constructType;
	}
	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getWorkType12Dept() {
		return workType12Dept;
	}
	public void setWorkType12Dept(String workType12Dept) {
		this.workType12Dept = workType12Dept;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getIsSysArrange() {
		return isSysArrange;
	}
	public void setIsSysArrange(String isSysArrange) {
		this.isSysArrange = isSysArrange;
	}
	
	public Integer getConstructDay() {
		return constructDay;
	}
	public void setConstructDay(Integer constructDay) {
		this.constructDay = constructDay;
	}
	public Date getPlanEnd() {
		return PlanEnd;
	}
	public void setPlanEnd(Date planEnd) {
		PlanEnd = planEnd;
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
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Date getComeDate() {
		return comeDate;
	}
	public void setComeDate(Date date) {
		this.comeDate = date;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getAciontLog() {
		return aciontLog;
	}
	public void setAciontLog(String aciontLog) {
		this.aciontLog = aciontLog;
	}
	
	public String getCheckArrFlag() {
		return checkArrFlag;
	}
	public void setCheckArrFlag(String checkArrFlag) {
		this.checkArrFlag = checkArrFlag;
	}
	public String getWorkerClassify() {
		return workerClassify;
	}
	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getIsAddAllInfo() {
		return isAddAllInfo;
	}
	public void setIsAddAllInfo(String isAddAllInfo) {
		this.isAddAllInfo = isAddAllInfo;
	}
	public Date getConPlanEnd() {
		return conPlanEnd;
	}
	public void setConPlanEnd(Date conPlanEnd) {
		this.conPlanEnd = conPlanEnd;
	}
	public String getStatisticalMethods() {
		return statisticalMethods;
	}
	public void setStatisticalMethods(String statisticalMethods) {
		this.statisticalMethods = statisticalMethods;
	}
	public String getComeDelayType() {
		return comeDelayType;
	}
	public void setComeDelayType(String comeDelayType) {
		this.comeDelayType = comeDelayType;
	}
	public String getEndDelayType() {
		return endDelayType;
	}
	public void setEndDelayType(String endDelayType) {
		this.endDelayType = endDelayType;
	}
	public String getSignDelayType() {
		return signDelayType;
	}
	public void setSignDelayType(String signDelayType) {
		this.signDelayType = signDelayType;
	}
	
}
