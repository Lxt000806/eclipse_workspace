package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class TicketQueryEvt extends BaseQueryEvt {
	/*@NotEmpty(message="活动编号不能为空")*///remove by zzr 修改到接口里通过requestFromPage判断,活动下定修改
	private String actNo;
	private String status;
	private String businessMan;
	private String custName;
	private boolean isSign;
	private String ticketNo;
	private String requestFromPage;
	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBusinessMan() {
		return businessMan;
	}
	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public boolean getIsSign() {
		return isSign;
	}
	public void setIsSign(boolean isSign) {
		this.isSign = isSign;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getRequestFromPage() {
		return requestFromPage;
	}
	public void setRequestFromPage(String requestFromPage) {
		this.requestFromPage = requestFromPage;
	}
	
}
