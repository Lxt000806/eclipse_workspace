package com.house.home.entity.project;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

@Entity
@Table(name = "tFixDuty")
public class FixDuty extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "CustWkPk")
	private Integer custWkPk;
	@Column(name = "AppManType")
	private String appManType;
	@Column(name = "AppDate")
	private Date appDate;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "AppWorkerCode")
	private String appWorkerCode;
	@Column(name = "OfferPrj")
	private Double offerPrj;
	@Column(name = "Material")
	private Double material;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Status")
	private String status;
	@Column(name = "PrjConfirmDate")
	private Date prjConfirmDate;
	@Column(name = "PrjMan")
	private String prjMan;
	@Column(name = "PrjRemark")
	private String prjRemark;
	@Column(name = "CfmDate")
	private Date cfmDate;
	@Column(name = "CfmMan")
	private String cfmMan;
	@Column(name = "CfmRemark")
	private String cfmRemark;
	@Column(name = "CfmOfferPrj")
	private Double cfmOfferPrj;
	@Column(name = "CfmMaterial")
	private Double cfmMaterial;
	@Column(name = "DutyMan")
	private String dutyMan;
	@Column(name = "DutyDate")
	private Date dutyDate;
	@Column(name = "ManageCfmDate")
	private Date manageCfmDate;
	@Column(name = "ManageCfmMan")
	private String manageCfmMan;
	@Column(name = "ProvideMan")
	private String provideMan;
	@Column(name = "ProvideDate")
	private Date provideDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "CancelRemark")
	private String cancelRemark;
	@Column(name = "IsPrjAllDuty")
	private String isPrjAllDuty;
	@Column(name="CfmReturnRemark")
	private String cfmReturnRemark;
	@Column(name="Type")
	private String type;
	
	
	@Transient
	private String czybh;
	@Transient
	private String address;
	@Transient
	private String nos;
	@Transient
	private String oldStatus;
	@Transient
	private String fixDutyManJson;
	@Transient
	private String empCode;
	@Transient
	private String workerCode;
	@Transient
	private String supplCode;
	@Transient
	private String faultType;
	@Transient
	private String workType12;

	@Transient
	private Date dutyDateFrom;
	@Transient
	private Date dutyDateTo;
	@Transient
	private String appManDescr;
	@Transient
	private String m_umStatus;
	@Transient 
	private String workerName;
	@Transient
	private String detailJson;
	@Transient
	private String workType12Descr;
	@Transient
	private String keys; //已存在pk
	@Transient
	private String department2;
	@Transient
	private String fromWays;
	@Transient
	private Date manageCfmDateFrom;
	@Transient
	private Date manageCfmDateTo;
	@Transient
	private String custType;
	@Transient
	private String department2s;
	@Transient
	private Date provideDateFrom;
	@Transient
	private Date provideDateTo;
	
	public String getDepartment2s() {
		return department2s;
	}
	public void setDepartment2s(String department2s) {
		this.department2s = department2s;
	}
	public String getFromWays() {
		return fromWays;
	}
	public void setFromWays(String fromWays) {
		this.fromWays = fromWays;
	}
	public String getCfmReturnRemark() {
		return cfmReturnRemark;
	}
	public void setCfmReturnRemark(String cfmReturnRemark) {
		this.cfmReturnRemark = cfmReturnRemark;
	}
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getM_umStatus() {
		return m_umStatus;
	}
	public void setM_umStatus(String m_umStatus) {
		this.m_umStatus = m_umStatus;
	}
	public String getAppManDescr() {
		return appManDescr;
	}
	public void setAppManDescr(String appManDescr) {
		this.appManDescr = appManDescr;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
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
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public String getAppManType() {
		return appManType;
	}
	public void setAppManType(String appManType) {
		this.appManType = appManType;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public String getAppWorkerCode() {
		return appWorkerCode;
	}
	public void setAppWorkerCode(String appWorkerCode) {
		this.appWorkerCode = appWorkerCode;
	}
	public Double getOfferPrj() {
		return offerPrj;
	}
	public void setOfferPrj(Double offerPrj) {
		this.offerPrj = offerPrj;
	}
	public Double getMaterial() {
		return material;
	}
	public void setMaterial(Double material) {
		this.material = material;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getPrjConfirmDate() {
		return prjConfirmDate;
	}
	public void setPrjConfirmDate(Date prjConfirmDate) {
		this.prjConfirmDate = prjConfirmDate;
	}
	public String getPrjMan() {
		return prjMan;
	}
	public void setPrjMan(String prjMan) {
		this.prjMan = prjMan;
	}
	public String getPrjRemark() {
		return prjRemark;
	}
	public void setPrjRemark(String prjRemark) {
		this.prjRemark = prjRemark;
	}
	public Date getCfmDate() {
		return cfmDate;
	}
	public void setCfmDate(Date cfmDate) {
		this.cfmDate = cfmDate;
	}
	public String getCfmMan() {
		return cfmMan;
	}
	public void setCfmMan(String cfmMan) {
		this.cfmMan = cfmMan;
	}
	public String getCfmRemark() {
		return cfmRemark;
	}
	public void setCfmRemark(String cfmRemark) {
		this.cfmRemark = cfmRemark;
	}
	public Double getCfmOfferPrj() {
		return cfmOfferPrj;
	}
	public void setCfmOfferPrj(Double cfmOfferPrj) {
		this.cfmOfferPrj = cfmOfferPrj;
	}
	public Double getCfmMaterial() {
		return cfmMaterial;
	}
	public void setCfmMaterial(Double cfmMaterial) {
		this.cfmMaterial = cfmMaterial;
	}
	public String getDutyMan() {
		return dutyMan;
	}
	public void setDutyMan(String dutyMan) {
		this.dutyMan = dutyMan;
	}
	public Date getDutyDate() {
		return dutyDate;
	}
	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}
	public Date getManageCfmDate() {
		return manageCfmDate;
	}
	public void setManageCfmDate(Date manageCfmDate) {
		this.manageCfmDate = manageCfmDate;
	}
	public String getManageCfmMan() {
		return manageCfmMan;
	}
	public void setManageCfmMan(String manageCfmMan) {
		this.manageCfmMan = manageCfmMan;
	}
	public String getProvideMan() {
		return provideMan;
	}
	public void setProvideMan(String provideMan) {
		this.provideMan = provideMan;
	}
	public Date getProvideDate() {
		return provideDate;
	}
	public void setProvideDate(Date provideDate) {
		this.provideDate = provideDate;
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

	public String getNos() {
		return nos;
	}

	public void setNos(String nos) {
		this.nos = nos;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getFixDutyManJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(fixDutyManJson);
    	return xml;
	}

	public void setFixDutyManJson(String fixDutyManJson) {
		this.fixDutyManJson = fixDutyManJson;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	public String getSupplCode() {
		return supplCode;
	}

	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}
	public String getCancelRemark() {
		return cancelRemark;
	}
	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}
	public Date getDutyDateFrom() {
		return dutyDateFrom;
	}
	public void setDutyDateFrom(Date dutyDateFrom) {
		this.dutyDateFrom = dutyDateFrom;
	}
	public Date getDutyDateTo() {
		return dutyDateTo;
	}
	public void setDutyDateTo(Date dutyDateTo) {
		this.dutyDateTo = dutyDateTo;
	}
	public String getIsPrjAllDuty() {
		return isPrjAllDuty;
	}
	public void setIsPrjAllDuty(String isPrjAllDuty) {
		this.isPrjAllDuty = isPrjAllDuty;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getManageCfmDateFrom() {
		return manageCfmDateFrom;
	}
	public void setManageCfmDateFrom(Date manageCfmDateFrom) {
		this.manageCfmDateFrom = manageCfmDateFrom;
	}
	public Date getManageCfmDateTo() {
		return manageCfmDateTo;
	}
	public void setManageCfmDateTo(Date manageCfmDateTo) {
		this.manageCfmDateTo = manageCfmDateTo;
	}
    public String getCustType() {
        return custType;
    }
    public void setCustType(String custType) {
        this.custType = custType;
    }
	public Date getProvideDateFrom() {
		return provideDateFrom;
	}
	public void setProvideDateFrom(Date provideDateFrom) {
		this.provideDateFrom = provideDateFrom;
	}
	public Date getProvideDateTo() {
		return provideDateTo;
	}
	public void setProvideDateTo(Date provideDateTo) {
		this.provideDateTo = provideDateTo;
	}
	
}
