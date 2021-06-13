package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class PersonMessageEvt extends BaseEvt{
	@NotEmpty(message="接收人类型不能为空")
	private String rcvType;
	@NotEmpty(message="接收人不能为空")
	private String rcvCzy;
	private String msgType;
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
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
