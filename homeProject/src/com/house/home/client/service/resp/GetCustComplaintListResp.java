package com.house.home.client.service.resp;

import java.util.Date;

public class GetCustComplaintListResp {
	
	private String no;
	private String statusDescr;
	private Date crtDate;
	private String department2Descr;
	private String address;
	private String dealCzyDescr;
	private String remarks;
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDealCzyDescr() {
		return dealCzyDescr;
	}
	public void setDealCzyDescr(String dealCzyDescr) {
		this.dealCzyDescr = dealCzyDescr;
	}
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
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getDepartment2Descr() {
		return department2Descr;
	}
	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
