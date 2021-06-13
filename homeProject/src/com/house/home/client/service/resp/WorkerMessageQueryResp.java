package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class WorkerMessageQueryResp {

	private Integer pk;
	private String msgText;
	private String address;
	private String msgTextNoAddress;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date sendDate;
	private String rcvStatus;
	private String rcvType;
	private String rcvCzy;
	private String msgType;
	private String msgRelNo;
	private long number;
	private String prjItemDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date crtDate;
	private String isPassDescr;
	private String remarks;
	private int messageNum;
	
	
	public int getMessageNum() {
		return messageNum;
	}
	public void setMessageNum(int messageNum) {
		this.messageNum = messageNum;
	}
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getIsPassDescr() {
		return isPassDescr;
	}
	public void setIsPassDescr(String isPassDescr) {
		this.isPassDescr = isPassDescr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMsgTextNoAddress() {
		return msgTextNoAddress;
	}
	public void setMsgTextNoAddress(String msgTextNoAddress) {
		this.msgTextNoAddress = msgTextNoAddress;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getRcvStatus() {
		return rcvStatus;
	}
	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
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
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgRelNo() {
		return msgRelNo;
	}
	public void setMsgRelNo(String msgRelNo) {
		this.msgRelNo = msgRelNo;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	
	
}
