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
@Table(name = "tCustWorkerApp")
public class CustWorkerApp extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PK")
	private Integer pk;
    @Column(name="CustCode")
	private  String custCode;
    @Column(name="WorkType12")
	private  String workType12;
    @Column(name="AppComeDate")
	private  Date appComeDate ;
    @Column(name="WorkerCode")
	private  String workerCode;
    @Column(name="AppDate")
	private  Date appDate;
    @Column(name="ComeDate")
	private  Date comeDate ;
    @Column(name="CustWorkPk")
	private  Integer custWorkPk  ;
    @Column(name="LastUpdate")
	private  Date lastUpdate;
    @Column(name="LastUpdatedBy")
	private  String lastUpdatedBy;
    @Column(name="Expired")
	private  String expired;
    @Column(name="ActionLog")
	private  String actionLog;
	@Column(name="Status")
	private  String status;
	@Column(name="Remarks")
	private  String remarks;
    @Column(name="ProgTempAlarmPK")
	private  Integer progTempAlarmPk;
    @Column(name="ArrDate")
    private Date arrDate;
	
	@Transient
    private String address;
    @Transient
    private String department1;
    @Transient
    private String department2;
    @Transient
    private String workerDescr;
    @Transient
    private String custDescr;
    @Transient
    private String isPrjSpc;
    @Transient
    private String projectManDescr;
    @Transient 
    private String projectMan;
    @Transient 
    private String czybh;
    @Transient
    private String onDo;
    @Transient
    private String workerLevel;
    @Transient
    private String isSign;
    @Transient
    private String region;
    @Transient
    private String constructStatus;
    @Transient
    private Date planEnd;
    @Transient 
    private Date endDate;
    @Transient
    private Integer constructDay;
    @Transient
    private Integer arrPK;
    @Transient
    private String isAutoArrange;
    @Transient
    private Date appDateFrom;
    @Transient 
    private Date appDateTo;
    @Transient
    private String custSceneDesigner;
    @Transient
    private String mainSteward;
    @Transient
    private String prjRegion;
    @Transient
    private String isWaterItemCtrl;
    @Transient
    private String mainStewardDescr;
    @Transient
    private String arrangeable;
    @Transient
    private String comeDelayType;
    
    // 集成部查询条件
    @Transient
    private String intDepartment2;
    
	public String getArrangeable() {
		return arrangeable;
	}

	public void setArrangeable(String arrangeable) {
		this.arrangeable = arrangeable;
	}

	public String getMainStewardDescr() {
		return mainStewardDescr;
	}

	public void setMainStewardDescr(String mainStewardDescr) {
		this.mainStewardDescr = mainStewardDescr;
	}

	public String getIsWaterItemCtrl() {
		return isWaterItemCtrl;
	}

	public void setIsWaterItemCtrl(String isWaterItemCtrl) {
		this.isWaterItemCtrl = isWaterItemCtrl;
	}

	public String getPrjRegion() {
		return prjRegion;
	}

	public void setPrjRegion(String prjRegion) {
		this.prjRegion = prjRegion;
	}

	public String getMainSteward() {
		return mainSteward;
	}

	public void setMainSteward(String mainSteward) {
		this.mainSteward = mainSteward;
	}

	public String getCustSceneDesigner() {
		return custSceneDesigner;
	}

	public void setCustSceneDesigner(String custSceneDesigner) {
		this.custSceneDesigner = custSceneDesigner;
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

	public String getIsAutoArrange() {
		return isAutoArrange;
	}

	public void setIsAutoArrange(String isAutoArrange) {
		this.isAutoArrange = isAutoArrange;
	}

	public Integer getArrPK() {
		return arrPK;
	}

	public void setArrPK(Integer arrPK) {
		this.arrPK = arrPK;
	}

	public Date getPlanEnd() {
		return planEnd;
	}

	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getConstructDay() {
		return constructDay;
	}

	public void setConstructDay(Integer constructDay) {
		this.constructDay = constructDay;
	}

	public String getConstructStatus() {
		return constructStatus;
	}

	public void setConstructStatus(String constructStatus) {
		this.constructStatus = constructStatus;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public String getWorkerLevel() {
		return workerLevel;
	}

	public void setWorkerLevel(String workerLevel) {
		this.workerLevel = workerLevel;
	}

	public String getOnDo() {
		return onDo;
	}

	public void setOnDo(String onDo) {
		this.onDo = onDo;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

	public String getProjectManDescr() {
		return projectManDescr;
	}

	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}

	public String getIsPrjSpc() {
		return isPrjSpc;
	}

	public void setIsPrjSpc(String isPrjSpc) {
		this.isPrjSpc = isPrjSpc;
	}

	public String getCustDescr() {
		return custDescr;
	}

	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}

	public String getWorkerDescr() {
		return workerDescr;
	}

	public void setWorkerDescr(String workerDescr) {
		this.workerDescr = workerDescr;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getWorkType12() {
		return workType12;
	}

	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}

	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	public Date getComeDate() {
		return comeDate;
	}

	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}

	public Integer getCustWorkPk() {
		return custWorkPk;
	}

	public void setCustWorkPk(Integer custWorkPk) {
		this.custWorkPk = custWorkPk;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getAppComeDate() {
		return appComeDate;
	}

	public void setAppComeDate(Date appComeDate) {
		this.appComeDate = appComeDate;
	}

	public Integer getProgTempAlarmPk() {
		return progTempAlarmPk;
	}

	public void setProgTempAlarmPk(Integer progTempAlarmPk) {
		this.progTempAlarmPk = progTempAlarmPk;
	}

	public String getComeDelayType() {
		return comeDelayType;
	}

	public void setComeDelayType(String comeDelayType) {
		this.comeDelayType = comeDelayType;
	}

    public String getIntDepartment2() {
        return intDepartment2;
    }

    public void setIntDepartment2(String intDepartment2) {
        this.intDepartment2 = intDepartment2;
    }

	public Date getArrDate() {
		return arrDate;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}
	
}
