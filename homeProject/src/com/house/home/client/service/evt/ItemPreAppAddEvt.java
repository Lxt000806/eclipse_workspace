package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemPreAppAddEvt extends BaseEvt{
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="材料类型1不能为空")
	private String itemType1;
	private String isSetItem;
	@NotEmpty(message="app操作员不能为空")
	private String appCzy;
	private String remarks;
	private String photoNames;
	private String xmlData;
	private String status;
	private String workerApp;
    
    private String fromInfoAdd;
    private Integer infoPk;
    private String confirmRemark;
	
    
    
    public String getWorkerApp() {
		return workerApp;
	}
	public void setWorkerApp(String workerApp) {
		this.workerApp = workerApp;
	}
	private String itemConfCheck; // add by zzr 2017/12/18 是否检查主材跟踪结果
    
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
	public String getIsSetItem() {
		return isSetItem;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFromInfoAdd() {
		return fromInfoAdd;
	}
	public void setFromInfoAdd(String fromInfoAdd) {
		this.fromInfoAdd = fromInfoAdd;
	}
	public Integer getInfoPk() {
		return infoPk;
	}
	public void setInfoPk(Integer infoPk) {
		this.infoPk = infoPk;
	}
	public String getItemConfCheck() {
		return itemConfCheck;
	}
	public void setItemConfCheck(String itemConfCheck) {
		this.itemConfCheck = itemConfCheck;
	}
	public String getConfirmRemark() {
		return confirmRemark;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}

}
