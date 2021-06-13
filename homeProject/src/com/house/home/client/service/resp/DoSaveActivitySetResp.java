package com.house.home.client.service.resp;

public class DoSaveActivitySetResp extends BaseResponse {
	private String actNo;
	private String ticketNo;
	private String supplType;
	private String supplCode;
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
	public String getSupplType() {
		return supplType;
	}
	public void setSupplType(String supplType) {
		this.supplType = supplType;
	}
	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
}
