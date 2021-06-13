package com.house.home.client.service.resp;

import java.util.Date;

public class GetExpenseAdvanceResp {
	private String no;
	private Double amount;
	private String actName;
	private String cardId;
	private String statusDescr;
	private String zwxm;
	private Date startTime;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getZwxm() {
		return zwxm;
	}
	public void setZwxm(String zwxm) {
		this.zwxm = zwxm;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	
}
