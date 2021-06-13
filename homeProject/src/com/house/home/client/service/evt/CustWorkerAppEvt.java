package com.house.home.client.service.evt;

import java.util.Date;

public class CustWorkerAppEvt extends BaseQueryEvt {
    private String address;
    private String status;
    private String projectMan;
    
    private String custCode;
    private String workType12;
    private Date appComeDate;
    private String remark;
    
    private Integer pk;
    
    private String opSign;
    
    private String fromInfoAdd;
    private Integer infoPk;
    private String checkPayFlag;
    
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
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public Date getAppComeDate() {
		return appComeDate;
	}
	public void setAppComeDate(Date appComeDate) {
		this.appComeDate = appComeDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getCheckPayFlag() {
		return checkPayFlag;
	}
	public void setCheckPayFlag(String checkPayFlag) {
		this.checkPayFlag = checkPayFlag;
	}
	
}
