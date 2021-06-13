package com.house.home.client.service.evt;

import java.util.Date;

public class CustProblemEvt extends BaseQueryEvt {

	private Integer pk;
	private String status;
	private String searchType;
	private String dealRemarks;
	private String dealDate;
	private Date rcvDate;
	private String empNum;
	private String type;
	private Date repDate;
	private String no;
	private String repEmp;
	private String custCode;
	private String remarks;
	private String dealMan;
	private String serviceEmp;
	private String address;
	private String undertaker;
	private String serviceDealMan;// 转售后的处理人
	private Date serviceDealDate;// 转售后的处理时间
	
	public String getServiceDealMan() {
		return serviceDealMan;
	}
	public void setServiceDealMan(String serviceDealMan) {
		this.serviceDealMan = serviceDealMan;
	}
	public Date getServiceDealDate() {
		return serviceDealDate;
	}
	public void setServiceDealDate(Date serviceDealDate) {
		this.serviceDealDate = serviceDealDate;
	}
	public Date getRepDate() {
		return repDate;
	}
	public void setRepDate(Date repDate) {
		this.repDate = repDate;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getRepEmp() {
		return repEmp;
	}
	public void setRepEmp(String repEmp) {
		this.repEmp = repEmp;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDealMan() {
		return dealMan;
	}
	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}
	public String getServiceEmp() {
		return serviceEmp;
	}
	public void setServiceEmp(String serviceEmp) {
		this.serviceEmp = serviceEmp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUndertaker() {
		return undertaker;
	}
	public void setUndertaker(String undertaker) {
		this.undertaker = undertaker;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmpNum() {
		return empNum;
	}
	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}
	public Date getRcvDate() {
		return rcvDate;
	}
	public void setRcvDate(Date rcvDate) {
		this.rcvDate = rcvDate;
	}
	public String getDealRemarks() {
		return dealRemarks;
	}
	public void setDealRemarks(String dealRemarks) {
		this.dealRemarks = dealRemarks;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
}
