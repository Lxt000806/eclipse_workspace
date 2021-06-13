package com.house.home.client.service.resp;

public class EmployeeDetailResp extends BaseResponse{
	
    private String number;
    private String nameChi;
    private String phone;
    private String czybh;
    private String rongCloudMessage;
    private String department;
    private String prjRole;
    private String token;
    private String sessionId;
    private Double amount;
    private String msg;
    private String mm;
    
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getRongCloudMessage() {
		return rongCloudMessage;
	}
	public void setRongCloudMessage(String rongCloudMessage) {
		this.rongCloudMessage = rongCloudMessage;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPrjRole() {
		return prjRole;
	}
	public void setPrjRole(String prjRole) {
		this.prjRole = prjRole;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
