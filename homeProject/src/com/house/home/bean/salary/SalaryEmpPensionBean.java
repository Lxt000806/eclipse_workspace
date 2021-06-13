package com.house.home.bean.salary;

import com.house.framework.commons.excel.ExcelAnnotation;

public class SalaryEmpPensionBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="工号", order=1)
	private String salaryEmp;
	@ExcelAnnotation(exportName="补贴科目", order=2)
	private String typeDescr;
	@ExcelAnnotation(exportName="金额", order=3)
	private Double amount;
	@ExcelAnnotation(exportName="开始月份", order=4)
	private Integer beginMon;
	public String getSalaryEmp() {
		return salaryEmp;
	}
	public void setSalaryEmp(String salaryEmp) {
		this.salaryEmp = salaryEmp;
	}
	public String getTypeDescr() {
		return typeDescr;
	}
	public void setTypeDescr(String typeDescr) {
		this.typeDescr = typeDescr;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getBeginMon() {
		return beginMon;
	}
	public void setBeginMon(Integer beginMon) {
		this.beginMon = beginMon;
	}
}
