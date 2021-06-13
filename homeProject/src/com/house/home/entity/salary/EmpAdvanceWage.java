package com.house.home.entity.salary;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tEmpAdvanceWage")
public class EmpAdvanceWage extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "SalaryEmp")
	private String salaryEmp;
	@Column(name = "AdvanceWage")
	private Double advanceWage;
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
	
	@Transient
	private String empName;
	@Transient
	private String department1;
	@Transient
	private String dept1Descr;
	@Transient
	private String queryCondition;
	
	public String getQueryCondition() {
		
		return queryCondition;
	}
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getDepartment1() {
		return department1;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	public String getDept1Descr() {
		return dept1Descr;
	}
	public void setDept1Descr(String dept1Descr) {
		this.dept1Descr = dept1Descr;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getSalaryEmp() {
		return salaryEmp;
	}
	public void setSalaryEmp(String salaryEmp) {
		this.salaryEmp = salaryEmp;
	}
	public Double getAdvanceWage() {
		return advanceWage;
	}
	public void setAdvanceWage(Double advanceWage) {
		this.advanceWage = advanceWage;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

}
