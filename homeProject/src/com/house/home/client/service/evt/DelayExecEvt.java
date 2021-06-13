package com.house.home.client.service.evt;

import java.util.Date;


public class DelayExecEvt extends BaseEvt{
	private Integer msgPk;
	private String czybh;
	private Date delayDate;
	private String delayReson;
	public Integer getMsgPk() {
		return msgPk;
	}
	public void setMsgPk(Integer msgPk) {
		this.msgPk = msgPk;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public Date getDelayDate() {
		return delayDate;
	}
	public void setDelayDate(Date delayDate) {
		this.delayDate = delayDate;
	}
	public String getDelayReson() {
		return delayReson;
	}
	public void setDelayReson(String delayReson) {
		this.delayReson = delayReson;
	}

}
