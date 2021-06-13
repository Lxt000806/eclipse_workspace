package com.house.home.client.service.resp;

import java.util.Date;


public class CustConResp {

	private int pk;	
	private String custCode;
	private String custAddress;
	private Date conDate;
	private String conMan;
	private String conManDescr;
	private String designManDescr;
	private String businessManDescr;
	private String remarks;
	private String resrCustCode;
	private String type;
	private String typeDescr;
	private int conDuration;
	private String callRecord;
	private String callRecordStatus;
	private String mobileFilePath;
	private Date nextConDate;
	private String conWay;
	
	
	public String getResrCustCode() {
		return resrCustCode;
	}
	public void setResrCustCode(String resrCustCode) {
		this.resrCustCode = resrCustCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeDescr() {
		return typeDescr;
	}
	public void setTypeDescr(String typeDescr) {
		this.typeDescr = typeDescr;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public Date getConDate() {
		return conDate;
	}
	public void setConDate(Date conDate) {
		this.conDate = conDate;
	}
	public String getConMan() {
		return conMan;
	}
	public void setConMan(String conMan) {
		this.conMan = conMan;
	}
	public String getConManDescr() {
		return conManDescr;
	}
	public void setConManDescr(String conManDescr) {
		this.conManDescr = conManDescr;
	}
	public String getDesignManDescr() {
		return designManDescr;
	}
	public void setDesignManDescr(String designManDescr) {
		this.designManDescr = designManDescr;
	}
	public String getBusinessManDescr() {
		return businessManDescr;
	}
	public void setBusinessManDescr(String businessManDescr) {
		this.businessManDescr = businessManDescr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getConDuration() {
		return conDuration;
	}
	public void setConDuration(int conDuration) {
		this.conDuration = conDuration;
	}
	public String getCallRecord() {
		return callRecord;
	}
	public void setCallRecord(String callRecord) {
		this.callRecord = callRecord;
	}
	public String getMobileFilePath() {
		return mobileFilePath;
	}
	public void setMobileFilePath(String mobileFilePath) {
		this.mobileFilePath = mobileFilePath;
	}
	public String getCallRecordStatus() {
		return callRecordStatus;
	}
	public void setCallRecordStatus(String callRecordStatus) {
		this.callRecordStatus = callRecordStatus;
	}
	public Date getNextConDate() {
		return nextConDate;
	}
	public void setNextConDate(Date nextConDate) {
		this.nextConDate = nextConDate;
	}
	public String getConWay() {
		return conWay;
	}
	public void setConWay(String conWay) {
		this.conWay = conWay;
	}
	
}
