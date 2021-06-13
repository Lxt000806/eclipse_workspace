package com.house.home.client.service.evt;

import java.util.Date;

public class ItemAppListQueryResp {
	private String no;
	private String note;
	private Date date;
	private Date confirmDate;
	private Date arriveDate;
	private Date sendDate;
	private String itemType1Descr;
	private String itemType2Descr;
	private Date actualArriveDate;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
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
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public Date getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getItemType1Descr() {
		return itemType1Descr;
	}
	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}
	public String getItemType2Descr() {
		return itemType2Descr;
	}
	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}
	public Date getActualArriveDate() {
		return actualArriveDate;
	}
	public void setActualArriveDate(Date actualArriveDate) {
		this.actualArriveDate = actualArriveDate;
	}
	
}
