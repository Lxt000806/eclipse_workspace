package com.house.home.entity.commi;

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
 * CommiCustStakeholderRule信息
 */
@Entity
@Table(name = "tCommiCustStakeholderRule")
public class CommiCustStakeholderRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SignCommiNo")
	private String signCommiNo;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "EmpCode")
	private String empCode;
	@Column(name = "Role")
	private String role;
	@Column(name = "WeightPer")
	private Double weightPer;
	@Column(name = "CommiPer")
	private Double commiPer;
	@Column(name = "SubsidyPer")
	private Double subsidyPer;
	@Column(name = "Multiple")
	private Double multiple;
	@Column(name = "PreCommiExprPK")
	private Integer preCommiExprPk;
	@Column(name = "CheckCommiExprPK")
	private Integer checkCommiExprPk;
	@Column(name = "CheckCommiType")
	private String checkCommiType;
	@Column(name = "CheckCommiPer")
	private Double checkCommiPer;
	@Column(name = "CheckFloatRulePK")
	private Integer checkFloatRulePk;
	@Column(name = "OldStakeholder")
	private String oldStakeholder;
	@Column(name = "OldStakeholder2")
	private String oldStakeholder2;
	@Column(name = "CommiProvidePer")
	private Double commiProvidePer;
	@Column(name = "SubsidyProvidePer")
	private Double subsidyProvidePer;
	@Column(name = "MultipleProvidePer")
	private Double multipleProvidePer;
	@Column(name = "TotalProvideAmount")
	private Double totalProvideAmount;
	@Column(name = "CrtNo")
	private String crtNo;
	@Column(name = "IsBearAgainCommi")
	private String isBearAgainCommi;
	@Column(name = "LastUpdateNo")
	private String lastUpdateNo;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "IsModified")
	private String isModified;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "RightCommiPer")
	private Double rightCommiPer;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "Department2")
	private String department2;
	@Column(name = "Department3")
	private String department3;
	
	@Transient
	private String roleDescr;
	@Transient
	private String empName;
	@Transient
	private String custDescr;
	@Transient
	private String oldStakeholderDescr;
	@Transient
	private String oldStakeholder2Descr;
	@Transient
	private String address;
	@Transient
	private Integer mon;
	@Transient
	private String onlyHitAgainMan;//仅仅显示撞单且有干系人
	@Transient
	private String department1Descr;
	@Transient
	private String department2Descr;
	@Transient
	private String department3Descr;
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setSignCommiNo(String signCommiNo) {
		this.signCommiNo = signCommiNo;
	}
	
	public String getSignCommiNo() {
		return this.signCommiNo;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getEmpCode() {
		return this.empCode;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
	public void setWeightPer(Double weightPer) {
		this.weightPer = weightPer;
	}
	
	public Double getWeightPer() {
		return this.weightPer;
	}
	public void setCommiPer(Double commiPer) {
		this.commiPer = commiPer;
	}
	
	public Double getCommiPer() {
		return this.commiPer;
	}
	public void setSubsidyPer(Double subsidyPer) {
		this.subsidyPer = subsidyPer;
	}
	
	public Double getSubsidyPer() {
		return this.subsidyPer;
	}
	public void setMultiple(Double multiple) {
		this.multiple = multiple;
	}
	
	public Double getMultiple() {
		return this.multiple;
	}
	public void setPreCommiExprPk(Integer preCommiExprPk) {
		this.preCommiExprPk = preCommiExprPk;
	}
	
	public Integer getPreCommiExprPk() {
		return this.preCommiExprPk;
	}
	public void setCheckCommiExprPk(Integer checkCommiExprPk) {
		this.checkCommiExprPk = checkCommiExprPk;
	}
	
	public Integer getCheckCommiExprPk() {
		return this.checkCommiExprPk;
	}
	public void setCheckCommiType(String checkCommiType) {
		this.checkCommiType = checkCommiType;
	}
	
	public String getCheckCommiType() {
		return this.checkCommiType;
	}
	public void setCheckCommiPer(Double checkCommiPer) {
		this.checkCommiPer = checkCommiPer;
	}
	
	public Double getCheckCommiPer() {
		return this.checkCommiPer;
	}
	public void setCheckFloatRulePk(Integer checkFloatRulePk) {
		this.checkFloatRulePk = checkFloatRulePk;
	}
	
	public Integer getCheckFloatRulePk() {
		return this.checkFloatRulePk;
	}
	public void setOldStakeholder(String oldStakeholder) {
		this.oldStakeholder = oldStakeholder;
	}
	
	public String getOldStakeholder() {
		return this.oldStakeholder;
	}
	public void setOldStakeholder2(String oldStakeholder2) {
		this.oldStakeholder2 = oldStakeholder2;
	}
	
	public String getOldStakeholder2() {
		return this.oldStakeholder2;
	}
	public void setCommiProvidePer(Double commiProvidePer) {
		this.commiProvidePer = commiProvidePer;
	}
	
	public Double getCommiProvidePer() {
		return this.commiProvidePer;
	}
	public void setSubsidyProvidePer(Double subsidyProvidePer) {
		this.subsidyProvidePer = subsidyProvidePer;
	}
	
	public Double getSubsidyProvidePer() {
		return this.subsidyProvidePer;
	}
	public void setMultipleProvidePer(Double multipleProvidePer) {
		this.multipleProvidePer = multipleProvidePer;
	}
	
	public Double getMultipleProvidePer() {
		return this.multipleProvidePer;
	}
	public void setTotalProvideAmount(Double totalProvideAmount) {
		this.totalProvideAmount = totalProvideAmount;
	}
	
	public Double getTotalProvideAmount() {
		return this.totalProvideAmount;
	}
	public void setCrtNo(String crtNo) {
		this.crtNo = crtNo;
	}
	
	public String getCrtNo() {
		return this.crtNo;
	}
	public void setIsBearAgainCommi(String isBearAgainCommi) {
		this.isBearAgainCommi = isBearAgainCommi;
	}
	
	public String getIsBearAgainCommi() {
		return this.isBearAgainCommi;
	}
	public void setLastUpdateNo(String lastUpdateNo) {
		this.lastUpdateNo = lastUpdateNo;
	}
	
	public String getLastUpdateNo() {
		return this.lastUpdateNo;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setIsModified(String isModified) {
		this.isModified = isModified;
	}
	
	public String getIsModified() {
		return this.isModified;
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

	public String getRoleDescr() {
		return roleDescr;
	}

	public void setRoleDescr(String roleDescr) {
		this.roleDescr = roleDescr;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCustDescr() {
		return custDescr;
	}

	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}

	public String getOldStakeholderDescr() {
		return oldStakeholderDescr;
	}

	public void setOldStakeholderDescr(String oldStakeholderDescr) {
		this.oldStakeholderDescr = oldStakeholderDescr;
	}

	public String getOldStakeholder2Descr() {
		return oldStakeholder2Descr;
	}

	public void setOldStakeholder2Descr(String oldStakeholder2Descr) {
		this.oldStakeholder2Descr = oldStakeholder2Descr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}

	public String getOnlyHitAgainMan() {
		return onlyHitAgainMan;
	}

	public void setOnlyHitAgainMan(String onlyHitAgainMan) {
		this.onlyHitAgainMan = onlyHitAgainMan;
	}

	public Double getRightCommiPer() {
		return rightCommiPer;
	}

	public void setRightCommiPer(Double rightCommiPer) {
		this.rightCommiPer = rightCommiPer;
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

	public String getDepartment1Descr() {
		return department1Descr;
	}

	public void setDepartment1Descr(String department1Descr) {
		this.department1Descr = department1Descr;
	}

	public String getDepartment2Descr() {
		return department2Descr;
	}

	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}

	public String getDepartment3Descr() {
		return department3Descr;
	}

	public void setDepartment3Descr(String department3Descr) {
		this.department3Descr = department3Descr;
	}
	
}
