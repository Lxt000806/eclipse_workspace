package com.house.home.client.service.evt;

import java.util.Date;

public class DoCompleteCustServiceEvt extends BaseEvt {

	private String no;
	private String dealMan;
	private Date dealDate;

	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getDealMan() {
		return dealMan;
	}
	public void setDealMan(String dealMan) {
		this.dealMan = dealMan;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	
	
	
}
