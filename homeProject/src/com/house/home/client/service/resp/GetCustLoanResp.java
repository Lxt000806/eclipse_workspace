package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class GetCustLoanResp extends BaseResponse{

	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date agreeDate;
	
	private String bank;
	
	private String followRemark;
	
	private String signRemark;
	
	private String confuseRemark;
	
	private Double amount;
	
	private Double firstAmount;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date firstDate;
	
	private Double secondAmount;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date secondDate;

	public Date getAgreeDate() {
		return agreeDate;
	}

	public void setAgreeDate(Date agreeDate) {
		this.agreeDate = agreeDate;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getFollowRemark() {
		return followRemark;
	}

	public void setFollowRemark(String followRemark) {
		this.followRemark = followRemark;
	}

	public String getSignRemark() {
		return signRemark;
	}

	public void setSignRemark(String signRemark) {
		this.signRemark = signRemark;
	}

	public String getConfuseRemark() {
		return confuseRemark;
	}

	public void setConfuseRemark(String confuseRemark) {
		this.confuseRemark = confuseRemark;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(Double firstAmount) {
		this.firstAmount = firstAmount;
	}

	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public Double getSecondAmount() {
		return secondAmount;
	}

	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}

	public Date getSecondDate() {
		return secondDate;
	}

	public void setSecondDate(Date secondDate) {
		this.secondDate = secondDate;
	}
	
}
