package com.house.home.client.service.evt;

public class MeasureRoomEvt extends BaseQueryEvt{
	private String czybh;
	private String custCode;
	private String address;
	private Double longitude;
	private Double latitude;
	private String remarks;
	private String errPosi;
	private String photoString;
	private String no;
	private int pk;
	private int signInPk;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getErrPosi() {
		return errPosi;
	}
	public void setErrPosi(String errPosi) {
		this.errPosi = errPosi;
	}
	public String getPhotoString() {
		return photoString;
	}
	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public int getSignInPk() {
		return signInPk;
	}
	public void setSignInPk(int signInPk) {
		this.signInPk = signInPk;
	}
	
	
}
