package com.house.home.client.service.resp;

import java.util.Date;

@SuppressWarnings("rawtypes")
public class CustRecommendResp extends BaseListQueryResp{

	private int pk;
	private String address;
	private String custName;
	private String custPhone;
	private String status;
	private String remarks;
	private String statusDescr;
	private String confirmRemarks;
	private Date confirmDate;
	private String base64String;
	private Date recommendDate;
	private String recommenderDescr;
	private String sence;
	
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
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
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getConfirmRemarks() {
		return confirmRemarks;
	}
	public void setConfirmRemarks(String confirmRemarks) {
		this.confirmRemarks = confirmRemarks;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getBase64String() {
		return base64String;
	}
	public void setBase64String(String base64String) {
		this.base64String = base64String;
	}
	public Date getRecommendDate() {
		return recommendDate;
	}
	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
	}
	public String getRecommenderDescr() {
		return recommenderDescr;
	}
	public void setRecommenderDescr(String recommenderDescr) {
		this.recommenderDescr = recommenderDescr;
	}
	public String getSence() {
		return sence;
	}
	public void setSence(String sence) {
		this.sence = sence;
	}
	
}
