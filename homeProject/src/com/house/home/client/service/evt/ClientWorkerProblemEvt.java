package com.house.home.client.service.evt;

import java.util.Date;

public class ClientWorkerProblemEvt extends BaseEvt{
	private String no;
	private Integer custWkPk;
	private String type;
	private double stopDay;
	private Date date;
	private String remarks;
	private String photoNameList;
	private int pageNo;
	private int pageSize;
	private String custCode;

	
	
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public double getStopDay() {
		return stopDay;
	}
	public void setStopDay(double stopDay) {
		this.stopDay = stopDay;
	}
	public String getPhotoNameList() {
		return photoNameList;
	}
	public void setPhotoNameList(String photoNameList) {
		this.photoNameList = photoNameList;
	}
	
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
