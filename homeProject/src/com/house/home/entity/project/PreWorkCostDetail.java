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
 * PreWorkCostDetail信息
 */
@Entity
@Table(name = "tPreWorkCostDetail")
public class PreWorkCostDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "SalaryType")
	private String salaryType;
	@Column(name = "WorkType2")
	private String workType2;
	@Column(name = "WorkerCode")
	private String workerCode;
	@Column(name = "ActName")
	private String actName;
	@Column(name = "CardId")
	private String cardId;
	@Column(name = "AppAmount")
	private Double appAmount;
	@Column(name = "CfmAmount")
	private Double cfmAmount;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Status")
	private String status;
	@Column(name = "ApplyMan")
	private String applyMan;
	@Column(name = "ApplyDate")
	private Date applyDate;
	@Column(name = "ConfirmAssist")
	private String confirmAssist;
	@Column(name = "AssistConfirmDate")
	private Date assistConfirmDate;
	@Column(name = "ConfirmManager")
	private String confirmManager;
	@Column(name = "ManagerConfirmDate")
	private Date managerConfirmDate;
	@Column(name = "No")
	private String no;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IsWithHold")
	private String isWithHold;
	@Column(name = "WithHoldNo")
	private Integer withHoldNo;
	@Column(name = "IsWorkApp")
	private String isWorkApp;
	@Column(name = "WorkAppAmount")
	private Double workAppAmount;
	@Column(name = "CustWkPk")
	private String custWkPk;
	@Column(name = "PrjConfirmDate")
	private Date prjConfirmDate;
	@Column(name = "IsAutoConfirm")
	private String isAutoConfirm;
	@Column(name = "CardId2")
	private String cardId2;
	@Transient
	private String itemAppsendDetailJson;
	@Transient
	private String salesInvoiceDetailJson;
	@Transient
	private String detailJson;
	@Transient
	private String unSelected;
	@Transient
	private String fieldJson; 
	@Transient
	private String detailJson1;	
	@Transient
	private String No1;
	@Transient
	private String address;
	@Transient
	private String projectMan;
	@Transient
	private String Department2;
	@Transient
	private String WorkCon;
	@Transient
	private Double Yukou;
	@Transient
	private Double ret1;
	@Transient
	private Double AllCustCtrl;
	@Transient
	private Double CustCtrl;
	@Transient
	private Double AllCustCost;	
	@Transient
	private Double CustCost;	
	@Transient
	private Double AllLeaveCustCost;
	@Transient
	private Double LeaveCustCost;
	@Transient
	private Double ConfirmAmount;	
	@Transient
	private String PrjItem;
	@Transient
	private Date EndDate;
	@Transient
	private String ConfirmCZY;
	@Transient
	private Date ConfirmDate;	
	@Transient
	private String ConfirmRemark;
	@Transient
	private Double CtrlCost;//工种发包
	@Transient
	private Double WorkCost;
	@Transient
	private Double CtrlCost1;//人工控制项	
	@Transient
	private String ApplyManDescr;
	@Transient
	private String ConfirmCZYDescr;
	@Transient
	private String workType1;
	@Transient
	private String msg;//1代表已经上传水电定位图到售后，0代表未上传
	@Transient
	private String m_s;//Z表示助理审核，M表示经理审核，ZF表示助理反审核，MF表示经理反审核
	
	@Transient
	private String xtcsid;//系统参数id	
	@Transient
	private String NoCzySpcBuilder;//非本人专盘
	@Transient
	private String salaryTypeDescr;
	@Transient
	private String workType2Descr;
	@Transient
	private String WorkType1Descr;
	@Transient
	private String ConfirmAssistDescr;
	@Transient
	private String ConfirmManagerDescr;
	@Transient
	private String DocumentNo;
	@Transient
	private String IsWithHoldDescr;
	@Transient
	private String refPrePks;
	@Transient
	private String appNo;
	@Transient
	private String refPrePk;
	@Transient
	private String CustCtrl_Kzx;
	@Transient
	private String CheckStatusDescr;
	@Transient
	private String IsSignDescr;
	@Transient
	private String QualityFeeBegin;
	@Transient
	private String StatusDescr;
	@Transient
	private String custStatus;
	@Transient
	private String isConfirmTwo; // 工资是否二次审核 --add by zb
	@Transient
	private String workLoad;
	@Transient
	private String workLoadDescr;
	@Transient
	private String hasCustWork; //质检安排工人——是：存在工人安排记录 --add by zb
	@Transient
	private Double quotaSalary; //定额工资 add by zb
	@Transient
	private String tips; //违反规则提示，有的话更新为备注 --add by cjg
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
	public String getSalaryType() {
		return salaryType;
	}
	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}
	public String getWorkType2() {
		return workType2;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public Double getAppAmount() {
		return appAmount;
	}
	public void setAppAmount(Double appAmount) {
		this.appAmount = appAmount;
	}
	public Double getCfmAmount() {
		return cfmAmount;
	}
	public void setCfmAmount(Double cfmAmount) {
		this.cfmAmount = cfmAmount;
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
	public String getApplyMan() {
		return applyMan;
	}
	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getConfirmAssist() {
		return confirmAssist;
	}
	public void setConfirmAssist(String confirmAssist) {
		this.confirmAssist = confirmAssist;
	}
	public Date getAssistConfirmDate() {
		return assistConfirmDate;
	}
	public void setAssistConfirmDate(Date assistConfirmDate) {
		this.assistConfirmDate = assistConfirmDate;
	}
	public String getConfirmManager() {
		return confirmManager;
	}
	public void setConfirmManager(String confirmManager) {
		this.confirmManager = confirmManager;
	}
	public Date getManagerConfirmDate() {
		return managerConfirmDate;
	}
	public void setManagerConfirmDate(Date managerConfirmDate) {
		this.managerConfirmDate = managerConfirmDate;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
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
	public String getIsWithHold() {
		return isWithHold;
	}
	public void setIsWithHold(String isWithHold) {
		this.isWithHold = isWithHold;
	}
	public Integer getWithHoldNo() {
		return withHoldNo;
	}
	public void setWithHoldNo(Integer withHoldNo) {
		this.withHoldNo = withHoldNo;
	}
	public String getIsWorkApp() {
		return isWorkApp;
	}
	public void setIsWorkApp(String isWorkApp) {
		this.isWorkApp = isWorkApp;
	}
	public Double getWorkAppAmount() {
		return workAppAmount;
	}
	public void setWorkAppAmount(Double workAppAmount) {
		this.workAppAmount = workAppAmount;
	}
	public String getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(String custWkPk) {
		this.custWkPk = custWkPk;
	}
	public Date getPrjConfirmDate() {
		return prjConfirmDate;
	}
	public void setPrjConfirmDate(Date prjConfirmDate) {
		this.prjConfirmDate = prjConfirmDate;
	}
	public String getIsAutoConfirm() {
		return isAutoConfirm;
	}
	public void setIsAutoConfirm(String isAutoConfirm) {
		this.isAutoConfirm = isAutoConfirm;
	}
	
	public String getCardId2() {
		return cardId2;
	}
	public void setCardId2(String cardId2) {
		this.cardId2 = cardId2;
	}
	public String getItemAppsendDetailJson() {
		return itemAppsendDetailJson;
	}
	public void setItemAppsendDetailJson(String itemAppsendDetailJson) {
		this.itemAppsendDetailJson = itemAppsendDetailJson;
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
	public String getUnSelected() {
		return unSelected;
	}
	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}
	public String getFieldJson() {
		return fieldJson;
	}
	public void setFieldJson(String fieldJson) {
		this.fieldJson = fieldJson;
	}
	public String getDetailJson1() {
		return detailJson1;
	}
	public void setDetailJson1(String detailJson1) {
		this.detailJson1 = detailJson1;
	}
	public String getNo1() {
		return No1;
	}
	public void setNo1(String no1) {
		No1 = no1;
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
	public String getDepartment2() {
		return Department2;
	}
	public void setDepartment2(String department2) {
		Department2 = department2;
	}
	public String getWorkCon() {
		return WorkCon;
	}
	public void setWorkCon(String workCon) {
		WorkCon = workCon;
	}
	public Double getYukou() {
		return Yukou;
	}
	public void setYukou(Double yukou) {
		Yukou = yukou;
	}
	public Double getRet1() {
		return ret1;
	}
	public void setRet1(Double ret1) {
		this.ret1 = ret1;
	}
	public Double getAllCustCtrl() {
		return AllCustCtrl;
	}
	public void setAllCustCtrl(Double allCustCtrl) {
		AllCustCtrl = allCustCtrl;
	}
	public Double getCustCtrl() {
		return CustCtrl;
	}
	public void setCustCtrl(Double custCtrl) {
		CustCtrl = custCtrl;
	}
	public Double getAllCustCost() {
		return AllCustCost;
	}
	public void setAllCustCost(Double allCustCost) {
		AllCustCost = allCustCost;
	}
	public Double getCustCost() {
		return CustCost;
	}
	public void setCustCost(Double custCost) {
		CustCost = custCost;
	}
	public Double getAllLeaveCustCost() {
		return AllLeaveCustCost;
	}
	public void setAllLeaveCustCost(Double allLeaveCustCost) {
		AllLeaveCustCost = allLeaveCustCost;
	}
	public Double getLeaveCustCost() {
		return LeaveCustCost;
	}
	public void setLeaveCustCost(Double leaveCustCost) {
		LeaveCustCost = leaveCustCost;
	}
	public Double getConfirmAmount() {
		return ConfirmAmount;
	}
	public void setConfirmAmount(Double confirmAmount) {
		ConfirmAmount = confirmAmount;
	}
	public String getPrjItem() {
		return PrjItem;
	}
	public void setPrjItem(String prjItem) {
		PrjItem = prjItem;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public String getConfirmCZY() {
		return ConfirmCZY;
	}
	public void setConfirmCZY(String confirmCZY) {
		ConfirmCZY = confirmCZY;
	}
	public Date getConfirmDate() {
		return ConfirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		ConfirmDate = confirmDate;
	}
	public String getConfirmRemark() {
		return ConfirmRemark;
	}
	public void setConfirmRemark(String confirmRemark) {
		ConfirmRemark = confirmRemark;
	}
	public Double getCtrlCost() {
		return CtrlCost;
	}
	public void setCtrlCost(Double ctrlCost) {
		CtrlCost = ctrlCost;
	}
	public Double getWorkCost() {
		return WorkCost;
	}
	public void setWorkCost(Double workCost) {
		WorkCost = workCost;
	}
	public Double getCtrlCost1() {
		return CtrlCost1;
	}
	public void setCtrlCost1(Double ctrlCost1) {
		CtrlCost1 = ctrlCost1;
	}
	public String getApplyManDescr() {
		return ApplyManDescr;
	}
	public void setApplyManDescr(String applyManDescr) {
		ApplyManDescr = applyManDescr;
	}
	public String getConfirmCZYDescr() {
		return ConfirmCZYDescr;
	}
	public void setConfirmCZYDescr(String confirmCZYDescr) {
		ConfirmCZYDescr = confirmCZYDescr;
	}
	public String getWorkType1() {
		return workType1;
	}
	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getM_s() {
		return m_s;
	}
	public void setM_s(String m_s) {
		this.m_s = m_s;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getXtcsid() {
		return xtcsid;
	}
	public void setXtcsid(String xtcsid) {
		this.xtcsid = xtcsid;
	}
	public String getNoCzySpcBuilder() {
		return NoCzySpcBuilder;
	}
	public void setNoCzySpcBuilder(String noCzySpcBuilder) {
		NoCzySpcBuilder = noCzySpcBuilder;
	}
	public String getSalaryTypeDescr() {
		return salaryTypeDescr;
	}
	public void setSalaryTypeDescr(String salaryTypeDescr) {
		this.salaryTypeDescr = salaryTypeDescr;
	}
	public String getWorkType2Descr() {
		return workType2Descr;
	}
	public void setWorkType2Descr(String workType2Descr) {
		this.workType2Descr = workType2Descr;
	}
	public String getWorkType1Descr() {
		return WorkType1Descr;
	}
	public void setWorkType1Descr(String workType1Descr) {
		WorkType1Descr = workType1Descr;
	}
	public String getConfirmManagerDescr() {
		return ConfirmManagerDescr;
	}
	public void setConfirmManagerDescr(String confirmManagerDescr) {
		ConfirmManagerDescr = confirmManagerDescr;
	}
	public String getDocumentNo() {
		return DocumentNo;
	}
	public void setDocumentNo(String documentNo) {
		DocumentNo = documentNo;
	}
	public String getIsWithHoldDescr() {
		return IsWithHoldDescr;
	}
	public void setIsWithHoldDescr(String isWithHoldDescr) {
		IsWithHoldDescr = isWithHoldDescr;
	}
	public String getConfirmAssistDescr() {
		return ConfirmAssistDescr;
	}
	public void setConfirmAssistDescr(String confirmAssistDescr) {
		ConfirmAssistDescr = confirmAssistDescr;
	}
	public String getRefPrePks() {
		return refPrePks;
	}
	public void setRefPrePks(String refPrePks) {
		this.refPrePks = refPrePks;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getRefPrePk() {
		return refPrePk;
	}
	public void setRefPrePk(String refPrePk) {
		this.refPrePk = refPrePk;
	}
	public String getCustCtrl_Kzx() {
		return CustCtrl_Kzx;
	}
	public void setCustCtrl_Kzx(String custCtrl_Kzx) {
		CustCtrl_Kzx = custCtrl_Kzx;
	}
	public String getCheckStatusDescr() {
		return CheckStatusDescr;
	}
	public void setCheckStatusDescr(String checkStatusDescr) {
		CheckStatusDescr = checkStatusDescr;
	}
	public String getIsSignDescr() {
		return IsSignDescr;
	}
	public void setIsSignDescr(String isSignDescr) {
		IsSignDescr = isSignDescr;
	}
	public String getQualityFeeBegin() {
		return QualityFeeBegin;
	}
	public void setQualityFeeBegin(String qualityFeeBegin) {
		QualityFeeBegin = qualityFeeBegin;
	}
	public String getStatusDescr() {
		return StatusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		StatusDescr = statusDescr;
	}
	public String getCustStatus() {
		return custStatus;
	}
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	public String getIsConfirmTwo() {
		return isConfirmTwo;
	}
	public void setIsConfirmTwo(String isConfirmTwo) {
		this.isConfirmTwo = isConfirmTwo;
	}
	public String getWorkLoad() {
		return workLoad;
	}
	public void setWorkLoad(String workLoad) {
		this.workLoad = workLoad;
	}
	public String getWorkLoadDescr() {
		return workLoadDescr;
	}
	public void setWorkLoadDescr(String workLoadDescr) {
		this.workLoadDescr = workLoadDescr;
	}
	public String getHasCustWork() {
		return hasCustWork;
	}
	public void setHasCustWork(String hasCustWork) {
		this.hasCustWork = hasCustWork;
	}
	public Double getQuotaSalary() {
		return quotaSalary;
	}
	public void setQuotaSalary(Double quotaSalary) {
		this.quotaSalary = quotaSalary;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	
}
