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
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * WorkCostDetail信息
 */
@Entity
@Table(name = "tWorkCostDetail")
public class WorkCostDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "ApplyMan")
	private String applyMan;
	@Column(name = "SalaryType")
	private String salaryType;
	@Column(name = "WorkType2")
	private String workType2;
	@Column(name = "AppAmount")
	private Double appAmount;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "ActName")
	private String actName;
	@Column(name = "CardId")
	private String cardId;
	@Column(name = "IsWithHold")
	private String isWithHold;
	@Column(name = "WithHoldNo")
	private Integer withHoldNo;
	@Column(name = "ConfirmAmount")
	private Double confirmAmount;
	@Column(name = "ConfirmRemark")
	private String confirmRemark;
	@Column(name = "Status")
	private String status;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "WorkerCode")
	private String workerCode;
	@Column(name = "RefPrePk")
	private Integer refPrePk;
	@Column(name = "RefCustCode")
	private String refCustCode;
	@Column(name = "FaultType")
	private String faultType;
	@Column(name = "FaultMan")
	private String faultMan;
	@Column(name = "WorkCostImportItemNo")
	private String workCostImportItemNo;
	
	@Transient
	private String DocumentNo;
	@Transient
	private String CustCodeDescr;
	@Transient
	private String Address;
	@Transient
	private Date SignDate;
	@Transient
	private String ApplyManDescr;
	@Transient
	private String SalaryTypeDescr;
	@Transient
	private String WorkType1;
	@Transient
	private String WorkType1Descr;
	@Transient
	private String StatusDescr;
	@Transient
	private String WorkType2Descr;
	@Transient
	private String IsWithHoldDescr;
	@Transient
	private String IsSignDescr;
	@Transient
	private String CheckStatusDescr;
	@Transient
	private String CustCtrl;
	@Transient
	private String CustCost;
	@Transient
	private String AllCustCtrl;
	@Transient
	private String AllCustCost;
	@Transient
	private String LeaveCustCost;
	@Transient
	private String AllLeaveCustCost;
	@Transient
	private String CustCtrl_Kzx;
	@Transient
	private String QualityFee;
	@Transient
	private String RealAmount;
	@Transient
	private String QualityFeeBegin;
	@Transient
	private String workCostDetailJson;
	@Transient
	private String sType;
	@Transient
	private String CustDescr;
	
	@Transient
	private String totalAmount;
	@Transient
	private String gotAmount;
	@Transient
	private String withHoldCost;
	@Transient
	private String rcvCost;
	@Transient
	private Date endDate;
	@Transient
	private Date confirmDate;
	@Transient
	private String prjItem;
	@Transient
	private String type;
	@Transient
	private String button;
	@Transient
	private String confirmCzyDescr;
	@Transient
	private String confirmCzy;
	@Transient
	private String cardId2;
	@Transient
	private String isConfirm;
	@Transient
	private String custCodes;
	@Transient
	private String custStatus;
	@Transient
	private String CheckStatus;
	@Transient
	private String ym;
	@Transient
	private String waterCustCodes;//本单有水电发包的客户号
	@Transient
	private String isWaterItemCtrl;//是否水电发包
	@Transient
	private String isWaterItemCtrlDescr;//是否水电发包
	@Transient
	private String workerPlanOffer;//定额工资
	@Transient
	private String isSalary;//是否生成防水工资
	@Transient
	private String isFacingSubsidy;//是否生成防水饰面补贴
	@Transient
	private String refCustDescr;//关联客户名称
	@Transient
	private String refAddress;//关联楼盘
	@Transient
	private String idnum;//工人身份证
	@Transient
	private String isFz;
	@Transient
	private String nameChi3;
	@Transient
	private String nameChi4;
	@Transient
	private String cardId3;
	@Transient
	private String cardId4;
	@Transient
	private String faultTypeDescr;
	@Transient
	private String faultManDescr;
	@Transient
	private Double prjQualityFee;
	@Transient
	private String refProjectMan;
	@Transient
	private String refProjectManDescr;
	@Transient
	private String jsonString;
	
	@Transient
	private String custTypeDescr;
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
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
	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}
	
	public String getApplyMan() {
		return this.applyMan;
	}
	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}
	
	public String getSalaryType() {
		return this.salaryType;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	
	public String getWorkType2() {
		return this.workType2;
	}
	public void setAppAmount(Double appAmount) {
		this.appAmount = appAmount;
	}
	
	public Double getAppAmount() {
		return this.appAmount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	
	public String getActName() {
		return this.actName;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getCardId() {
		return this.cardId;
	}
	public void setIsWithHold(String isWithHold) {
		this.isWithHold = isWithHold;
	}
	
	public String getIsWithHold() {
		return this.isWithHold;
	}
	public void setWithHoldNo(Integer withHoldNo) {
		this.withHoldNo = withHoldNo;
	}
	
	public Integer getWithHoldNo() {
		return this.withHoldNo;
	}
	public void setConfirmAmount(Double confirmAmount) {
		this.confirmAmount = confirmAmount;
	}
	
	public Double getConfirmAmount() {
		return this.confirmAmount;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}
	
	public String getConfirmRemark() {
		return this.confirmRemark;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
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
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	
	public String getWorkerCode() {
		return this.workerCode;
	}
	public void setRefPrePk(Integer refPrePk) {
		this.refPrePk = refPrePk;
	}
	
	public Integer getRefPrePk() {
		return this.refPrePk;
	}

	public String getDocumentNo() {
		return DocumentNo;
	}

	public void setDocumentNo(String documentNo) {
		DocumentNo = documentNo;
	}

	public String getCustCodeDescr() {
		return CustCodeDescr;
	}

	public void setCustCodeDescr(String custCodeDescr) {
		CustCodeDescr = custCodeDescr;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public Date getSignDate() {
		return SignDate;
	}

	public void setSignDate(Date signDate) {
		SignDate = signDate;
	}

	public String getApplyManDescr() {
		return ApplyManDescr;
	}

	public void setApplyManDescr(String applyManDescr) {
		ApplyManDescr = applyManDescr;
	}

	public String getSalaryTypeDescr() {
		return SalaryTypeDescr;
	}

	public void setSalaryTypeDescr(String salaryTypeDescr) {
		SalaryTypeDescr = salaryTypeDescr;
	}

	public String getWorkType1() {
		return WorkType1;
	}

	public void setWorkType1(String workType1) {
		WorkType1 = workType1;
	}

	public String getWorkType1Descr() {
		return WorkType1Descr;
	}

	public void setWorkType1Descr(String workType1Descr) {
		WorkType1Descr = workType1Descr;
	}

	public String getStatusDescr() {
		return StatusDescr;
	}

	public void setStatusDescr(String statusDescr) {
		StatusDescr = statusDescr;
	}

	public String getWorkType2Descr() {
		return WorkType2Descr;
	}

	public void setWorkType2Descr(String workType2Descr) {
		WorkType2Descr = workType2Descr;
	}

	public String getIsWithHoldDescr() {
		return IsWithHoldDescr;
	}

	public void setIsWithHoldDescr(String isWithHoldDescr) {
		IsWithHoldDescr = isWithHoldDescr;
	}

	public String getIsSignDescr() {
		return IsSignDescr;
	}

	public void setIsSignDescr(String isSignDescr) {
		IsSignDescr = isSignDescr;
	}

	public String getCheckStatusDescr() {
		return CheckStatusDescr;
	}

	public void setCheckStatusDescr(String checkStatusDescr) {
		CheckStatusDescr = checkStatusDescr;
	}

	public String getCustCtrl() {
		return CustCtrl;
	}

	public void setCustCtrl(String custCtrl) {
		CustCtrl = custCtrl;
	}

	public String getCustCost() {
		return CustCost;
	}

	public void setCustCost(String custCost) {
		CustCost = custCost;
	}

	public String getAllCustCtrl() {
		return AllCustCtrl;
	}

	public void setAllCustCtrl(String allCustCtrl) {
		AllCustCtrl = allCustCtrl;
	}

	public String getAllCustCost() {
		return AllCustCost;
	}

	public void setAllCustCost(String allCustCost) {
		AllCustCost = allCustCost;
	}

	public String getLeaveCustCost() {
		return LeaveCustCost;
	}

	public void setLeaveCustCost(String leaveCustCost) {
		LeaveCustCost = leaveCustCost;
	}

	public String getAllLeaveCustCost() {
		return AllLeaveCustCost;
	}

	public void setAllLeaveCustCost(String allLeaveCustCost) {
		AllLeaveCustCost = allLeaveCustCost;
	}

	public String getCustCtrl_Kzx() {
		return CustCtrl_Kzx;
	}

	public void setCustCtrl_Kzx(String custCtrl_Kzx) {
		CustCtrl_Kzx = custCtrl_Kzx;
	}

	public String getQualityFee() {
		return QualityFee;
	}

	public void setQualityFee(String qualityFee) {
		QualityFee = qualityFee;
	}

	public String getRealAmount() {
		return RealAmount;
	}

	public void setRealAmount(String realAmount) {
		RealAmount = realAmount;
	}

	public String getQualityFeeBegin() {
		return QualityFeeBegin;
	}

	public void setQualityFeeBegin(String qualityFeeBegin) {
		QualityFeeBegin = qualityFeeBegin;
	}

	public String getWorkCostDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(workCostDetailJson);
    	return xml;
	}

	public void setWorkCostDetailJson(String workCostDetailJson) {
		this.workCostDetailJson = workCostDetailJson;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public String getCustDescr() {
		return CustDescr;
	}

	public void setCustDescr(String custDescr) {
		CustDescr = custDescr;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getGotAmount() {
		return gotAmount;
	}

	public void setGotAmount(String gotAmount) {
		this.gotAmount = gotAmount;
	}

	public String getWithHoldCost() {
		return withHoldCost;
	}

	public void setWithHoldCost(String withHoldCost) {
		this.withHoldCost = withHoldCost;
	}

	public String getRcvCost() {
		return rcvCost;
	}

	public void setRcvCost(String rcvCost) {
		this.rcvCost = rcvCost;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getPrjItem() {
		return prjItem;
	}

	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}

	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}

	public String getConfirmCzy() {
		return confirmCzy;
	}

	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}

	public String getCardId2() {
		return cardId2;
	}

	public void setCardId2(String cardId2) {
		this.cardId2 = cardId2;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public String getCheckStatus() {
		return CheckStatus;
	}

	public void setCheckStatus(String checkStatus) {
		CheckStatus = checkStatus;
	}

	public String getYm() {
		return ym;
	}

	public void setYm(String ym) {
		this.ym = ym;
	}

	public String getCustCodes() {
		return custCodes;
	}

	public void setCustCodes(String custCodes) {
		this.custCodes = custCodes;
	}

	public String getWaterCustCodes() {
		return waterCustCodes;
	}

	public void setWaterCustCodes(String waterCustCodes) {
		this.waterCustCodes = waterCustCodes;
	}

	public String getIsWaterItemCtrl() {
		return isWaterItemCtrl;
	}

	public void setIsWaterItemCtrl(String isWaterItemCtrl) {
		this.isWaterItemCtrl = isWaterItemCtrl;
	}

	public String getIsWaterItemCtrlDescr() {
		return isWaterItemCtrlDescr;
	}

	public void setIsWaterItemCtrlDescr(String isWaterItemCtrlDescr) {
		this.isWaterItemCtrlDescr = isWaterItemCtrlDescr;
	}

	public String getWorkerPlanOffer() {
		return workerPlanOffer;
	}

	public void setWorkerPlanOffer(String workerPlanOffer) {
		this.workerPlanOffer = workerPlanOffer;
	}

	public String getIsSalary() {
		return isSalary;
	}

	public void setIsSalary(String isSalary) {
		this.isSalary = isSalary;
	}

	public String getIsFacingSubsidy() {
		return isFacingSubsidy;
	}

	public void setIsFacingSubsidy(String isFacingSubsidy) {
		this.isFacingSubsidy = isFacingSubsidy;
	}

	public String getRefCustCode() {
		return refCustCode;
	}

	public void setRefCustCode(String refCustCode) {
		this.refCustCode = refCustCode;
	}

	public String getRefCustDescr() {
		return refCustDescr;
	}

	public void setRefCustDescr(String refCustDescr) {
		this.refCustDescr = refCustDescr;
	}

	public String getRefAddress() {
		return refAddress;
	}

	public void setRefAddress(String refAddress) {
		this.refAddress = refAddress;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public String getIsFz() {
		return isFz;
	}

	public void setIsFz(String isFz) {
		this.isFz = isFz;
	}

	public String getNameChi3() {
		return nameChi3;
	}

	public void setNameChi3(String nameChi3) {
		this.nameChi3 = nameChi3;
	}

	public String getNameChi4() {
		return nameChi4;
	}

	public void setNameChi4(String nameChi4) {
		this.nameChi4 = nameChi4;
	}

	public String getCardId3() {
		return cardId3;
	}

	public void setCardId3(String cardId3) {
		this.cardId3 = cardId3;
	}

	public String getCardId4() {
		return cardId4;
	}

	public void setCardId4(String cardId4) {
		this.cardId4 = cardId4;
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

	public String getRefProjectMan() {
		return refProjectMan;
	}

	public void setRefProjectMan(String refProjectMan) {
		this.refProjectMan = refProjectMan;
	}

	public String getRefProjectManDescr() {
		return refProjectManDescr;
	}

	public void setRefProjectManDescr(String refProjectManDescr) {
		this.refProjectManDescr = refProjectManDescr;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getWorkCostImportItemNo() {
		return workCostImportItemNo;
	}

	public void setWorkCostImportItemNo(String workCostImportItemNo) {
		this.workCostImportItemNo = workCostImportItemNo;
	}

    public String getCustTypeDescr() {
        return custTypeDescr;
    }

    public void setCustTypeDescr(String custTypeDescr) {
        this.custTypeDescr = custTypeDescr;
    }

}
