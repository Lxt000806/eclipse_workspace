package com.house.home.client.service.evt;

public class WorkerMessageEvt extends BaseQueryEvt{
	
	private Integer pk;
	private String msgType;
	private String rcvType;
	private String rcvCzy;
	private String rcvStatus;
	private String msgRelCustCode;
	private String address;
	private String progMsgType;
	private String timeoutFlag;
	
	
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getRcvType() {
		return rcvType;
	}
	public void setRcvType(String rcvType) {
		this.rcvType = rcvType;
	}
	public String getRcvCzy() {
		return rcvCzy;
	}
	public void setRcvCzy(String rcvCzy) {
		this.rcvCzy = rcvCzy;
	}
	public String getRcvStatus() {
		return rcvStatus;
	}
	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
	}
	public String getMsgRelCustCode() {
		return msgRelCustCode;
	}
	public void setMsgRelCustCode(String msgRelCustCode) {
		this.msgRelCustCode = msgRelCustCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProgMsgType() {
		return progMsgType;
	}
	public void setProgMsgType(String progMsgType) {
		this.progMsgType = progMsgType;
	}
	public String getTimeoutFlag() {
		return timeoutFlag;
	}
	public void setTimeoutFlag(String timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
	}
	
	
	
}
