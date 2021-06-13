package com.house.home.client.service.evt;

public class CustRecommendEvt extends BaseQueryEvt{
	private int pk;
	private String address;
	private String status;
	private String custName;
	private String custPhone;
	private String remarks;
	private String recommender;
	private String page;
	private String czybh;
	private String recommendSource;
	private int width;
	private String recommenderType;
	private String custCode;
	private String workerCode;
	private String portalAccount;
	private String searchInfo;
	private String isSelf;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRecommender() {
		return recommender;
	}
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getRecommendSource() {
		return recommendSource;
	}
	public void setRecommendSource(String recommendSource) {
		this.recommendSource = recommendSource;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getRecommenderType() {
		return recommenderType;
	}
	public void setRecommenderType(String recommenderType) {
		this.recommenderType = recommenderType;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getPortalAccount() {
		return portalAccount;
	}
	public void setPortalAccount(String portalAccount) {
		this.portalAccount = portalAccount;
	}
	public String getSearchInfo() {
		return searchInfo;
	}
	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}
	public String getIsSelf() {
		return isSelf;
	}
	public void setIsSelf(String isSelf) {
		this.isSelf = isSelf;
	}
	
}
