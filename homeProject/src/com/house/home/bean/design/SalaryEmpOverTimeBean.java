package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * SalaryEmpOverTimeBean信息bean
 */
public class SalaryEmpOverTimeBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="人员工号", order=2)
	private String salaryEmp;
	@ExcelAnnotation(exportName="加班次数", order=3)
	private Double times;
	@ExcelAnnotation(exportName="备注", order=4)
	private String remarks;
	@ExcelAnnotation(exportName="姓名", order=5)
	private String empName;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getSalaryEmp() {
		return salaryEmp;
	}
	public void setSalaryEmp(String salaryEmp) {
		this.salaryEmp = salaryEmp;
	}
	public Double getTimes() {
		return times;
	}
	public void setTimes(Double times) {
		this.times = times;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
}
