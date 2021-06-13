package com.house.home.client.service.resp;

public class XtdmQueryResp {
	
	private String id;
	private String cbm;
	private Integer ibm;
	private String note;
	private String value;
	private String descr;
	private String expired;
	
	
	
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCbm() {
		return cbm;
	}
	public void setCbm(String cbm) {
		this.cbm = cbm;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getIbm() {
		return ibm;
	}
	public void setIbm(Integer ibm) {
		this.ibm = ibm;
	}
	
	
}
