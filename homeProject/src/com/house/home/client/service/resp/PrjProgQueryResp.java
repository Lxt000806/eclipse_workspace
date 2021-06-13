package com.house.home.client.service.resp;

import java.util.Date;

public class PrjProgQueryResp {
	
	private String code;
	private String address;
	private Integer progPk;
	private Integer pk;
	private String note;
	private Date planBegin;
	private Date beginDate;
	private Integer delay;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getProgPk() {
		return progPk;
	}
	public void setProgPk(Integer progPk) {
		this.progPk = progPk;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getPlanBegin() {
		return planBegin;
	}
	public void setPlanBegin(Date planBegin) {
		this.planBegin = planBegin;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Integer getDelay() {
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}

}
