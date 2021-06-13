package com.mju.hrmis.domain;

/**
 * ‘±π§¿‡
 * @author HY
 *
 */
public class Employee extends ValueObject{

	private String payrollNo;
	private String telephoneCode;
	private String lastname;
	private String firstname;
	private String initial;
	private int deptNo;
	private String jobTitle;
	private String hiringDate;
	public String getPayrollNo() {
		return payrollNo;
	}
	public void setPayrollNo(String payrollNo) {
		this.payrollNo = payrollNo;
	}
	public String getTelephoneCode() {
		return telephoneCode;
	}
	public void setTelephoneCode(String telephoneCode) {
		this.telephoneCode = telephoneCode;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getHiringDate() {
		return hiringDate;
	}
	public void setHiringDate(String hiringDate) {
		this.hiringDate = hiringDate;
	}
	
	
}
