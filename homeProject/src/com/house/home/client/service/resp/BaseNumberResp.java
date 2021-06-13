package com.house.home.client.service.resp;

public class BaseNumberResp extends BaseResponse{
	
	private long number;
	private String msgType;
	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
