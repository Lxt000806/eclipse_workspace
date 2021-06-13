package com.house.home.client.service.resp;

import java.util.Date;

public class GetPrjProblemListResp{

	private String no;
	private String statusDescr;
	private String promDeptCodeDescr;
	private String promTypeCodeDescr;
	private Date appDate;
	private String address;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getPromDeptCodeDescr() {
		return promDeptCodeDescr;
	}
	public void setPromDeptCodeDescr(String promDeptCodeDescr) {
		this.promDeptCodeDescr = promDeptCodeDescr;
	}
	public String getPromTypeCodeDescr() {
		return promTypeCodeDescr;
	}
	public void setPromTypeCodeDescr(String promTypeCodeDescr) {
		this.promTypeCodeDescr = promTypeCodeDescr;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
