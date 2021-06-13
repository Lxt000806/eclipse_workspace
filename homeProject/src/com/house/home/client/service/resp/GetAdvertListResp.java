package com.house.home.client.service.resp;

import java.util.Date;

public class GetAdvertListResp {

	private Integer pk;
	private String advType;
	private String picAddr;
	private String advTypeDescr;
	private String title;
	private String outUrl;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getAdvType() {
		return advType;
	}
	public void setAdvType(String advType) {
		this.advType = advType;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public String getAdvTypeDescr() {
		return advTypeDescr;
	}
	public void setAdvTypeDescr(String advTypeDescr) {
		this.advTypeDescr = advTypeDescr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOutUrl() {
		return outUrl;
	}
	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}
	
}
