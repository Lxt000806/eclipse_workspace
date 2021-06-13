package com.house.home.client.service.resp;

import java.util.Date;

public class CustWorkerAppResp extends BaseResponse {
	private Integer pk;
	private String statusDescr;
	private String workType12Descr;
	private Date appDate;
	private String address;
	private String status;
	private String workType12;
	private Date appComeDate;
	private String remarks;
	private String nameChi;
	private String phone;
	private Date comeDate;
	private String custCode;
	private String isInitSign;//草签标记
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public Date getAppComeDate() {
		return appComeDate;
	}
	public void setAppComeDate(Date appComeDate) {
		this.appComeDate = appComeDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getComeDate() {
		return comeDate;
	}
	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getIsInitSign() {
		return isInitSign;
	}
	public void setIsInitSign(String isInitSign) {
		this.isInitSign = isInitSign;
	}
	
}
