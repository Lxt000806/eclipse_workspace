package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

public class GetShowBuildsResp {

	private String custCode;
	private String address;
	private Integer area;
	private String custDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date beginDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date confirmDate;
	private String prjProgConfirmNo;
	private List<Map<String, Object>> photos;
	private String prjItem1Descr;
	private String gender;
	private String dimAddress; //模糊楼盘
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public String getCustDescr() {
		return custDescr;
	}
	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public String getPrjProgConfirmNo() {
		return prjProgConfirmNo;
	}
	public void setPrjProgConfirmNo(String prjProgConfirmNo) {
		this.prjProgConfirmNo = prjProgConfirmNo;
	}
	public List<Map<String, Object>> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Map<String, Object>> photos) {
		this.photos = photos;
	}
	public String getPrjItem1Descr() {
		return prjItem1Descr;
	}
	public void setPrjItem1Descr(String prjItem1Descr) {
		this.prjItem1Descr = prjItem1Descr;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDimAddress() {
		return dimAddress;
	}
	public void setDimAddress(String dimAddress) {
		this.dimAddress = dimAddress;
	}

	
}
