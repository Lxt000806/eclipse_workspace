package com.house.home.entity.salary;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * SalaryEmp信息
 */
@Entity
@Table(name = "tSalaryEmp")
public class SalaryEmp extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "EmpCode")
	private String empCode;
	@Column(name = "EmpName")
	private String empName;
	@Column(name = "Category")
	private String category;
	@Column(name = "IDNum")
	private String idnum;
	@Column(name = "PosiClass")
	private Integer posiClass;
	@Column(name = "PosiLevel")
	private Integer posiLevel;
	@Column(name = "JoinDate")
	private Date joinDate;
	@Column(name = "RegularDate")
	private Date regularDate;
	@Column(name = "LeaveDate")
	private Date leaveDate;
	@Column(name = "Status")
	private String status;
	@Column(name = "ConSignCmp")
	private String conSignCmp;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "Department2")
	private String department2;
	@Column(name = "FinancialCode")
	private String financialCode;
	@Column(name = "BasicSalarySetMode")
	private String basicSalarySetMode;
	@Column(name = "BasicSalary")
	private Double basicSalary;
	@Column(name = "Salary")
	private Double salary;
	@Column(name = "SocialInsurParam")
	private Integer socialInsurParam;
	@Column(name = "EdmInsurMon")
	private Integer edmInsurMon;
	@Column(name = "MedInsurMon")
	private Integer medInsurMon;
	@Column(name = "HouFundMon")
	private Integer houFundMon;
	@Column(name = "PayMode")
	private String payMode;
	@Column(name = "InsurLimit")
	private Double insurLimit;
	@Column(name = "SalarySettleCmp")
	private Integer salarySettleCmp;
	@Column(name = "CmpUsageType")
	private String cmpUsageType;
	@Column(name = "SalaryChgDate")
	private Date salaryChgDate;
	@Column(name = "SalaryStatus")
	private String salaryStatus;
	@Column(name = "IsTaxable")
	private String isTaxable;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IsFront")
	private String isFront;
	@Column(name = "IsSocialInsur")
	private String isSocialInsur;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "WorkingDays")
	private Integer workingDays;
	@Column(name = "Position")
	private String position;
	@Column(name = "InsurBase")
	private Double insurBase;
	@Column(name = "PosiSalary")
	private Double posiSalary;
	@Column(name = "SkillSubsidy")
	private Double skillSubsidy;
	@Column(name = "OtherBonuse")
	private Double otherBonuse;
	@Column(name = "PerfBonuse")
	private Double perfBonuse;
	@Column(name = "StarSubsidy")
	private Double starSubsidy;
	@Column(name = "OtherSubsidy")
	private Double otherSubsidy;
	@Column(name = "PosiChgDate")
	private Date posiChgDate;
	@Column(name = "Cash")
	private Double cash;
	@Column(name = "IsBasicSalaryPayment")
	private String isBasicSalaryPayment;
	@Column(name="BelongType")
	private String belongType;
	@Transient
	private String bankType;
	@Transient
	private String actName;
	@Transient
	private String cardId;
	@Transient
	private String bankDetailJson;
	@Transient
	private String payCmp1;
	@Transient
	private Double weight1;
	@Transient
	private String payCmp2;
	@Transient
	private Double weight2;
	@Transient
	private String payCmp3;
	@Transient
	private Double weight3;
	@Transient
	private String payCmp4;
	@Transient
	private Double weight4;
	@Transient
	private Double basicSalaryByLevel;
	@Transient
	private Double salaryByLevel;
	@Transient
	private Date joinDateFrom;
	@Transient
	private Date joinDateTo;
	@Transient
	private String queryCondition;
	@Transient
	private String scopeType;
	@Transient
	private String scopeOperate;
	@Transient
	private String scopeNum;
	@Transient
	private String salaryScheme;
	@Transient
	private String chgType;
	@Transient
	private String isSeries;
	@Transient
	private String empCodes;
	@Transient
	private String dateType;
	@Transient
	private String bankTypeDescr;
	
	public String getBelongType() {
		return belongType;
	}

	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}

	public String getBankTypeDescr() {
		return bankTypeDescr;
	}

	public void setBankTypeDescr(String bankTypeDescr) {
		this.bankTypeDescr = bankTypeDescr;
	}

	public String getEmpCodes() {
		return empCodes;
	}

	public void setEmpCodes(String empCodes) {
		this.empCodes = empCodes;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getEmpCode() {
		return this.empCode;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public String getEmpName() {
		return this.empName;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	
	public String getIdnum() {
		return this.idnum;
	}
	public void setPosiClass(Integer posiClass) {
		this.posiClass = posiClass;
	}
	
	public Integer getPosiClass() {
		return this.posiClass;
	}
	public void setPosiLevel(Integer posiLevel) {
		this.posiLevel = posiLevel;
	}
	
	public Integer getPosiLevel() {
		return this.posiLevel;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	public Date getJoinDate() {
		return this.joinDate;
	}
	public void setRegularDate(Date regularDate) {
		this.regularDate = regularDate;
	}
	
	public Date getRegularDate() {
		return this.regularDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	
	public Date getLeaveDate() {
		return this.leaveDate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setConSignCmp(String conSignCmp) {
		this.conSignCmp = conSignCmp;
	}
	
	public String getConSignCmp() {
		return this.conSignCmp;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	
	public String getDepartment1() {
		return this.department1;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	
	public String getDepartment2() {
		return this.department2;
	}
	public void setFinancialCode(String financialCode) {
		this.financialCode = financialCode;
	}
	
	public String getFinancialCode() {
		return this.financialCode;
	}
	public void setBasicSalarySetMode(String basicSalarySetMode) {
		this.basicSalarySetMode = basicSalarySetMode;
	}
	
	public String getBasicSalarySetMode() {
		return this.basicSalarySetMode;
	}
	public void setBasicSalary(Double basicSalary) {
		this.basicSalary = basicSalary;
	}
	
	public Double getBasicSalary() {
		return this.basicSalary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	public Double getSalary() {
		return this.salary;
	}
	public void setSocialInsurParam(Integer socialInsurParam) {
		this.socialInsurParam = socialInsurParam;
	}
	
	public Integer getSocialInsurParam() {
		return this.socialInsurParam;
	}
	public void setEdmInsurMon(Integer edmInsurMon) {
		this.edmInsurMon = edmInsurMon;
	}
	
	public Integer getEdmInsurMon() {
		return this.edmInsurMon;
	}
	public void setMedInsurMon(Integer medInsurMon) {
		this.medInsurMon = medInsurMon;
	}
	
	public Integer getMedInsurMon() {
		return this.medInsurMon;
	}
	public void setHouFundMon(Integer houFundMon) {
		this.houFundMon = houFundMon;
	}
	
	public Integer getHouFundMon() {
		return this.houFundMon;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	
	public String getPayMode() {
		return this.payMode;
	}
	public void setInsurLimit(Double insurLimit) {
		this.insurLimit = insurLimit;
	}
	
	public Double getInsurLimit() {
		return this.insurLimit;
	}
	
	public Integer getSalarySettleCmp() {
		return salarySettleCmp;
	}

	public void setSalarySettleCmp(Integer salarySettleCmp) {
		this.salarySettleCmp = salarySettleCmp;
	}

	public void setCmpUsageType(String cmpUsageType) {
		this.cmpUsageType = cmpUsageType;
	}
	
	public String getCmpUsageType() {
		return this.cmpUsageType;
	}
	public void setSalaryChgDate(Date salaryChgDate) {
		this.salaryChgDate = salaryChgDate;
	}
	
	public Date getSalaryChgDate() {
		return this.salaryChgDate;
	}
	public void setSalaryStatus(String salaryStatus) {
		this.salaryStatus = salaryStatus;
	}
	
	public String getSalaryStatus() {
		return this.salaryStatus;
	}
	public void setIsTaxable(String isTaxable) {
		this.isTaxable = isTaxable;
	}
	
	public String getIsTaxable() {
		return this.isTaxable;
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

	public String getIsFront() {
		return isFront;
	}

	public void setIsFront(String isFront) {
		this.isFront = isFront;
	}

	public String getIsSocialInsur() {
		return isSocialInsur;
	}

	public void setIsSocialInsur(String isSocialInsur) {
		this.isSocialInsur = isSocialInsur;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
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

	public String getBankDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(bankDetailJson);
    	return xml;
	}

	public void setBankDetailJson(String bankDetailJson) {
		this.bankDetailJson = bankDetailJson;
	}

	public String getPayCmp1() {
		return payCmp1;
	}

	public void setPayCmp1(String payCmp1) {
		this.payCmp1 = payCmp1;
	}

	public Double getWeight1() {
		return weight1;
	}

	public void setWeight1(Double weight1) {
		this.weight1 = weight1;
	}

	public String getPayCmp2() {
		return payCmp2;
	}

	public void setPayCmp2(String payCmp2) {
		this.payCmp2 = payCmp2;
	}

	public Double getWeight2() {
		return weight2;
	}

	public void setWeight2(Double weight2) {
		this.weight2 = weight2;
	}

	public String getPayCmp3() {
		return payCmp3;
	}

	public void setPayCmp3(String payCmp3) {
		this.payCmp3 = payCmp3;
	}

	public Double getWeight3() {
		return weight3;
	}

	public void setWeight3(Double weight3) {
		this.weight3 = weight3;
	}

	public String getPayCmp4() {
		return payCmp4;
	}

	public void setPayCmp4(String payCmp4) {
		this.payCmp4 = payCmp4;
	}

	public Double getWeight4() {
		return weight4;
	}

	public void setWeight4(Double weight4) {
		this.weight4 = weight4;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getBasicSalaryByLevel() {
		return basicSalaryByLevel;
	}

	public void setBasicSalaryByLevel(Double basicSalaryByLevel) {
		this.basicSalaryByLevel = basicSalaryByLevel;
	}

	public Double getSalaryByLevel() {
		return salaryByLevel;
	}

	public void setSalaryByLevel(Double salaryByLevel) {
		this.salaryByLevel = salaryByLevel;
	}

	public Date getJoinDateFrom() {
		return joinDateFrom;
	}

	public void setJoinDateFrom(Date joinDateFrom) {
		this.joinDateFrom = joinDateFrom;
	}

	public Date getJoinDateTo() {
		return joinDateTo;
	}

	public void setJoinDateTo(Date joinDateTo) {
		this.joinDateTo = joinDateTo;
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	public String getScopeType() {
		return scopeType;
	}

	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}

	public String getScopeOperate() {
		return scopeOperate;
	}

	public void setScopeOperate(String scopeOperate) {
		this.scopeOperate = scopeOperate;
	}

	public String getScopeNum() {
		return scopeNum;
	}

	public void setScopeNum(String scopeNum) {
		this.scopeNum = scopeNum;
	}

	public String getSalaryScheme() {
		return salaryScheme;
	}

	public void setSalaryScheme(String salaryScheme) {
		this.salaryScheme = salaryScheme;
	}

	public String getChgType() {
		return chgType;
	}

	public void setChgType(String chgType) {
		this.chgType = chgType;
	}

	public String getIsSeries() {
		return isSeries;
	}

	public void setIsSeries(String isSeries) {
		this.isSeries = isSeries;
	}

	public Integer getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(Integer workingDays) {
		this.workingDays = workingDays;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Double getInsurBase() {
		return insurBase;
	}

	public void setInsurBase(Double insurBase) {
		this.insurBase = insurBase;
	}

	public Double getPosiSalary() {
		return posiSalary;
	}

	public void setPosiSalary(Double posiSalary) {
		this.posiSalary = posiSalary;
	}

	public Double getSkillSubsidy() {
		return skillSubsidy;
	}

	public void setSkillSubsidy(Double skillSubsidy) {
		this.skillSubsidy = skillSubsidy;
	}

	public Double getOtherBonuse() {
		return otherBonuse;
	}

	public void setOtherBonuse(Double otherBonuse) {
		this.otherBonuse = otherBonuse;
	}

	public Double getPerfBonuse() {
		return perfBonuse;
	}

	public void setPerfBonuse(Double perfBonuse) {
		this.perfBonuse = perfBonuse;
	}

	public Double getStarSubsidy() {
		return starSubsidy;
	}

	public void setStarSubsidy(Double starSubsidy) {
		this.starSubsidy = starSubsidy;
	}

	public Double getOtherSubsidy() {
		return otherSubsidy;
	}

	public void setOtherSubsidy(Double otherSubsidy) {
		this.otherSubsidy = otherSubsidy;
	}

	public Date getPosiChgDate() {
		return posiChgDate;
	}

	public void setPosiChgDate(Date posiChgDate) {
		this.posiChgDate = posiChgDate;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public String getIsBasicSalaryPayment() {
		return isBasicSalaryPayment;
	}

	public void setIsBasicSalaryPayment(String isBasicSalaryPayment) {
		this.isBasicSalaryPayment = isBasicSalaryPayment;
	}


}
