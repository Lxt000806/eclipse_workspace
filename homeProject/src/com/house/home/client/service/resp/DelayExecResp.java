package com.house.home.client.service.resp;

import java.util.Date;

public class DelayExecResp {
	
	private Date date;
	private String remarks;
	private String czybhDescr;
	
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
	public String getCzybhDescr() {
		return czybhDescr;
	}
	public void setCzybhDescr(String czybhDescr) {
		this.czybhDescr = czybhDescr;
	}

}
