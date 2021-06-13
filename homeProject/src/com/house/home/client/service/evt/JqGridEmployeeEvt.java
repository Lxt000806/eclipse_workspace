package com.house.home.client.service.evt;

public class JqGridEmployeeEvt extends BaseQueryEvt{
	
	private String nameChi;
	private String wfProcNo;
	private String taskKey;
	private String role;
	private String custCode;
	private String isManager;
	private String startMan;
	private String expired;
	
	
	public String getIsManager() {
		return isManager;
	}

	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}

	public String getStartMan() {
		return startMan;
	}

	public void setStartMan(String startMan) {
		this.startMan = startMan;
	}

	public String getNameChi() {
		return nameChi;
	}

	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}

	public String getWfProcNo() {
		return wfProcNo;
	}

	public void setWfProcNo(String wfProcNo) {
		this.wfProcNo = wfProcNo;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

}
