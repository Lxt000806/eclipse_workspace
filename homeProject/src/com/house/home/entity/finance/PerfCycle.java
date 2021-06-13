package com.house.home.entity.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * PerfCycle信息
 */
@Entity
@Table(name = "tPerfCycle")
public class PerfCycle extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Y")
	private Integer y;
	@Column(name = "M")
	private Integer m;
	@Column(name = "Season")
	private Integer season;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
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
	@Column(name = "Remarks")
	private String remarks;
	
	@Transient
	private Date crtDateFrom;
	@Transient
	private Date crtDateTo;
	@Transient
	private Date achieveDateFrom;
	@Transient
	private Date achieveDateTo;
	@Transient
	private String deptType;
	@Transient
	private String department1;
	@Transient
	private String department2;
	@Transient
	private String department3;
	@Transient
	private String custType;
	@Transient
	private String region;
	@Transient
	private String empCode;
	@Transient
	private String address;
	@Transient
	private String busiType;
	@Transient
	private String perfType;
	@Transient
	private String isChecked;
	@Transient
	private String documentNo;
	@Transient
	private String roleBox;
	@Transient
	private String custCode;
	@Transient
	private String isCalcPersonPerf;
	@Transient
	private String isCalcPersonPerfDescr;
	@Transient
	private String isModified;
	@Transient
	private String dataType;
	@Transient
	private String isCalcDeptPerf;
	@Transient
	private String isCalcDeptPerfDescr;
	@Transient
	private String isCalPkPerf;
	@Transient
	private String businessMan;
	@Transient
	private String designMan;
	@Transient
	private String againMan;
	@Transient
	private String chkNoLeader;
	@Transient
	private String tileStatus;
	@Transient
	private String bathStatus;
	@Transient
	private String custDescr;
	@Transient
	private String custStatus;
	@Transient
	private Date achieveDate;
	@Transient
	private String isCalcPerf;
	@Transient
	private Date signDate;
	@Transient
	private String selectedTab;//选中tab中的tableid
	@Transient
	private Integer manualPerfCustPk;
	@Transient
	private Integer perfPk;
	@Transient
	private String role;
	@Transient
	private String perfPer;
	@Transient
	private String leaderCode;
	@Transient
	private String busiDrc;
	@Transient
	private String busiDrcDescr;
	@Transient
	private String leaderName;
	@Transient
	private String empName;
	@Transient
	private String dept1Descr;
	@Transient
	private String dept2Descr;
	@Transient
	private String dept3Descr;
	@Transient
	private String roleDescr;
	@Transient
	private Integer pk;
	@Transient
	private String chgNos;
	@Transient
	private String chgNo;
	@Transient
	private String chgType;
	@Transient
	private String number;
	@Transient
	private String payType;
	@Transient
	private String perfExpr;
	@Transient
	private String regPerfPk;
	@Transient
	private String openCount;//查看原业绩打开次数
	@Transient
	private String perfCycleNo;
	@Transient
	private String type;
	@Transient
	private double quantity;
	@Transient
	private Integer area;
	@Transient
	private double designFee;
	@Transient
	private double basePlan;
	@Transient
	private double manageFee_Base;
	@Transient
	private double mainPlan;
	@Transient
	private double integratePlan;
	@Transient
	private double cupboardPlan;
	@Transient
	private double softPlan;
	@Transient
	private double mainServPlan;
	@Transient
	private double baseDisc;
	@Transient
	private double contractFee;
	@Transient
	private double longFee;
	@Transient
	private double softFee_Furniture;
	@Transient
	private double perfAmount;
	@Transient
	private double firstPay;
	@Transient
	private double mustReceive;
	@Transient
	private double realReceive;
	@Transient
	private double mainProPer;
	@Transient
	private Date setDate;
	@Transient
	private double setMinus;
	@Transient
	private double manageFee_InSet;
	@Transient
	private double manageFee_Main;
	@Transient
	private double manageFee_Int;
	@Transient
	private double manageFee_Serv;
	@Transient
	private double manageFee_Soft;
	@Transient
	private double manageFee_Cup;
	@Transient
	private double tileDeduction;
	@Transient
	private double bathDeduction;
	@Transient
	private double mainDeduction;
	@Transient
	private double recalPerf;
	@Transient
	private double perfPerc;
	@Transient
	private double perfDisc;
	@Transient
	private double markup;
	@Transient
	private double softTokenAmount;
	@Transient
	private double baseDeduction;
	@Transient
	private double itemDeduction;
	@Transient
	private double basePerfPer;
	@Transient
	private double marketFund;
	@Transient
	private double realMaterPerf;
	@Transient
	private double maxMaterPerf;
	@Transient
	private double alreadyMaterPerf;
	@Transient
	private double materPerf;
	@Transient
	private String gift;
	@Transient
	private String perfExprRemarks;
	@Transient
	private String gxrDetailJson;
	@Transient
	private String jczjDetailJson;
	@Transient
	private String clzjDetailJson;
	@Transient
	private String htfyzjDetailJson;
	@Transient
	private String baseChgNos;
	@Transient
	private String itemChgNos;
	@Transient
	private Date personChgDateFrom;
	@Transient
	private Date personChgDateTo;
	@Transient
	private String checkStatus;
	@Transient
	private double perfMarkup;
	@Transient
	private String source;
	@Transient
	private String pks;
	@Transient
	private String isInitSign;
	@Transient
	private double tax;
	@Transient
	private String isAddAllInfo;
	@Transient
	private String baseChgStakeholder;
	@Transient
	private String baseChgNo;
	@Transient
	private String payeeCode;
	@Transient
	private String expired_dept;
	@Transient
	private String level;//部门级别
	@Transient
	private String departmentDescr;//部门名称
	@Transient
	private String teamCode;//团队编号
	@Transient
	private String chgStakeholder;
	@Transient
	private Double setAdd;
	@Transient
	private String delPks;
	@Transient
	private String freeBaseItem;
	
	@Transient
	private String expired_empForPerf; //业绩人员过期
	@Transient
	private double basePersonalPlan;
	@Transient
	private double manageFee_basePersonal;
	@Transient
	private double woodPlan;
	@Transient
	private double manageFee_wood;
	@Transient
	private double contractAndTax;
	@Transient
	private String perfDateType; // 1、 旧业绩、2、新业绩
	
	public String getDelPks() {
		return delPks;
	}

	public void setDelPks(String delPks) {
		this.delPks = delPks;
	}

	public Double getSetAdd() {
		return setAdd;
	}

	public void setSetAdd(Double setAdd) {
		this.setAdd = setAdd;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	
	public Integer getY() {
		return this.y;
	}
	public void setM(Integer m) {
		this.m = m;
	}
	
	public Integer getM() {
		return this.m;
	}
	public void setSeason(Integer season) {
		this.season = season;
	}
	
	public Integer getSeason() {
		return this.season;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getBeginDate() {
		return this.beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
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

	public Date getCrtDateFrom() {
		return crtDateFrom;
	}

	public void setCrtDateFrom(Date crtDateFrom) {
		this.crtDateFrom = crtDateFrom;
	}

	public Date getCrtDateTo() {
		return crtDateTo;
	}

	public void setCrtDateTo(Date crtDateTo) {
		this.crtDateTo = crtDateTo;
	}

	public Date getAchieveDateFrom() {
		return achieveDateFrom;
	}

	public void setAchieveDateFrom(Date achieveDateFrom) {
		this.achieveDateFrom = achieveDateFrom;
	}

	public Date getAchieveDateTo() {
		return achieveDateTo;
	}

	public void setAchieveDateTo(Date achieveDateTo) {
		this.achieveDateTo = achieveDateTo;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
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

	public String getDepartment3() {
		return department3;
	}

	public void setDepartment3(String department3) {
		this.department3 = department3;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getPerfType() {
		return perfType;
	}

	public void setPerfType(String perfType) {
		this.perfType = perfType;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getRoleBox() {
		return roleBox;
	}

	public void setRoleBox(String roleBox) {
		this.roleBox = roleBox;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getIsCalcPersonPerf() {
		return isCalcPersonPerf;
	}

	public void setIsCalcPersonPerf(String isCalcPersonPerf) {
		this.isCalcPersonPerf = isCalcPersonPerf;
	}

	public String getIsModified() {
		return isModified;
	}

	public void setIsModified(String isModified) {
		this.isModified = isModified;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getIsCalcDeptPerf() {
		return isCalcDeptPerf;
	}

	public void setIsCalcDeptPerf(String isCalcDeptPerf) {
		this.isCalcDeptPerf = isCalcDeptPerf;
	}

	public String getIsCalPkPerf() {
		return isCalPkPerf;
	}

	public void setIsCalPkPerf(String isCalPkPerf) {
		this.isCalPkPerf = isCalPkPerf;
	}

	public String getBusinessMan() {
		return businessMan;
	}

	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}

	public String getDesignMan() {
		return designMan;
	}

	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}

	public String getAgainMan() {
		return againMan;
	}

	public void setAgainMan(String againMan) {
		this.againMan = againMan;
	}

	public String getChkNoLeader() {
		return chkNoLeader;
	}

	public void setChkNoLeader(String chkNoLeader) {
		this.chkNoLeader = chkNoLeader;
	}

	public String getTileStatus() {
		return tileStatus;
	}

	public void setTileStatus(String tileStatus) {
		this.tileStatus = tileStatus;
	}

	public String getBathStatus() {
		return bathStatus;
	}

	public void setBathStatus(String bathStatus) {
		this.bathStatus = bathStatus;
	}

	public String getCustDescr() {
		return custDescr;
	}

	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}

	public String getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsCalcPerf() {
		return isCalcPerf;
	}

	public void setIsCalcPerf(String isCalcPerf) {
		this.isCalcPerf = isCalcPerf;
	}

	public Date getAchieveDate() {
		return achieveDate;
	}

	public void setAchieveDate(Date achieveDate) {
		this.achieveDate = achieveDate;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

	public Integer getManualPerfCustPk() {
		return manualPerfCustPk;
	}

	public void setManualPerfCustPk(Integer manualPerfCustPk) {
		this.manualPerfCustPk = manualPerfCustPk;
	}

	public Integer getPerfPk() {
		return perfPk;
	}

	public void setPerfPk(Integer perfPk) {
		this.perfPk = perfPk;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPerfPer() {
		return perfPer;
	}

	public void setPerfPer(String perfPer) {
		this.perfPer = perfPer;
	}

	public String getLeaderCode() {
		return leaderCode;
	}

	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}

	public String getBusiDrc() {
		return busiDrc;
	}

	public void setBusiDrc(String busiDrc) {
		this.busiDrc = busiDrc;
	}

	public String getBusiDrcDescr() {
		return busiDrcDescr;
	}

	public void setBusiDrcDescr(String busiDrcDescr) {
		this.busiDrcDescr = busiDrcDescr;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDept1Descr() {
		return dept1Descr;
	}

	public void setDept1Descr(String dept1Descr) {
		this.dept1Descr = dept1Descr;
	}

	public String getDept2Descr() {
		return dept2Descr;
	}

	public void setDept2Descr(String dept2Descr) {
		this.dept2Descr = dept2Descr;
	}

	public String getDept3Descr() {
		return dept3Descr;
	}

	public void setDept3Descr(String dept3Descr) {
		this.dept3Descr = dept3Descr;
	}

	public String getRoleDescr() {
		return roleDescr;
	}

	public void setRoleDescr(String roleDescr) {
		this.roleDescr = roleDescr;
	}

	public String getIsCalcPersonPerfDescr() {
		return isCalcPersonPerfDescr;
	}

	public void setIsCalcPersonPerfDescr(String isCalcPersonPerfDescr) {
		this.isCalcPersonPerfDescr = isCalcPersonPerfDescr;
	}

	public String getIsCalcDeptPerfDescr() {
		return isCalcDeptPerfDescr;
	}

	public void setIsCalcDeptPerfDescr(String isCalcDeptPerfDescr) {
		this.isCalcDeptPerfDescr = isCalcDeptPerfDescr;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getChgNos() {
		return chgNos;
	}

	public void setChgNos(String chgNos) {
		this.chgNos = chgNos;
	}

	public String getChgNo() {
		return chgNo;
	}

	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}

	public String getChgType() {
		return chgType;
	}

	public void setChgType(String chgType) {
		this.chgType = chgType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPerfExpr() {
		return perfExpr;
	}

	public void setPerfExpr(String perfExpr) {
		this.perfExpr = perfExpr;
	}

	public String getRegPerfPk() {
		return regPerfPk;
	}

	public void setRegPerfPk(String regPerfPk) {
		this.regPerfPk = regPerfPk;
	}

	public String getOpenCount() {
		return openCount;
	}

	public void setOpenCount(String openCount) {
		this.openCount = openCount;
	}

	public String getPerfCycleNo() {
		return perfCycleNo;
	}

	public void setPerfCycleNo(String perfCycleNo) {
		this.perfCycleNo = perfCycleNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public double getDesignFee() {
		return designFee;
	}

	public void setDesignFee(double designFee) {
		this.designFee = designFee;
	}

	public double getBasePlan() {
		return basePlan;
	}

	public void setBasePlan(double basePlan) {
		this.basePlan = basePlan;
	}

	public double getManageFee_Base() {
		return manageFee_Base;
	}

	public void setManageFee_Base(double manageFee_Base) {
		this.manageFee_Base = manageFee_Base;
	}

	public double getMainPlan() {
		return mainPlan;
	}

	public void setMainPlan(double mainPlan) {
		this.mainPlan = mainPlan;
	}

	public double getIntegratePlan() {
		return integratePlan;
	}

	public void setIntegratePlan(double integratePlan) {
		this.integratePlan = integratePlan;
	}

	public double getCupboardPlan() {
		return cupboardPlan;
	}

	public void setCupboardPlan(double cupboardPlan) {
		this.cupboardPlan = cupboardPlan;
	}

	public double getSoftPlan() {
		return softPlan;
	}

	public void setSoftPlan(double softPlan) {
		this.softPlan = softPlan;
	}

	public double getMainServPlan() {
		return mainServPlan;
	}

	public void setMainServPlan(double mainServPlan) {
		this.mainServPlan = mainServPlan;
	}

	public double getBaseDisc() {
		return baseDisc;
	}

	public void setBaseDisc(double baseDisc) {
		this.baseDisc = baseDisc;
	}

	public double getContractFee() {
		return contractFee;
	}

	public void setContractFee(double contractFee) {
		this.contractFee = contractFee;
	}

	public double getLongFee() {
		return longFee;
	}

	public void setLongFee(double longFee) {
		this.longFee = longFee;
	}

	public double getSoftFee_Furniture() {
		return softFee_Furniture;
	}

	public void setSoftFee_Furniture(double softFee_Furniture) {
		this.softFee_Furniture = softFee_Furniture;
	}

	public double getPerfAmount() {
		return perfAmount;
	}

	public void setPerfAmount(double perfAmount) {
		this.perfAmount = perfAmount;
	}

	public double getFirstPay() {
		return firstPay;
	}

	public void setFirstPay(double firstPay) {
		this.firstPay = firstPay;
	}

	public double getMustReceive() {
		return mustReceive;
	}

	public void setMustReceive(double mustReceive) {
		this.mustReceive = mustReceive;
	}

	public double getRealReceive() {
		return realReceive;
	}

	public void setRealReceive(double realReceive) {
		this.realReceive = realReceive;
	}

	public double getMainProPer() {
		return mainProPer;
	}

	public void setMainProPer(double mainProPer) {
		this.mainProPer = mainProPer;
	}

	public Date getSetDate() {
		return setDate;
	}

	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

	public double getSetMinus() {
		return setMinus;
	}

	public void setSetMinus(double setMinus) {
		this.setMinus = setMinus;
	}

	public double getManageFee_InSet() {
		return manageFee_InSet;
	}

	public void setManageFee_InSet(double manageFee_InSet) {
		this.manageFee_InSet = manageFee_InSet;
	}

	public double getManageFee_Main() {
		return manageFee_Main;
	}

	public void setManageFee_Main(double manageFee_Main) {
		this.manageFee_Main = manageFee_Main;
	}

	public double getManageFee_Int() {
		return manageFee_Int;
	}

	public void setManageFee_Int(double manageFee_Int) {
		this.manageFee_Int = manageFee_Int;
	}

	public double getManageFee_Serv() {
		return manageFee_Serv;
	}

	public void setManageFee_Serv(double manageFee_Serv) {
		this.manageFee_Serv = manageFee_Serv;
	}

	public double getManageFee_Soft() {
		return manageFee_Soft;
	}

	public void setManageFee_Soft(double manageFee_Soft) {
		this.manageFee_Soft = manageFee_Soft;
	}

	public double getManageFee_Cup() {
		return manageFee_Cup;
	}

	public void setManageFee_Cup(double manageFee_Cup) {
		this.manageFee_Cup = manageFee_Cup;
	}

	public double getTileDeduction() {
		return tileDeduction;
	}

	public void setTileDeduction(double tileDeduction) {
		this.tileDeduction = tileDeduction;
	}

	public double getBathDeduction() {
		return bathDeduction;
	}

	public void setBathDeduction(double bathDeduction) {
		this.bathDeduction = bathDeduction;
	}

	public double getMainDeduction() {
		return mainDeduction;
	}

	public void setMainDeduction(double mainDeduction) {
		this.mainDeduction = mainDeduction;
	}

	public double getRecalPerf() {
		return recalPerf;
	}

	public void setRecalPerf(double recalPerf) {
		this.recalPerf = recalPerf;
	}

	public double getPerfPerc() {
		return perfPerc;
	}

	public void setPerfPerc(double perfPerc) {
		this.perfPerc = perfPerc;
	}

	public double getPerfDisc() {
		return perfDisc;
	}

	public void setPerfDisc(double perfDisc) {
		this.perfDisc = perfDisc;
	}

	public double getMarkup() {
		return markup;
	}

	public void setMarkup(double markup) {
		this.markup = markup;
	}

	public double getSoftTokenAmount() {
		return softTokenAmount;
	}

	public void setSoftTokenAmount(double softTokenAmount) {
		this.softTokenAmount = softTokenAmount;
	}

	public double getBaseDeduction() {
		return baseDeduction;
	}

	public void setBaseDeduction(double baseDeduction) {
		this.baseDeduction = baseDeduction;
	}

	public double getItemDeduction() {
		return itemDeduction;
	}

	public void setItemDeduction(double itemDeduction) {
		this.itemDeduction = itemDeduction;
	}

	public double getBasePerfPer() {
		return basePerfPer;
	}

	public void setBasePerfPer(double basePerfPer) {
		this.basePerfPer = basePerfPer;
	}

	public double getMarketFund() {
		return marketFund;
	}

	public void setMarketFund(double marketFund) {
		this.marketFund = marketFund;
	}

	public double getRealMaterPerf() {
		return realMaterPerf;
	}

	public void setRealMaterPerf(double realMaterPerf) {
		this.realMaterPerf = realMaterPerf;
	}

	public double getMaxMaterPerf() {
		return maxMaterPerf;
	}

	public void setMaxMaterPerf(double maxMaterPerf) {
		this.maxMaterPerf = maxMaterPerf;
	}

	public double getAlreadyMaterPerf() {
		return alreadyMaterPerf;
	}

	public void setAlreadyMaterPerf(double alreadyMaterPerf) {
		this.alreadyMaterPerf = alreadyMaterPerf;
	}

	public double getMaterPerf() {
		return materPerf;
	}

	public void setMaterPerf(double materPerf) {
		this.materPerf = materPerf;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public String getPerfExprRemarks() {
		return perfExprRemarks;
	}

	public void setPerfExprRemarks(String perfExprRemarks) {
		this.perfExprRemarks = perfExprRemarks;
	}

	public String getGxrDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(gxrDetailJson);
    	return xml;
	}

	public void setGxrDetailJson(String gxrDetailJson) {
		this.gxrDetailJson = gxrDetailJson;
	}

	public String getJczjDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(jczjDetailJson);
    	return xml;
	}

	public void setJczjDetailJson(String jczjDetailJson) {
		this.jczjDetailJson = jczjDetailJson;
	}

	public String getClzjDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(clzjDetailJson);
    	return xml;
	}

	public void setClzjDetailJson(String clzjDetailJson) {
		this.clzjDetailJson = clzjDetailJson;
	}

	public String getHtfyzjDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(htfyzjDetailJson);
    	return xml;
	}

	public void setHtfyzjDetailJson(String htfyzjDetailJson) {
		this.htfyzjDetailJson = htfyzjDetailJson;
	}

	public String getBaseChgNos() {
		return baseChgNos;
	}

	public void setBaseChgNos(String baseChgNos) {
		this.baseChgNos = baseChgNos;
	}

	public String getItemChgNos() {
		return itemChgNos;
	}

	public void setItemChgNos(String itemChgNos) {
		this.itemChgNos = itemChgNos;
	}

	public Date getPersonChgDateFrom() {
		return personChgDateFrom;
	}

	public void setPersonChgDateFrom(Date personChgDateFrom) {
		this.personChgDateFrom = personChgDateFrom;
	}

	public Date getPersonChgDateTo() {
		return personChgDateTo;
	}

	public void setPersonChgDateTo(Date personChgDateTo) {
		this.personChgDateTo = personChgDateTo;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public double getPerfMarkup() {
		return perfMarkup;
	}

	public void setPerfMarkup(double perfMarkup) {
		this.perfMarkup = perfMarkup;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPks() {
		return pks;
	}

	public void setPks(String pks) {
		this.pks = pks;
	}

	public String getIsInitSign() {
		return isInitSign;
	}

	public void setIsInitSign(String isInitSign) {
		this.isInitSign = isInitSign;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public String getIsAddAllInfo() {
		return isAddAllInfo;
	}

	public void setIsAddAllInfo(String isAddAllInfo) {
		this.isAddAllInfo = isAddAllInfo;
	}

	public String getBaseChgStakeholder() {
		return baseChgStakeholder;
	}

	public void setBaseChgStakeholder(String baseChgStakeholder) {
		this.baseChgStakeholder = baseChgStakeholder;
	}

	public String getBaseChgNo() {
		return baseChgNo;
	}

	public void setBaseChgNo(String baseChgNo) {
		this.baseChgNo = baseChgNo;
	}

	public String getExpired_dept() {
		return expired_dept;
	}

	public void setExpired_dept(String expired_dept) {
		this.expired_dept = expired_dept;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDepartmentDescr() {
		return departmentDescr;
	}

	public void setDepartmentDescr(String departmentDescr) {
		this.departmentDescr = departmentDescr;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getChgStakeholder() {
		return chgStakeholder;
	}

	public void setChgStakeholder(String chgStakeholder) {
		this.chgStakeholder = chgStakeholder;
	}

	public String getFreeBaseItem() {
		return freeBaseItem;
	}

	public void setFreeBaseItem(String freeBaseItem) {
		this.freeBaseItem = freeBaseItem;
	}

	public String getExpired_empForPerf() {
		return expired_empForPerf;
	}

	public void setExpired_empForPerf(String expired_empForPerf) {
		this.expired_empForPerf = expired_empForPerf;
	}

	public double getBasePersonalPlan() {
		return basePersonalPlan;
	}

	public void setBasePersonalPlan(double basePersonalPlan) {
		this.basePersonalPlan = basePersonalPlan;
	}

	public double getManageFee_basePersonal() {
		return manageFee_basePersonal;
	}

	public void setManageFee_basePersonal(double manageFee_basePersonal) {
		this.manageFee_basePersonal = manageFee_basePersonal;
	}

	public double getWoodPlan() {
		return woodPlan;
	}

	public void setWoodPlan(double woodPlan) {
		this.woodPlan = woodPlan;
	}

	public double getManageFee_wood() {
		return manageFee_wood;
	}

	public void setManageFee_wood(double manageFee_wood) {
		this.manageFee_wood = manageFee_wood;
	}

	public double getContractAndTax() {
		return contractAndTax;
	}

	public void setContractAndTax(double contractAndTax) {
		this.contractAndTax = contractAndTax;
	}

	public String getPerfDateType() {
		return perfDateType;
	}

	public void setPerfDateType(String perfDateType) {
		this.perfDateType = perfDateType;
	}
	
		
}
