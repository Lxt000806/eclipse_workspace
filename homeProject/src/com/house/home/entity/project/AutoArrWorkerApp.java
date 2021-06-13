package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * AutoArrWorkerApp信息
 */
@Entity
@Table(name = "tAutoArrWorkerApp")
public class AutoArrWorkerApp extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ArrPK")
	private Integer arrPk;
	@Column(name = "AppPK")
	private Integer appPk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "WorkType12")
	private String workType12;
	@Column(name = "AppComeDate")
	private Date appComeDate;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "ProjectMan")
	private String projectMan;
	@Column(name = "PrjLevel")
	private String prjLevel;
	@Column(name = "IsSupvr")
	private String isSupvr;
	@Column(name = "SpcBuilder")
	private String spcBuilder;
	@Column(name = "SpcBldExpired")
	private String spcBldExpired;
	@Column(name = "RegionCode")
	private String regionCode;
	@Column(name = "RegionCode2")
	private String regionCode2;
	@Column(name = "RegIsSpcWorker")
	private String regIsSpcWorker;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "Area")
	private Integer area;
	@Column(name = "WorkerCode")
	private String workerCode;
	@Column(name = "ComeDate")
	private Date comeDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setArrPk(Integer arrPk) {
		this.arrPk = arrPk;
	}
	
	public Integer getArrPk() {
		return this.arrPk;
	}
	public void setAppPk(Integer appPk) {
		this.appPk = appPk;
	}
	
	public Integer getAppPk() {
		return this.appPk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	
	public String getWorkType12() {
		return this.workType12;
	}
	public void setAppComeDate(Date appComeDate) {
		this.appComeDate = appComeDate;
	}
	
	public Date getAppComeDate() {
		return this.appComeDate;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	
	public String getProjectMan() {
		return this.projectMan;
	}
	public void setPrjLevel(String prjLevel) {
		this.prjLevel = prjLevel;
	}
	
	public String getPrjLevel() {
		return this.prjLevel;
	}
	public void setIsSupvr(String isSupvr) {
		this.isSupvr = isSupvr;
	}
	
	public String getIsSupvr() {
		return this.isSupvr;
	}
	public void setSpcBuilder(String spcBuilder) {
		this.spcBuilder = spcBuilder;
	}
	
	public String getSpcBuilder() {
		return this.spcBuilder;
	}
	public void setSpcBldExpired(String spcBldExpired) {
		this.spcBldExpired = spcBldExpired;
	}
	
	public String getSpcBldExpired() {
		return this.spcBldExpired;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	public String getRegionCode() {
		return this.regionCode;
	}
	public void setRegionCode2(String regionCode2) {
		this.regionCode2 = regionCode2;
	}
	
	public String getRegionCode2() {
		return this.regionCode2;
	}
	public void setRegIsSpcWorker(String regIsSpcWorker) {
		this.regIsSpcWorker = regIsSpcWorker;
	}
	
	public String getRegIsSpcWorker() {
		return this.regIsSpcWorker;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	
	public String getDepartment1() {
		return this.department1;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	
	public Integer getArea() {
		return this.area;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	
	public String getWorkerCode() {
		return this.workerCode;
	}
	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}
	
	public Date getComeDate() {
		return this.comeDate;
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

}
