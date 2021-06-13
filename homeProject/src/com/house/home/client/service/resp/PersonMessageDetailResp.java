package com.house.home.client.service.resp;

import java.util.Date;

public class PersonMessageDetailResp extends BaseResponse{
	
	private Integer pk;
	private String msgType;
	private String msgText;
	private String msgRelNo;
	private String msgRelCustCode;
	private Date crtDate;
	private Date sendDate;
	private String rcvType;
	private String rcvCzy;
	private Date rcvDate;
	private String rcvStatus;
	private String isPush;
	private String pushStatus;
	private String address;
	private Date planDealDate;
	private Date planArrDate;
	private String remarks;
	private String title;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getMsgRelNo() {
		return msgRelNo;
	}
	public void setMsgRelNo(String msgRelNo) {
		this.msgRelNo = msgRelNo;
	}
	public String getMsgRelCustCode() {
		return msgRelCustCode;
	}
	public void setMsgRelCustCode(String msgRelCustCode) {
		this.msgRelCustCode = msgRelCustCode;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
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
	public Date getRcvDate() {
		return rcvDate;
	}
	public void setRcvDate(Date rcvDate) {
		this.rcvDate = rcvDate;
	}
	public String getRcvStatus() {
		return rcvStatus;
	}
	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getIsPush() {
		return isPush;
	}
	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}
	public String getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getPlanDealDate() {
		return planDealDate;
	}
	public void setPlanDealDate(Date planDealDate) {
		this.planDealDate = planDealDate;
	}
	public Date getPlanArrDate() {
		return planArrDate;
	}
	public void setPlanArrDate(Date planArrDate) {
		this.planArrDate = planArrDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	

}
