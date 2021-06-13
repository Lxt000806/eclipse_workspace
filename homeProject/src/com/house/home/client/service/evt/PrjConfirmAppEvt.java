package com.house.home.client.service.evt;

import java.util.Date;

public class PrjConfirmAppEvt extends BaseQueryEvt {
    private String address;
    private String status;
    private String projectMan;
    
    private String custCode;
    private String remarks;
    
    private Integer pk;
    
    private String opSign;
    
    private String prjItem;
    
    private String fromInfoAdd;
    private Integer infoPk;
    
    private String workerCode;
    private String workType12;
    private String msgText;
    private Date endDate;
    private String isPass;
    
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getOpSign() {
		return opSign;
	}
	public void setOpSign(String opSign) {
		this.opSign = opSign;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
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
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
