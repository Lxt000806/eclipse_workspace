package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemPreAppUpdateEvt extends BaseEvt{
	@NotEmpty(message="预领料编号不能为空")
	private String no;
	private String remarks;
	@NotEmpty(message="app操作员不能为空")
	private String appCzy;
	private String photoNames;
	private String xmlData;
	private String status;
	private String confirmRemark;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getPhotoNames() {
		return photoNames;
	}
	public void setPhotoNames(String photoNames) {
		this.photoNames = photoNames;
	}
	public String getXmlData() {
		return xmlData;
	}
	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}
	public String getAppCzy() {
		return appCzy;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	public String getConfirmRemark() {
		return confirmRemark;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}


}
