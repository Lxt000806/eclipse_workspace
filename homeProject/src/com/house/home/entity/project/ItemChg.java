package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * ItemChg信息
 */
@Entity
@Table(name = "tItemChg")
public class ItemChg extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "Date")
	private Date date;
	@Column(name = "BefAmount")
	private Double befAmount;
	@Column(name = "DiscAmount")
	private Double discAmount;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IsService")
	private Integer isService;
	@Column(name = "ManageFee")
	private Double manageFee;
	@Column(name = "IsCupboard")
	private String isCupboard;
	@Column(name = "DiscCost")
	private Double discCost;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "ConfirmCZY")
	private String confirmCzy;
	@Column(name = "confirmdate")
	private Date confirmdate;
	@Column(name = "PerfPK")
	private Integer perfPk;
	@Column(name = "IscalPerf")
	private String iscalPerf;
	@Column(name="planArriveDate")
	private Date planArriveDate;
	@Column(name="RefCustCode")
	private String	refCustCode;
	@Column(name="ExceptionRemarks")
	private String exceptionRemarks;
	@Column(name = "Tax")
	private Double tax;
	@Column(name = "ItemBatchNo")
	private String itemBatchNo;
	@Column(name = "IsAddAllInfo")
	private String isAddAllInfo;	//常规变更
	@Column(name = "TempNo")
	private String tempNo;
	@Column(name = "FaultType")
	private String faultType;
	@Column(name = "FaultMan")
	private String faultMan;
	@Column(name = "FaultAmount")
	private Double faultAmount;
	@Column(name = "ConfirmStatus")
	private String confirmStatus;
	
	@Transient
	private String address;
	@Transient
	private String confirmCzyDescr;
	@Transient
	private String documentNo;
	@Transient
	private String statusDescr;
	@Transient
	private String isServiceDescr;
	@Transient
	private String IsCupboardDescr;
	@Transient
	//1清单，2套餐
	private String isOutSet;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	public void setNo(String no) {
		this.no = no;
	}
	@Transient
	private Double manageFeeMainPer;
	@Transient
	private Double manageFeeServPer;
	@Transient
	private Double manageFeeCupPer;
	@Transient
	private Double chgManageFeePer;
	@Transient
	private String custType;
	@Transient
	private String itemType1Descr;
	@Transient
	private String itemCode;
	@Transient
	private String appCzyDescr;
	@Transient
	private String rowId;
	@Transient
	private String detailJson;
	@Transient
	private String itemType2;
	@Transient
	private Integer reqPk;
	@Transient
	private Integer containMain;
	@Transient
	private Integer containSoft;
	@Transient
	private Integer containMainServ;
	@Transient
	private Integer containInt;
	@Transient
	private Integer fixAreaPk;
	@Transient
	private String costRight;
	@Transient
	private String itemDescr;
	@Transient
	private String department1;
	@Transient
	private String supplCode;
	@Transient
	private String brandCode;//品牌编号
	@Transient
	private String employee;
	@Transient
	private String department2;
	@Transient
	private String buyer;
	@Transient
	private String buyerDep;
	@Transient 
	private String containCmpCust;
	@Transient
	private String notContainPlan;
	@Transient
	private String countType;//统计类型
	@Transient
	private String countWay;//统计方式
	@Transient
	private String saleType;//销售类型
	@Transient
	private String moduleCall;//模块调用
	@Transient
	private String isIntPerfDetail;//集成业绩查看明细时要判断是否橱柜
	@Transient
	private Date intPerfEndDate;//集成业绩周期截止时间
	@Transient 
	private String refAddress;
	@Transient
	private String sendType;
	@Transient
	private String mainTempNo;
	@Transient
	private String checkStatus;
	@Transient
	private String endCode;
	@Transient
	private Double chgPer;
	@Transient
	private String empDescr;
	@Transient
	private String chgStakeholderList;//干系人列表XML
	@Transient 
	private String isRegenCanreplace ;//可替换项是否替换
	@Transient 
	private String isRegenCanmodiQty ;//可替换项是否替换
	@Transient
	private String tempDescr;
	@Transient
	private String signQuoteType;//签约报价顺序
	@Transient
	private String faultTypeDescr;
	@Transient
	private String faultManDescr;
	@Transient
	private Double prjQualityFee;
	@Transient
	private String projectMan;
	@Transient
	private String projectManDescr;
	
	@Transient
	private String role;
	
	@Transient
	private String empCode;
	@Transient
	private Integer dispSeq;
	@Transient
	private String discTokenNo;
	@Transient
	private double discTokenAmount;
	@Transient
	private String itemSetNo;
	@Transient 
	private Integer prePlanAreaPK;
	@Transient
	private String constructStatus;
	@Transient
	private Double manageFeePer;
	@Transient
	private Double innerArea;
	
	@Transient
	private String excludedReqPks;

	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getSignQuoteType() {
		return signQuoteType;
	}
	public void setSignQuoteType(String signQuoteType) {
		this.signQuoteType = signQuoteType;
	}
	public String getTempDescr() {
		return tempDescr;
	}
	public void setTempDescr(String tempDescr) {
		this.tempDescr = tempDescr;
	}
	public String getIsRegenCanreplace() {
		return isRegenCanreplace;
	}
	public void setIsRegenCanreplace(String isRegenCanreplace) {
		this.isRegenCanreplace = isRegenCanreplace;
	}
	public String getIsRegenCanmodiQty() {
		return isRegenCanmodiQty;
	}
	public void setIsRegenCanmodiQty(String isRegenCanmodiQty) {
		this.isRegenCanmodiQty = isRegenCanmodiQty;
	}
	
	public String getTempNo() {
		return tempNo;
	}
	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}
	public Double getChgPer() {
		return chgPer;
	}
	public void setChgPer(Double chgPer) {
		this.chgPer = chgPer;
	}
	public String getRefAddress() {
		return refAddress;
	}
	public void setRefAddress(String refAddress) {
		this.refAddress = refAddress;
	}
	public String getRefCustCode() {
		return refCustCode;
	}
	public void setRefCustCode(String refCustCode) {
		this.refCustCode = refCustCode;
	}
	public Date getPlanArriveDate() {
		return planArriveDate;
	}
	public void setPlanArriveDate(Date planArriveDate) {
		this.planArriveDate = planArriveDate;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getCountType() {
		return countType;
	}
	public void setCountType(String countType) {
		this.countType = countType;
	}
	public String getCountWay() {
		return countWay;
	}
	public void setCountWay(String countWay) {
		this.countWay = countWay;
	}
	public String getNotContainPlan() {
		return notContainPlan;
	}
	public void setNotContainPlan(String notContainPlan) {
		this.notContainPlan = notContainPlan;
	}
	public String getContainCmpCust() {
		return containCmpCust;
	}
	public void setContainCmpCust(String containCmpCust) {
		this.containCmpCust = containCmpCust;
	}
	public String getBuyerDep() {
		return buyerDep;
	}
	public void setBuyerDep(String buyerDep) {
		this.buyerDep = buyerDep;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	public String getNo() {
		return this.no;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getItemChgDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getItemType1() {
		return this.itemType1;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setBefAmount(Double befAmount) {
		this.befAmount = befAmount;
	}
	
	public Double getBefAmount() {
		return this.befAmount;
	}
	public void setDiscAmount(Double discAmount) {
		this.discAmount = discAmount;
	}
	
	public Double getDiscAmount() {
		return this.discAmount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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
	public void setIsService(Integer isService) {
		this.isService = isService;
	}
	
	public Integer getIsService() {
		return this.isService;
	}
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	
	public Double getManageFee() {
		return this.manageFee;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	
	public String getIsCupboard() {
		return this.isCupboard;
	}
	public void setDiscCost(Double discCost) {
		this.discCost = discCost;
	}
	
	public Double getDiscCost() {
		return this.discCost;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
	public String getConfirmCzy() {
		return this.confirmCzy;
	}
	public void setConfirmdate(Date confirmdate) {
		this.confirmdate = confirmdate;
	}
	
	public Date getConfirmdate() {
		return this.confirmdate;
	}
	public void setPerfPk(Integer perfPk) {
		this.perfPk = perfPk;
	}
	
	public Integer getPerfPk() {
		return this.perfPk;
	}
	public void setIscalPerf(String iscalPerf) {
		this.iscalPerf = iscalPerf;
	}
	
	public String getIscalPerf() {
		return this.iscalPerf;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}

	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getStatusDescr() {
		return statusDescr;
	}

	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getAppCzyDescr() {
		return appCzyDescr;
	}
	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}
	public String getIsServiceDescr() {
		return isServiceDescr;
	}
	public void setIsServiceDescr(String isServiceDescr) {
		this.isServiceDescr = isServiceDescr;
	}
	public String getItemType1Descr() {
		return itemType1Descr;
	}
	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Double getManageFeeMainPer() {
		return manageFeeMainPer;
	}
	public void setManageFeeMainPer(Double manageFeeMainPer) {
		this.manageFeeMainPer = manageFeeMainPer;
	}
	public Double getManageFeeServPer() {
		return manageFeeServPer;
	}
	public void setManageFeeServPer(Double manageFeeServPer) {
		this.manageFeeServPer = manageFeeServPer;
	}
	public Double getManageFeeCupPer() {
		return manageFeeCupPer;
	}
	public void setManageFeeCupPer(Double manageFeeCupPer) {
		this.manageFeeCupPer = manageFeeCupPer;
	}
	public Double getChgManageFeePer() {
		return chgManageFeePer;
	}
	public void setChgManageFeePer(Double chgManageFeePer) {
		this.chgManageFeePer = chgManageFeePer;
	}
	public String getIsOutSet() {
		return isOutSet;
	}
	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
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
	public String getIsCupboardDescr() {
		return IsCupboardDescr;
	}
	public void setIsCupboardDescr(String isCupboardDescr) {
		IsCupboardDescr = isCupboardDescr;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public Integer getReqPk() {
		return reqPk;
	}
	public void setReqPk(Integer reqPk) {
		this.reqPk = reqPk;
	}
	public Integer getContainMain() {
		return containMain;
	}
	public void setContainMain(Integer containMain) {
		this.containMain = containMain;
	}
	public Integer getContainSoft() {
		return containSoft;
	}
	public void setContainSoft(Integer containSoft) {
		this.containSoft = containSoft;
	}
	public Integer getContainMainServ() {
		return containMainServ;
	}
	public void setContainMainServ(Integer containMainServ) {
		this.containMainServ = containMainServ;
	}
	public Integer getContainInt() {
		return containInt;
	}
	public void setContainInt(Integer containInt) {
		this.containInt = containInt;
	}
	public Integer getFixAreaPk() {
		return fixAreaPk;
	}
	public void setFixAreaPk(Integer fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}
	public String getCostRight() {
		return costRight;
	}
	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public String getDepartment1() {
		return department1;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	public String getModuleCall() {
		return moduleCall;
	}
	public void setModuleCall(String moduleCall) {
		this.moduleCall = moduleCall;
	}
	
	public String getIsIntPerfDetail() {
		return isIntPerfDetail;
	}
	public void setIsIntPerfDetail(String isIntPerfDetail) {
		this.isIntPerfDetail = isIntPerfDetail;
	}
	public Date getIntPerfEndDate() {
		return intPerfEndDate;
	}
	public void setIntPerfEndDate(Date intPerfEndDate) {
		this.intPerfEndDate = intPerfEndDate;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getExceptionRemarks() {
		return exceptionRemarks;
	}
	public void setExceptionRemarks(String exceptionRemarks) {
		this.exceptionRemarks = exceptionRemarks;
	}
	public String getMainTempNo() {
		return mainTempNo;
	}
	public void setMainTempNo(String mainTempNo) {
		this.mainTempNo = mainTempNo;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getEndCode() {
		return endCode;
	}
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public String getItemBatchNo() {
		return itemBatchNo;
	}
	public void setItemBatchNo(String itemBatchNo) {
		this.itemBatchNo = itemBatchNo;
	}
	public String getIsAddAllInfo() {
		return isAddAllInfo;
	}
	public void setIsAddAllInfo(String isAddAllInfo) {
		this.isAddAllInfo = isAddAllInfo;
	}
	public String getEmpDescr() {
		return empDescr;
	}
	public void setEmpDescr(String empDescr) {
		this.empDescr = empDescr;
	}
	public String getChgStakeholderList() {
		String xml = XmlConverUtil.jsonToXmlNoHead(chgStakeholderList);
    	return xml;
	}
	public void setChgStakeholderList(String chgStakeholderList) {
		this.chgStakeholderList = chgStakeholderList;
	}
	public String getFaultType() {
		return faultType;
	}
	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}
	public String getFaultMan() {
		return faultMan;
	}
	public void setFaultMan(String faultMan) {
		this.faultMan = faultMan;
	}
	public Double getFaultAmount() {
		return faultAmount;
	}
	public void setFaultAmount(Double faultAmount) {
		this.faultAmount = faultAmount;
	}
	public String getFaultTypeDescr() {
		return faultTypeDescr;
	}
	public void setFaultTypeDescr(String faultTypeDescr) {
		this.faultTypeDescr = faultTypeDescr;
	}
	public String getFaultManDescr() {
		return faultManDescr;
	}
	public void setFaultManDescr(String faultManDescr) {
		this.faultManDescr = faultManDescr;
	}
	public Double getPrjQualityFee() {
		return prjQualityFee;
	}
	public void setPrjQualityFee(Double prjQualityFee) {
		this.prjQualityFee = prjQualityFee;
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
	public String getDiscTokenNo() {
		return discTokenNo;
	}
	public void setDiscTokenNo(String discTokenNo) {
		this.discTokenNo = discTokenNo;
	}
	public double getDiscTokenAmount() {
		return discTokenAmount;
	}
	public void setDiscTokenAmount(double discTokenAmount) {
		this.discTokenAmount = discTokenAmount;
	}
	public String getItemSetNo() {
		return itemSetNo;
	}
	public void setItemSetNo(String itemSetNo) {
		this.itemSetNo = itemSetNo;
	}
	public Integer getPrePlanAreaPK() {
		return prePlanAreaPK;
	}
	public void setPrePlanAreaPK(Integer prePlanAreaPK) {
		this.prePlanAreaPK = prePlanAreaPK;
	}
	public String getConstructStatus() {
		return constructStatus;
	}
	public void setConstructStatus(String constructStatus) {
		this.constructStatus = constructStatus;
	}
	public Double getManageFeePer() {
		return manageFeePer;
	}
	public void setManageFeePer(Double manageFeePer) {
		this.manageFeePer = manageFeePer;
	}
    public Double getInnerArea() {
        return innerArea;
    }
    public void setInnerArea(Double innerArea) {
        this.innerArea = innerArea;
    }
    public String getExcludedReqPks() {
        return excludedReqPks;
    }
    public void setExcludedReqPks(String excludedReqPks) {
        this.excludedReqPks = excludedReqPks;
    }
	
}
