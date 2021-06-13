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
 * CommiCustStakeholder信息
 */
@Entity
@Table(name = "tCommiCustStakeholder")
public class CommiCustStakeholder extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CommiNo")
	private String commiNo;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "EmpCode")
	private String empCode;
	@Column(name = "Role")
	private String role;
	@Column(name = "Type")
	private String type;
	@Column(name = "PerfPK")
	private Integer perfPk;
	@Column(name = "PerfAmount")
	private Double perfAmount;
	@Column(name = "WeightPer")
	private Double weightPer;
	@Column(name = "CommiAmount")
	private Double commiAmount;
	@Column(name = "CheckPerfPK")
	private Integer checkPerfPk;
	@Column(name = "CommiExprPK")
	private Integer commiExprPk;
	@Column(name = "CommiExpr")
	private String commiExpr;
	@Column(name = "CommiExprRemarks")
	private String commiExprRemarks;
	@Column(name = "CommiExprWithNum")
	private String commiExprWithNum;
	@Column(name = "CommiPer")
	private Double commiPer;
	@Column(name = "CommiProvidePer")
	private Double commiProvidePer;
	@Column(name = "SubsidyPer")
	private Double subsidyPer;
	@Column(name = "SubsidyProvidePer")
	private Double subsidyProvidePer;
	@Column(name = "Multiple")
	private Double multiple;
	@Column(name = "MultipleProvidePer")
	private Double multipleProvidePer;
	@Column(name = "ShouldProvideAmount")
	private Double shouldProvideAmount;
	@Column(name = "AdjustAmount")
	private Double adjustAmount;
	@Column(name = "RealProvideAmount")
	private Double realProvideAmount;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "TotalRealProvideAmount")
	private Double totalRealProvideAmount;
	@Column(name = "SignCommiNo")
	private String signCommiNo;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "RightCardinal")
	private Double rightCardinal;
	@Column(name = "RightCommiPer")
	private Double rightCommiPer;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "Department2")
	private String department2;
	@Column(name = "Department3")
	private String department3;
	
	@Transient
	private String address;
	@Transient
	private String adjustRemarks;
	@Transient
	private String roleDescr;
	@Transient
	private String empName;
	@Transient
	private Integer mon;
	@Transient
	private String no;
	@Transient
	private Integer beginMon;
	@Transient
	private Integer endMon;
	
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
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setPerfPk(Integer perfPk) {
		this.perfPk = perfPk;
	}
	
	public Integer getPerfPk() {
		return this.perfPk;
	}
	public void setPerfAmount(Double perfAmount) {
		this.perfAmount = perfAmount;
	}
	
	public Double getPerfAmount() {
		return this.perfAmount;
	}
	public void setWeightPer(Double weightPer) {
		this.weightPer = weightPer;
	}
	
	public Double getWeightPer() {
		return this.weightPer;
	}
	public void setCommiAmount(Double commiAmount) {
		this.commiAmount = commiAmount;
	}
	
	public Double getCommiAmount() {
		return this.commiAmount;
	}
	public void setCheckPerfPk(Integer checkPerfPk) {
		this.checkPerfPk = checkPerfPk;
	}
	
	public Integer getCheckPerfPk() {
		return this.checkPerfPk;
	}
	public void setCommiExprPk(Integer commiExprPk) {
		this.commiExprPk = commiExprPk;
	}
	
	public Integer getCommiExprPk() {
		return this.commiExprPk;
	}
	public void setCommiExpr(String commiExpr) {
		this.commiExpr = commiExpr;
	}
	
	public String getCommiExpr() {
		return this.commiExpr;
	}
	public void setCommiExprRemarks(String commiExprRemarks) {
		this.commiExprRemarks = commiExprRemarks;
	}
	
	public String getCommiExprRemarks() {
		return this.commiExprRemarks;
	}
	public void setCommiExprWithNum(String commiExprWithNum) {
		this.commiExprWithNum = commiExprWithNum;
	}
	
	public String getCommiExprWithNum() {
		return this.commiExprWithNum;
	}
	public void setCommiPer(Double commiPer) {
		this.commiPer = commiPer;
	}
	
	public Double getCommiPer() {
		return this.commiPer;
	}
	public void setCommiProvidePer(Double commiProvidePer) {
		this.commiProvidePer = commiProvidePer;
	}
	
	public Double getCommiProvidePer() {
		return this.commiProvidePer;
	}
	public void setSubsidyPer(Double subsidyPer) {
		this.subsidyPer = subsidyPer;
	}
	
	public Double getSubsidyPer() {
		return this.subsidyPer;
	}
	public void setSubsidyProvidePer(Double subsidyProvidePer) {
		this.subsidyProvidePer = subsidyProvidePer;
	}
	
	public Double getSubsidyProvidePer() {
		return this.subsidyProvidePer;
	}
	public void setMultiple(Double multiple) {
		this.multiple = multiple;
	}
	
	public Double getMultiple() {
		return this.multiple;
	}
	public void setMultipleProvidePer(Double multipleProvidePer) {
		this.multipleProvidePer = multipleProvidePer;
	}
	
	public Double getMultipleProvidePer() {
		return this.multipleProvidePer;
	}
	public void setShouldProvideAmount(Double shouldProvideAmount) {
		this.shouldProvideAmount = shouldProvideAmount;
	}
	
	public Double getShouldProvideAmount() {
		return this.shouldProvideAmount;
	}
	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}
	
	public Double getAdjustAmount() {
		return this.adjustAmount;
	}
	public void setRealProvideAmount(Double realProvideAmount) {
		this.realProvideAmount = realProvideAmount;
	}
	
	public Double getRealProvideAmount() {
		return this.realProvideAmount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setTotalRealProvideAmount(Double totalRealProvideAmount) {
		this.totalRealProvideAmount = totalRealProvideAmount;
	}
	
	public Double getTotalRealProvideAmount() {
		return this.totalRealProvideAmount;
	}
	public void setSignCommiNo(String signCommiNo) {
		this.signCommiNo = signCommiNo;
	}
	
	public String getSignCommiNo() {
		return this.signCommiNo;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdjustRemarks() {
		return adjustRemarks;
	}

	public void setAdjustRemarks(String adjustRemarks) {
		this.adjustRemarks = adjustRemarks;
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

	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Double getRightCardinal() {
		return rightCardinal;
	}

	public void setRightCardinal(Double rightCardinal) {
		this.rightCardinal = rightCardinal;
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

	public Integer getBeginMon() {
		return beginMon;
	}

	public void setBeginMon(Integer beginMon) {
		this.beginMon = beginMon;
	}

	public Integer getEndMon() {
		return endMon;
	}

	public void setEndMon(Integer endMon) {
		this.endMon = endMon;
	}
	
}
