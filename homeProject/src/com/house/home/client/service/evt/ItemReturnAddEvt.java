package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemReturnAddEvt extends BaseEvt{
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="材料类型1不能为空")
	private String itemType1;
	@NotEmpty(message="app操作员不能为空")
	private String appCzy;
	private String remarks;
	private String driverCode;
	private String sendBatchNo;
	private String xmlData;
	private String status;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getAppCzy() {
		return appCzy;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getSendBatchNo() {
		return sendBatchNo;
	}
	public void setSendBatchNo(String sendBatchNo) {
		this.sendBatchNo = sendBatchNo;
	}

}
