package com.house.home.client.service.resp;

public class TicketResp extends BaseResponse {
	private Integer pk;
	private String actName;
	private String actNo;
	private String ticketNo;
	private String descr;
	private String address;
	private String businessManDescr;
	private String businessManPhone;
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBusinessManDescr() {
		return businessManDescr;
	}
	public void setBusinessManDescr(String businessManDescr) {
		this.businessManDescr = businessManDescr;
	}
	public String getBusinessManPhone() {
		return businessManPhone;
	}
	public void setBusinessManPhone(String businessManPhone) {
		this.businessManPhone = businessManPhone;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
}
