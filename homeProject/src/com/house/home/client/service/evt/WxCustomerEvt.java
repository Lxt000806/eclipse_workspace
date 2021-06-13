package com.house.home.client.service.evt;

public class WxCustomerEvt extends BaseEvt{
	private String code;
	private String phone;
	private String smsPwd;
	private String encryptedData;
	private String iv;
	private String recommender;
	private String recommenderType;
	private String recommendSource;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmsPwd() {
		return smsPwd;
	}

	public void setSmsPwd(String smsPwd) {
		this.smsPwd = smsPwd;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getRecommender() {
		return recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	public String getRecommenderType() {
		return recommenderType;
	}

	public void setRecommenderType(String recommenderType) {
		this.recommenderType = recommenderType;
	}

	public String getRecommendSource() {
		return recommendSource;
	}

	public void setRecommendSource(String recommendSource) {
		this.recommendSource = recommendSource;
	}
	
	
}
