package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class TicketDetailEvt extends BaseEvt {
	private String actNo;
	@NotEmpty(message="门票号不能为空")
	private String ticketNo;
	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	
}
