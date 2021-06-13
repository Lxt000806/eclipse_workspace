package com.house.home.client.service.evt;

import com.alibaba.fastjson.JSONObject;


public class GetOperatorEvt extends BaseEvt {
	private JSONObject el;
	private String pdID;
	private String department;
	private String wfProcNo;
	public JSONObject getEl() {
		return el;
	}
	public void setEl(JSONObject el) {
		this.el = el;
	}
	public String getPdID() {
		return pdID;
	}
	public void setPdID(String pdID) {
		this.pdID = pdID;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getWfProcNo() {
		return wfProcNo;
	}
	public void setWfProcNo(String wfProcNo) {
		this.wfProcNo = wfProcNo;
	}
	
	
}
