package com.house.home.client.service.evt;

import java.util.Date;

public class WorkerArrangeEvt extends BaseQueryEvt{
	
	private String workType12;
	private Boolean hideOrdered;
	private String dayType;
	private Date date;
	private String department1;
	private String department2;
	private String czybh;
	private String address;
	private Integer pk;
	private String custCode;
	
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public Boolean getHideOrdered() {
		return hideOrdered;
	}
	public void setHideOrdered(Boolean hideOrdered) {
		this.hideOrdered = hideOrdered;
	}
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	
}
