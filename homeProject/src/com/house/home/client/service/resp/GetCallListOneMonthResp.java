package com.house.home.client.service.resp;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;

public class GetCallListOneMonthResp {
	private String name;
	private String callTypeDescr;
	private int conDuration;
	private Date conDate;
	private String callRecord;
	private String status;
	private String mobileFilePath;
	private String type;
	private String remarks;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCallTypeDescr() {
		return callTypeDescr;
	}
	public void setCallTypeDescr(String callTypeDescr) {
		this.callTypeDescr = callTypeDescr;
	}
	public int getConDuration() {
		return conDuration;
	}
	public void setConDuration(int conDuration) {
		this.conDuration = conDuration;
	}
	public Date getConDate() {
		return conDate;
	}
	public void setConDate(Date conDate) {
		this.conDate = conDate;
	}
	public String getCallRecord() {
		return callRecord;
	}
	public void setCallRecord(String callRecord) {
		this.callRecord = callRecord;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMobileFilePath() {
		return mobileFilePath;
	}
	public void setMobileFilePath(String mobileFilePath) {
		this.mobileFilePath = mobileFilePath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	} 
	
}
