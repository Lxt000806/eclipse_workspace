package com.house.home.client.service.resp;

import java.util.Date;

public class PrjConfirmAppResp extends BaseResponse {
	private Integer pk;
	private String statusDescr;
	private Date appDate;
	private String address;
	private String status;
	private String remarks;
	private String prjItem;
	private String prjItemDescr;
	private String confirmCzyDescr;
	private String prjLevelDescr;
	private Date confirmDate;
	private String custCode;
	private String isPassDescr;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}
	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}
	public String getPrjLevelDescr() {
		return prjLevelDescr;
	}
	public void setPrjLevelDescr(String prjLevelDescr) {
		this.prjLevelDescr = prjLevelDescr;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getIsPassDescr() {
		return isPassDescr;
	}
	public void setIsPassDescr(String isPassDescr) {
		this.isPassDescr = isPassDescr;
	}
	
}
