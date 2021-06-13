package com.house.home.client.service.resp;

import java.util.Date;

public class CustomerResp extends BaseResponse{
	private String code;
	private String address;
	private String prjItem;
	private String prjItemDescr;
	private Date appDate;
	private String isPrjConfirm;
	
	private String workType1;
	private String offerWorkType2;
	private String buildPass;
	private String workerApp;
	private String conId;
	private String isPass;
	private String realAddress;
	private String remarks;
	
	
	
	public String getRealAddress() {
		return realAddress;
	}
	public void setRealAddress(String realAddress) {
		this.realAddress = realAddress;
	}
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getConId() {
		return conId;
	}
	public void setConId(String conId) {
		this.conId = conId;
	}
	public String getWorkerApp() {
		return workerApp;
	}
	public void setWorkerApp(String workerApp) {
		this.workerApp = workerApp;
	}
	public String getIsPrjConfirm() {
		return isPrjConfirm;
	}
	public void setIsPrjConfirm(String isPrjConfirm) {
		this.isPrjConfirm = isPrjConfirm;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getWorkType1() {
		return workType1;
	}
	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}
	public String getOfferWorkType2() {
		return offerWorkType2;
	}
	public void setOfferWorkType2(String offerWorkType2) {
		this.offerWorkType2 = offerWorkType2;
	}
	public String getBuildPass() {
		return buildPass;
	}
	public void setBuildPass(String buildPass) {
		this.buildPass = buildPass;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
