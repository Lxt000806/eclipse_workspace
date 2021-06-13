package com.house.home.client.service.evt;

import java.util.Date;

public class BusinessWorkEvt extends BaseQueryEvt{

	private Date beginDate;
	private Date endDate;
	private String czybh;
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	@Override
	public String toString() {
		return "DesignWorkEvt [beginDate=" + beginDate + ", endDate=" + endDate
				+ ", czybh=" + czybh + "]";
	}
	
}
