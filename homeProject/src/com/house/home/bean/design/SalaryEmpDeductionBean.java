package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * SalaryEmpDeduction信息bean
 */
public class SalaryEmpDeductionBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="工号", order=2)
	private String salaryEmp;
	@ExcelAnnotation(exportName="扣款科目", order=3)
	private String deductType2;
	@ExcelAnnotation(exportName="扣款金额", order=4)
	private Double amount;
	@ExcelAnnotation(exportName="扣款内容", order=5)
	private String remarks;
    @ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=7)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=8)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=9)
	private String actionLog;
	@ExcelAnnotation(exportName="财务编码", order=10)
	private String financialCode;
	@ExcelAnnotation(exportName="姓名", order=11)
	private String empName;
	@ExcelAnnotation(exportName="备注", order=12)
	private String remark;
	@ExcelAnnotation(exportName="一级部门", order=13)
	private String department1;
	@ExcelAnnotation(exportName="二级部门", order=14)
	private String department2;
	@ExcelAnnotation(exportName="岗位", order=15)
	private String positionDescr;
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getSalaryEmp() {
		return salaryEmp;
	}
	public void setSalaryEmp(String salaryEmp) {
		this.salaryEmp = salaryEmp;
	}
	public String getDeductType2() {
		return deductType2;
	}
	public void setDeductType2(String deductType2) {
		this.deductType2 = deductType2;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
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
	public Integer getPk() {
		return pk;
	}
	public String getFinancialCode() {
		return financialCode;
	}
	public void setFinancialCode(String financialCode) {
		this.financialCode = financialCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getPositionDescr() {
		return positionDescr;
	}
	public void setPositionDescr(String positionDescr) {
		this.positionDescr = positionDescr;
	}
	
	
}
