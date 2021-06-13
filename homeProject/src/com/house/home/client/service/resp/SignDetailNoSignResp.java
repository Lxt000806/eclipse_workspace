package com.house.home.client.service.resp;

import java.util.Date;

public class SignDetailNoSignResp extends BaseResponse{

	private String address;
	private String workerName;
	private String phone;
	private Date comeDate;
	private Date lastCrtDate;
	private String empName;
	private String remark;
	private Date date;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getComeDate() {
		return comeDate;
	}
	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}
	public Date getLastCrtDate() {
		return lastCrtDate;
	}
	public void setLastCrtDate(Date lastCrtDate) {
		this.lastCrtDate = lastCrtDate;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
}
