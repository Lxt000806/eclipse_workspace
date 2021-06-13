package com.house.home.client.service.evt;

public class GetActivitySetListEvt extends BaseQueryEvt {
	private String actNo;
	private String ticketNo;
	private String tokenCzyDescr;

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
	public String getTokenCzyDescr() {
		return tokenCzyDescr;
	}
	public void setTokenCzyDescr(String tokenCzyDescr) {
		this.tokenCzyDescr = tokenCzyDescr;
	}
	
}
