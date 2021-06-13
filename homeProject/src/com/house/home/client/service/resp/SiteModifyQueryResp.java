package com.house.home.client.service.resp;

import java.util.Date;

public class SiteModifyQueryResp {
	private String no;
	private String address;
	private Integer remainDate;
	private Integer modifyTime;
	private Date date;
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
	
	public Integer getRemainDate() {
		return remainDate;
	}
	public void setRemainDate(Integer remainDate) {
		this.remainDate = remainDate;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
