package com.house.home.entity.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * prjCheck信息
 */

@Entity
@Table(name = "tPrjCheck")
public class PrjCheck extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "BaseChg")
	private Double baseChg;
	@Column(name = "BaseCtrlAmt")
	private Double baseCtrlAmt;
	@Column(name = "Cost")
	private Double cost;
	@Column(name = "WithHold")
	private Double withHold;
	@Column(name = "MainCoopFee")
	private Double mainCoopFee;
	@Column(name = "RecvFee")
	private Double recvFee;
	@Column(name = "QualityFee")
	private Double qualityFee;
	@Column(name = "AccidentFee")
	private Double accidentFee;
	@Column(name = "MustAmount")
	private Double mustAmount;
	@Column(name = "RealAmount")
	private Double realAmount;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "IsProvide")
	private String isProvide;
	@Column(name = "ProvideNo")
	private String provideNo;
	@Column(name = "ProvideSeq")
	private Integer provideSeq;
	@Column(name = "ProvideRemark")
	private String provideRemark;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "Date")
	private Date date;
	@Column(name = "ConfirmCZY")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "AllSetMinus")
	private Double allSetMinus;
	@Column(name = "AllSetAdd")
	private Double allSetAdd;
	@Column(name = "AllManageFee_Base")
	private Double allManageFeeBase;
	@Column(name = "AllItemAmount")
	private Double allItemAmount;
	@Column(name = "UpgWithHold")
	private Double upgWithHold;
	@Column(name = "BaseCost")
	private Double baseCost;
	@Column(name = "MainCost")
	private Double mainCost;
	@Column(name = "ctrlExprRemarks")
	private String ctrlExprRemarks;
	@Column(name = "ctrlExprWithNum")
	private String ctrlExprWithNum;
	@Column(name = "RecvFee_FixDuty")
	private Double recvFeeFixDuty;
	
	@Transient
	private String custDescr;
	@Transient
	private String address;
	@Transient
	private Double baseFeeDirct;
	@Transient
	private Double mainAmount;
	@Transient
	private Double baseFee;
	@Transient
	private Double chgMainAmount;
	@Transient
	private Double projectCtrlAdj; //发包补贴
	@Transient
	private String appCZYDescr;
	@Transient
	private String confirmCZYDescr;
	@Transient
	private String projectMan;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private String Status;
	@Transient
	private String custTypeType; //1清单客户，2套餐客户
	@Transient
	private Integer area;
	@Transient
	private Double mainSetFee;
	@Transient
	private Double longFee;
	@Transient
	private String checkStatus;
	@Transient
	private String hasCheckCZY;
	@Transient
	private String allDetailInfo;
	@Transient
	private String prjNo; 
	@Transient
	private Double remainQualityFee;
	@Transient
	private String prjCtrlType;
	@Transient
	private Double remainAccidentFee;
	@Transient
	private Double designFixDutyAmount;
	
	public Double getDesignFixDutyAmount() {
		return designFixDutyAmount;
	}

	public void setDesignFixDutyAmount(Double designFixDutyAmount) {
		this.designFixDutyAmount = designFixDutyAmount;
	}

	public Double getRemainAccidentFee() {
		return remainAccidentFee;
	}

	public void setRemainAccidentFee(Double remainAccidentFee) {
		this.remainAccidentFee = remainAccidentFee;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setBaseChg(Double baseChg) {
		this.baseChg = baseChg;
	}
	
	public Double getBaseChg() {
		return this.baseChg;
	}
	public void setBaseCtrlAmt(Double baseCtrlAmt) {
		this.baseCtrlAmt = baseCtrlAmt;
	}
	
	public Double getBaseCtrlAmt() {
		return this.baseCtrlAmt;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Double getCost() {
		return this.cost;
	}
	public void setWithHold(Double withHold) {
		this.withHold = withHold;
	}
	
	public Double getWithHold() {
		return this.withHold;
	}
	public void setMainCoopFee(Double mainCoopFee) {
		this.mainCoopFee = mainCoopFee;
	}
	
	public Double getMainCoopFee() {
		return this.mainCoopFee;
	}
	public void setRecvFee(Double recvFee) {
		this.recvFee = recvFee;
	}
	
	public Double getRecvFee() {
		return this.recvFee;
	}
	public void setQualityFee(Double qualityFee) {
		this.qualityFee = qualityFee;
	}
	
	public Double getQualityFee() {
		return this.qualityFee;
	}
	public void setAccidentFee(Double accidentFee) {
		this.accidentFee = accidentFee;
	}
	
	public Double getAccidentFee() {
		return this.accidentFee;
	}
	public void setMustAmount(Double mustAmount) {
		this.mustAmount = mustAmount;
	}
	
	public Double getMustAmount() {
		return this.mustAmount;
	}
	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
	}
	
	public Double getRealAmount() {
		return this.realAmount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setIsProvide(String isProvide) {
		this.isProvide = isProvide;
	}
	
	public String getIsProvide() {
		return this.isProvide;
	}
	public void setProvideNo(String provideNo) {
		this.provideNo = provideNo;
	}
	
	public String getProvideNo() {
		return this.provideNo;
	}
	public void setProvideSeq(Integer provideSeq) {
		this.provideSeq = provideSeq;
	}
	
	public Integer getProvideSeq() {
		return this.provideSeq;
	}
	public void setProvideRemark(String provideRemark) {
		this.provideRemark = provideRemark;
	}
	
	public String getProvideRemark() {
		return this.provideRemark;
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
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
	public String getConfirmCzy() {
		return this.confirmCzy;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	public void setAllSetMinus(Double allSetMinus) {
		this.allSetMinus = allSetMinus;
	}
	
	public Double getAllSetMinus() {
		return this.allSetMinus;
	}
	public void setAllSetAdd(Double allSetAdd) {
		this.allSetAdd = allSetAdd;
	}
	
	public Double getAllSetAdd() {
		return this.allSetAdd;
	}
	public void setAllManageFeeBase(Double allManageFeeBase) {
		this.allManageFeeBase = allManageFeeBase;
	}
	
	public Double getAllManageFeeBase() {
		return this.allManageFeeBase;
	}
	public void setAllItemAmount(Double allItemAmount) {
		this.allItemAmount = allItemAmount;
	}
	
	public Double getAllItemAmount() {
		return this.allItemAmount;
	}
	public void setUpgWithHold(Double upgWithHold) {
		this.upgWithHold = upgWithHold;
	}
	
	public Double getUpgWithHold() {
		return this.upgWithHold;
	}
	public void setBaseCost(Double baseCost) {
		this.baseCost = baseCost;
	}
	
	public Double getBaseCost() {
		return this.baseCost;
	}
	public void setMainCost(Double mainCost) {
		this.mainCost = mainCost;
	}
	
	public Double getMainCost() {
		return this.mainCost;
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

	public Double getBaseFeeDirct() {
		return baseFeeDirct;
	}

	public void setBaseFeeDirct(Double baseFeeDirct) {
		this.baseFeeDirct = baseFeeDirct;
	}

	public Double getMainAmount() {
		return mainAmount;
	}

	public void setMainAmount(Double mainAmount) {
		this.mainAmount = mainAmount;
	}

	public Double getBaseFee() {
		return baseFee;
	}

	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}

	public Double getChgMainAmount() {
		return chgMainAmount;
	}

	public void setChgMainAmount(Double chgMainAmount) {
		this.chgMainAmount = chgMainAmount;
	}

	public Double getProjectCtrlAdj() {
		return projectCtrlAdj;
	}

	public void setProjectCtrlAdj(Double projectCtrlAdj) {
		this.projectCtrlAdj = projectCtrlAdj;
	}

	public String getAppCZYDescr() {
		return appCZYDescr;
	}

	public void setAppCZYDescr(String appCZYDescr) {
		this.appCZYDescr = appCZYDescr;
	}

	public String getConfirmCZYDescr() {
		return confirmCZYDescr;
	}

	public void setConfirmCZYDescr(String confirmCZYDescr) {
		this.confirmCZYDescr = confirmCZYDescr;
	}

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
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

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getCustTypeType() {
		return custTypeType;
	}

	public void setCustTypeType(String custTypeType) {
		this.custTypeType = custTypeType;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Double getMainSetFee() {
		return mainSetFee;
	}

	public void setMainSetFee(Double mainSetFee) {
		this.mainSetFee = mainSetFee;
	}

	public Double getLongFee() {
		return longFee;
	}

	public void setLongFee(Double longFee) {
		this.longFee = longFee;
	}

	public String getCtrlExprRemarks() {
		return ctrlExprRemarks;
	}

	public void setCtrlExprRemarks(String ctrlExprRemarks) {
		this.ctrlExprRemarks = ctrlExprRemarks;
	}

	public String getCtrlExprWithNum() {
		return ctrlExprWithNum;
	}

	public void setCtrlExprWithNum(String ctrlExprWithNum) {
		this.ctrlExprWithNum = ctrlExprWithNum;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getHasCheckCZY() {
		return hasCheckCZY;
	}

	public void setHasCheckCZY(String hasCheckCZY) {
		this.hasCheckCZY = hasCheckCZY;
	}

	public String getAllDetailInfo() {
		return allDetailInfo;
	}

	public void setAllDetailInfo(String allDetailInfo) {
		this.allDetailInfo = allDetailInfo;
	}
	//用来标识prjPrjprovide详细信息的访问

	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	public Double getRemainQualityFee() {
		return remainQualityFee;
	}

	public void setRemainQualityFee(Double remainQualityFee) {
		this.remainQualityFee = remainQualityFee;
	}

	public String getPrjCtrlType() {
		return prjCtrlType;
	}

	public void setPrjCtrlType(String prjCtrlType) {
		this.prjCtrlType = prjCtrlType;
	}

	public Double getRecvFeeFixDuty() {
		return recvFeeFixDuty;
	}

	public void setRecvFeeFixDuty(Double recvFeeFixDuty) {
		this.recvFeeFixDuty = recvFeeFixDuty;
	}
	
}
