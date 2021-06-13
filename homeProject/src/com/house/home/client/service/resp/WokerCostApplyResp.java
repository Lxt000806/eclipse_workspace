package com.house.home.client.service.resp;

import java.util.Date;

public class WokerCostApplyResp extends BaseResponse {
	private String address;
	private String status;
	private Double workAppAmount;
	private Date applyDate;
	private String statusDescr;
	private int pk;
	private Double realAmount;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getWorkAppAmount() {
		return workAppAmount;
	}
	public void setWorkAppAmount(Double workAppAmount) {
		this.workAppAmount = workAppAmount;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public Double getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
	}
	
}
