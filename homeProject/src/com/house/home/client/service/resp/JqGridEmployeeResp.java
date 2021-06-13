package com.house.home.client.service.resp;

import java.util.Date;
import java.util.Map;

public class JqGridEmployeeResp extends BaseResponse {
	
	private String number;
	private String nameChi;
	private String positionDescr;
	private Date joinDate;
	private String department2Descr;
	private String type;
	private Double befAmount;
	private String depType;
	private Date expenseDate;
	private String czybh;
	private Double advanceAmount;
	private String Company;
	private String CompanyCode;
	private String department2Code;
	private Map<String, Object> datas;
	private String phone;
	private String department;
	private String departmentDescr;
	
	public String getDepartmentDescr() {
		return departmentDescr;
	}
	public void setDepartmentDescr(String departmentDescr) {
		this.departmentDescr = departmentDescr;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Map<String, Object> getDatas() {
		return datas;
	}
	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}
	public String getDepartment2Code() {
		return department2Code;
	}
	public void setDepartment2Code(String department2Code) {
		this.department2Code = department2Code;
	}
	public String getCompanyCode() {
		return CompanyCode;
	}
	public void setCompanyCode(String companyCode) {
		CompanyCode = companyCode;
	}
	public String getCompany() {
		return Company;
	}
	public void setCompany(String company) {
		Company = company;
	}
	public Double getAdvanceAmount() {
		return advanceAmount;
	}
	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public Date getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}
	public String getDepType() {
		return depType;
	}
	public void setDepType(String depType) {
		this.depType = depType;
	}
	public Double getBefAmount() {
		return befAmount;
	}
	public void setBefAmount(Double befAmount) {
		this.befAmount = befAmount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getPositionDescr() {
		return positionDescr;
	}
	public void setPositionDescr(String positionDescr) {
		this.positionDescr = positionDescr;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public String getDepartment2Descr() {
		return department2Descr;
	}
	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}
	
	
}
