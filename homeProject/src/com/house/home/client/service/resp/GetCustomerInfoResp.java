package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GetCustomerInfoResp{

	private String region;
	private String layout;
	private Double area;
	private Double contractFee;
	private String designMan;
	private String designStyle;
	private String projectMan;
	private String address;
	private String custCode;
	private String currentPrjItem;
	private List<Map<String, Object>> photos;
	private Date signDate;
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Double getContractFee() {
		return contractFee;
	}
	public void setContractFee(Double contractFee) {
		this.contractFee = contractFee;
	}
	public String getDesignMan() {
		return designMan;
	}
	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}
	public String getDesignStyle() {
		return designStyle;
	}
	public void setDesignStyle(String designStyle) {
		this.designStyle = designStyle;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCurrentPrjItem() {
		return currentPrjItem;
	}
	public void setCurrentPrjItem(String currentPrjItem) {
		this.currentPrjItem = currentPrjItem;
	}
	public List<Map<String, Object>> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Map<String, Object>> photos) {
		this.photos = photos;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
}
