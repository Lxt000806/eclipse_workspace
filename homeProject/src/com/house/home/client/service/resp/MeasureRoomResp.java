package com.house.home.client.service.resp;

import java.util.Date;

@SuppressWarnings("rawtypes")
public class MeasureRoomResp extends BaseListQueryResp{
	
	private String address;
	private String custCode;
	private Date measureDate;
	private String no;
	private String src;
	private int pk;
	private String remarks;
	private Date planMeasureDate;
	private int signInPk;
	private String photoName;
	
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
	public Date getMeasureDate() {
		return measureDate;
	}
	public void setMeasureDate(Date measureDate) {
		this.measureDate = measureDate;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getPlanMeasureDate() {
		return planMeasureDate;
	}
	public void setPlanMeasureDate(Date planMeasureDate) {
		this.planMeasureDate = planMeasureDate;
	}
	public int getSignInPk() {
		return signInPk;
	}
	public void setSignInPk(int signInPk) {
		this.signInPk = signInPk;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
	
}
