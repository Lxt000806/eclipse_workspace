package com.house.home.client.service.evt;

public class DoSavePrjProblemEvt extends BaseEvt {
	
	private String custCode;
	private String promDeptCode;
	private String promTypeCode;
	private String promPropCode;
	private String remarks;
	private String isBringStop;
	private String photoString;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPromDeptCode() {
		return promDeptCode;
	}
	public void setPromDeptCode(String promDeptCode) {
		this.promDeptCode = promDeptCode;
	}
	public String getPromTypeCode() {
		return promTypeCode;
	}
	public void setPromTypeCode(String promTypeCode) {
		this.promTypeCode = promTypeCode;
	}
	public String getPromPropCode() {
		return promPropCode;
	}
	public void setPromPropCode(String promPropCode) {
		this.promPropCode = promPropCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIsBringStop() {
		return isBringStop;
	}
	public void setIsBringStop(String isBringStop) {
		this.isBringStop = isBringStop;
	}
	public String getPhotoString() {
		return photoString;
	}
	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}
	
}
