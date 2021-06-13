package com.house.home.client.service.resp;

import java.util.List;


public class GetOperatorResp extends BaseResponse {
	
	private List<Object> operateList;
	
	private String mistakeMsg;

	public List<Object> getOperateList() {
		return operateList;
	}

	public void setOperateList(List<Object> operateList) {
		this.operateList = operateList;
	}

	public String getMistakeMsg() {
		return mistakeMsg;
	}

	public void setMistakeMsg(String mistakeMsg) {
		this.mistakeMsg = mistakeMsg;
	}
}
