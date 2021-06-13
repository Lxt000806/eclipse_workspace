package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class PrjProgCheckQueryResp {

	private String no;
	private String custCode;
	private String custAddress;
	private String prjItem;
	private String prjItemDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date date;
	private String status;
	private String buildStatus;
	private String czyName;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date compDate;
	private String remarks;
	private String appCzyName;
	private String projectManName;
	
	public String getProjectManName() {
		return projectManName;
	}
	public void setProjectManName(String projectManName) {
		this.projectManName = projectManName;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBuildStatus() {
		return buildStatus;
	}
	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}
	
	public String getCzyName() {
		return czyName;
	}
	public void setCzyName(String czyName) {
		this.czyName = czyName;
	}
	public Date getCompDate() {
		return compDate;
	}
	public void setCompDate(Date compDate) {
		this.compDate = compDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAppCzyName() {
		return appCzyName;
	}
	public void setAppCzyName(String appCzyName) {
		this.appCzyName = appCzyName;
	}
	
}
