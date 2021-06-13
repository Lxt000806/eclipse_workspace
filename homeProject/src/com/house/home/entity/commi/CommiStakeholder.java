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
 * CommiStakeholder信息
 */
@Entity
@Table(name = "tCommiStakeholder")
public class CommiStakeholder extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CommiNo")
	private String commiNo;
	@Column(name = "EmpCode")
	private String empCode;
	@Column(name = "Department")
	private String department;
	@Column(name = "Role")
	private String role;
	@Column(name = "EffectiveCardinal")
	private Double effectiveCardinal;
	@Column(name = "CommiPer")
	private Double commiPer;
	@Column(name = "Multiple")
	private Double multiple;
	@Column(name = "SubsidyPer")
	private Double subsidyPer;
	@Column(name = "DesignAgainSubsidyPer")
	private Double designAgainSubsidyPer;
	@Column(name = "RecordCommiPer")
	private Double recordCommiPer;
	@Column(name = "IsBearAgainCommi")
	private String isBearAgainCommi;
	@Column(name = "CheckCommiType")
	private String checkCommiType;
	@Column(name = "CheckCommiPer")
	private Double checkCommiPer;
	@Column(name = "CheckFloatRulePK")
	private Integer checkFloatRulePk;
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
	@Column(name = "RecordRightCommiPer")
	private Double recordRightCommiPer;
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
	private String departmentDescr;
	@Transient
	private Integer mon;
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
	public void setCommiNo(String commiNo) {
		this.commiNo = commiNo;
	}
	
	public String getCommiNo() {
		return this.commiNo;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getEmpCode() {
		return this.empCode;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getDepartment() {
		return this.department;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
	public void setEffectiveCardinal(Double effectiveCardinal) {
		this.effectiveCardinal = effectiveCardinal;
	}
	
	public Double getEffectiveCardinal() {
		return this.effectiveCardinal;
	}
	public void setCommiPer(Double commiPer) {
		this.commiPer = commiPer;
	}
	
	public Double getCommiPer() {
		return this.commiPer;
	}
	public void setMultiple(Double multiple) {
		this.multiple = multiple;
	}
	
	public Double getMultiple() {
		return this.multiple;
	}
	public void setSubsidyPer(Double subsidyPer) {
		this.subsidyPer = subsidyPer;
	}
	
	public Double getSubsidyPer() {
		return this.subsidyPer;
	}
	
	public Double getDesignAgainSubsidyPer() {
		return designAgainSubsidyPer;
	}

	public void setDesignAgainSubsidyPer(Double designAgainSubsidyPer) {
		this.designAgainSubsidyPer = designAgainSubsidyPer;
	}

	public Double getRecordCommiPer() {
		return recordCommiPer;
	}

	public void setRecordCommiPer(Double recordCommiPer) {
		this.recordCommiPer = recordCommiPer;
	}

	public String getIsBearAgainCommi() {
		return isBearAgainCommi;
	}

	public void setIsBearAgainCommi(String isBearAgainCommi) {
		this.isBearAgainCommi = isBearAgainCommi;
	}

	public String getCheckCommiType() {
		return checkCommiType;
	}

	public void setCheckCommiType(String checkCommiType) {
		this.checkCommiType = checkCommiType;
	}

	public Double getCheckCommiPer() {
		return checkCommiPer;
	}

	public void setCheckCommiPer(Double checkCommiPer) {
		this.checkCommiPer = checkCommiPer;
	}

	public Integer getCheckFloatRulePk() {
		return checkFloatRulePk;
	}

	public void setCheckFloatRulePk(Integer checkFloatRulePk) {
		this.checkFloatRulePk = checkFloatRulePk;
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

	public String getDepartmentDescr() {
		return departmentDescr;
	}

	public void setDepartmentDescr(String departmentDescr) {
		this.departmentDescr = departmentDescr;
	}

	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}

	public Double getRightCommiPer() {
		return rightCommiPer;
	}

	public void setRightCommiPer(Double rightCommiPer) {
		this.rightCommiPer = rightCommiPer;
	}

	public Double getRecordRightCommiPer() {
		return recordRightCommiPer;
	}

	public void setRecordRightCommiPer(Double recordRightCommiPer) {
		this.recordRightCommiPer = recordRightCommiPer;
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

	public String getDepartment3() {
		return department3;
	}

	public void setDepartment3(String department3) {
		this.department3 = department3;
	}

	public String getDepartment3Descr() {
		return department3Descr;
	}

	public void setDepartment3Descr(String department3Descr) {
		this.department3Descr = department3Descr;
	}
	
}
