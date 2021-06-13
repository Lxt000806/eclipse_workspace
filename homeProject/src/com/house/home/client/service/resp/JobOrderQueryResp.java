package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class JobOrderQueryResp {
	
	private String msgTextTemp;
	private String address;
	private String prjItemDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date rcvDate;
	private String dayType;
	private Integer addDay;
	private String nameChi;
	private String timeoutFlag;
	private String msgRelNo;
	private String alarmDate;
	private String code;
	private String descr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date sendDate;
	
	
	
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getMsgTextTemp() {
		return msgTextTemp;
	}
	public void setMsgTextTemp(String msgTextTemp) {
		this.msgTextTemp = msgTextTemp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}
	public Date getRcvDate() {
		return rcvDate;
	}
	public void setRcvDate(Date rcvDate) {
		this.rcvDate = rcvDate;
	}
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public Integer getAddDay() {
		return addDay;
	}
	public void setAddDay(Integer addDay) {
		this.addDay = addDay;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getTimeoutFlag() {
		return timeoutFlag;
	}
	public void setTimeoutFlag(String timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
	}
	public String getMsgRelNo() {
		return msgRelNo;
	}
	public void setMsgRelNo(String msgRelNo) {
		this.msgRelNo = msgRelNo;
	}
	public String getAlarmDate() {
		return alarmDate;
	}
	public void setAlarmDate(String alarmDate) {
		this.alarmDate = alarmDate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	

}
