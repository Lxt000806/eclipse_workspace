package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class DealPrjJobEvt extends BaseEvt {
	@NotEmpty(message="任务编号不能为空")
	private String no;
	private String dealRemark;
	private String endCode;
	private String address;
	private String dealCzy;
	private String photoNameList;
	private Double longitude;
	private Double latitude;
	private String errPosi;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getDealRemark() {
		return dealRemark;
	}
	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
	}
	public String getEndCode() {
		return endCode;
	}
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDealCzy() {
		return dealCzy;
	}
	public void setDealCzy(String dealCzy) {
		this.dealCzy = dealCzy;
	}
	public String getPhotoNameList() {
		return photoNameList;
	}
	public void setPhotoNameList(String photoNameList) {
		this.photoNameList = photoNameList;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getErrPosi() {
		return errPosi;
	}
	public void setErrPosi(String errPosi) {
		this.errPosi = errPosi;
	}
	
}
