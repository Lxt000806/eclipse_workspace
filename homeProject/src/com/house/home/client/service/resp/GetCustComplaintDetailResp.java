package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

public class GetCustComplaintDetailResp extends BaseResponse {
	
	private String address;
	
	private String projectManDescr;
	
	private String source;
	
	private String status;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date crtDate;
	
	private String remarks;
	
	private List<Map<String, Object>> complaintDetailList;
	
	private String custCode;
	
	private String custType;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProjectManDescr() {
		return projectManDescr;
	}
	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<Map<String, Object>> getComplaintDetailList() {
		return complaintDetailList;
	}
	public void setComplaintDetailList(List<Map<String, Object>> complaintDetailList) {
		this.complaintDetailList = complaintDetailList;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	
}
