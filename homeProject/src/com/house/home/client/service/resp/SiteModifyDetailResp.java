package com.house.home.client.service.resp;

import java.util.Date;

public class SiteModifyDetailResp extends BaseResponse {
	private String no;
	private String address;
	private String note;
	private Date date;
	private String remarks;
	private String compRemark;
	private Date compDate;
	private Integer modifyTime;
	private Integer remainDate;
	private String modifyComplete;
	private String phone;
	private String nameChi;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCompRemark() {
		return compRemark;
	}
	public void setCompRemark(String compRemark) {
		this.compRemark = compRemark;
	}
	public Date getCompDate() {
		return compDate;
	}
	public void setCompDate(Date compDate) {
		this.compDate = compDate;
	}
	public Integer getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getRemainDate() {
		return remainDate;
	}
	public void setRemainDate(Integer remainDate) {
		this.remainDate = remainDate;
	}
	public String getModifyComplete() {
		return modifyComplete;
	}
	public void setModifyComplete(String modifyComplete) {
		this.modifyComplete = modifyComplete;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	
}
