package com.house.home.client.service.resp;

public class ItemBatchHeaderQueryResp {
	private String no;
	private String remarks;
	private String batchType;
	private String itemType1;
	private String descr;
	private String code;
	private String existApplied;
	
	
	
	public String getExistApplied() {
		return existApplied;
	}
	public void setExistApplied(String existApplied) {
		this.existApplied = existApplied;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getBatchType() {
		return batchType;
	}
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	
}
