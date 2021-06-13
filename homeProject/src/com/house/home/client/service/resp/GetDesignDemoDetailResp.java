package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GetDesignDemoDetailResp extends BaseResponse {

	private String custDescr;
	private String builderDescr;
	private String designManDescr;
	private String layoutDescr;
	private String designStyleDescr;
	private Double area;
	private Double amount;
	private String designRemark;
	private String gender;
	private List<Map<String,Object>> photos;
	
	public String getCustDescr() {
		return custDescr;
	}
	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}
	public String getBuilderDescr() {
		return builderDescr;
	}
	public void setBuilderDescr(String builderDescr) {
		this.builderDescr = builderDescr;
	}
	public String getDesignManDescr() {
		return designManDescr;
	}
	public void setDesignManDescr(String designManDescr) {
		this.designManDescr = designManDescr;
	}
	public String getLayoutDescr() {
		return layoutDescr;
	}
	public void setLayoutDescr(String layoutDescr) {
		this.layoutDescr = layoutDescr;
	}
	public String getDesignStyleDescr() {
		return designStyleDescr;
	}
	public void setDesignStyleDescr(String designStyleDescr) {
		this.designStyleDescr = designStyleDescr;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDesignRemark() {
		return designRemark;
	}
	public void setDesignRemark(String designRemark) {
		this.designRemark = designRemark;
	}
	public List<Map<String, Object>> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Map<String, Object>> photos) {
		this.photos = photos;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	
}
