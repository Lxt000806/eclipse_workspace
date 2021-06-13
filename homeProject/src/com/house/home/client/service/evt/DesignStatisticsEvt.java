package com.house.home.client.service.evt;

import java.util.Date;

public class DesignStatisticsEvt extends BaseQueryEvt{

	private Date beginDate;
	private Date endDate;
	private String czybh;
	private String code;
	private String number;
	private String type;
	private String searchDescr;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSearchDescr() {
		return searchDescr;
	}
	public void setSearchDescr(String searchDescr) {
		this.searchDescr = searchDescr;
	}
	
	
}
