package com.house.home.client.service.evt;

public class GetCustMessageListEvt extends BaseQueryEvt{

	private String msgType;
	private String rcvCZY;
	private String rcvStatus;
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getRcvCZY() {
		return rcvCZY;
	}
	public void setRcvCZY(String rcvCZY) {
		this.rcvCZY = rcvCZY;
	}
	public String getRcvStatus() {
		return rcvStatus;
	}
	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
	}

}
