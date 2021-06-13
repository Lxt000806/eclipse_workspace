package com.house.home.client.service.resp;

import java.util.Date;

public class PrjItemResp {
	
	private String prjItem;
	private String prjItemDescr;
	private String isConfirm;
	private Date beginDate;
	private Date endDate;
	private Integer pk;
	private Integer prjphotoNum;
	
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}
	public String getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
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
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public Integer getPrjphotoNum() {
		return prjphotoNum;
	}
	public void setPrjphotoNum(Integer prjphotoNum) {
		this.prjphotoNum = prjphotoNum;
	}
	
}
