package com.house.home.client.service.resp;

public class DesignXtdmResp extends BaseListQueryResp {
	private String value;
	private String descr;
	private String selected;
	
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
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	
}
