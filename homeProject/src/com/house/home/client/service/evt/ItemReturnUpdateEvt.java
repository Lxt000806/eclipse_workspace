package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemReturnUpdateEvt extends BaseEvt{
	@NotEmpty(message="退货申请编号不能为空")
	private String no;
	private String remarks;
	@NotEmpty(message="app操作员不能为空")
	private String appCzy;
	private String xmlData;
	private String status;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}
