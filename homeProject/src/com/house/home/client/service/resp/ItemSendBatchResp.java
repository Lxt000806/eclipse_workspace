package com.house.home.client.service.resp;

import java.util.Date;

public class ItemSendBatchResp {
	private String no;
	private String appczydesc;
	private String drivername;
	private String statusdesc;
	private Date date;
	private String remarks;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getAppczydesc() {
		return appczydesc;
	}
	public void setAppczydesc(String appczydesc) {
		this.appczydesc = appczydesc;
	}
	public String getDrivername() {
		return drivername;
	}
	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}
	public String getStatusdesc() {
		return statusdesc;
	}
	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
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
	
}
