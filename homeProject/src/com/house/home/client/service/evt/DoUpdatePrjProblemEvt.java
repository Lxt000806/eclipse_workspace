package com.house.home.client.service.evt;

public class DoUpdatePrjProblemEvt extends BaseEvt {
	
	private String opSign;/*操作标志.0只更新状态,1更新内容和状态*/
	private String no;
	private String status;
	private String promDeptCode;
	private String promTypeCode;
	private String promPropCode;
	private String remarks;
	private String isBringStop;
	private String photoString;
	
	public String getOpSign() {
		return opSign;
	}
	public void setOpSign(String opSign) {
		this.opSign = opSign;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
