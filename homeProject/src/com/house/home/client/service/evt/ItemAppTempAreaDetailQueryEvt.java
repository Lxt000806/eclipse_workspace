package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemAppTempAreaDetailQueryEvt extends BaseQueryEvt {
	@NotEmpty(message="模板编号不能为空")
	private String no;
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	private String batchCode;
	private String confCode;
	private String appItemCodes;
	private String itemCode;
	private String flag;
	private String isSetItem;
	private String m_umStatus;
	
	
	public String getM_umStatus() {
		return m_umStatus;
	}
	public void setM_umStatus(String m_umStatus) {
		this.m_umStatus = m_umStatus;
	}
	public String getIsSetItem() {
		return isSetItem;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getAppItemCodes() {
		return appItemCodes;
	}
	public void setAppItemCodes(String appItemCodes) {
		this.appItemCodes = appItemCodes;
	}
	public String getConfCode() {
		return confCode;
	}
	public void setConfCode(String confCode) {
		this.confCode = confCode;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
}
