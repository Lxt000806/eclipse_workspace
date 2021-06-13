package com.house.home.client.service.evt;

import com.alibaba.fastjson.JSONObject;

public class GoJqGridByProcInstNoEvt extends BaseQueryEvt {
	
	private String wfProcInstNo;
	private String type;
	private String procDefKey;
	private JSONObject el;

	public JSONObject getEl() {
		return el;
	}
	public void setEl(JSONObject el) {
		this.el = el;
	}
	public String getWfProcInstNo() {
		return wfProcInstNo;
	}
	public void setWfProcInstNo(String wfProcInstNo) {
		this.wfProcInstNo = wfProcInstNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProcDefKey() {
		return procDefKey;
	}
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	
}
