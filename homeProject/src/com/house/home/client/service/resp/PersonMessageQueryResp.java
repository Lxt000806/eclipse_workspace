package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class PersonMessageQueryResp {
	
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
	
	//20171018 add zzr
	private String progMsgType;
	private String itemType1;
	private String itemType2;
	private String workType12;
	private String prjItem;
	private String jobType;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date planDealDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date planArrDate;
	private String msgRelCustCode;
	private String remarks;
	
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
	public String getMsgTextNoAddress() {
		return msgTextNoAddress;
	}
	public void setMsgTextNoAddress(String msgTextNoAddress) {
		this.msgTextNoAddress = msgTextNoAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getProgMsgType() {
		return progMsgType;
	}
	public void setProgMsgType(String progMsgType) {
		this.progMsgType = progMsgType;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
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
	public String getMsgRelCustCode() {
		return msgRelCustCode;
	}
	public void setMsgRelCustCode(String msgRelCustCode) {
		this.msgRelCustCode = msgRelCustCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
