package com.house.home.client.service.evt;

public class DoSaveCallListEvt extends BaseEvt{
	
	private String callList;
	private int callListLength;
	private Long beginTime;
	
	public String getCallList() {
		return callList;
	}

	public void setCallList(String callList) {
		this.callList = callList;
	}

	public int getCallListLength() {
		return callListLength;
	}

	public void setCallListLength(int callListLength) {
		this.callListLength = callListLength;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
}
