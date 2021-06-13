package com.house.home.client.service.resp;

import java.util.Date;

public class SitePreparationResp extends BaseResponse {
	private String custCode;
	private String address;
	private Date beginDate;
	private Date endDate;
	private String typeDescr;
	private int pk;
	private String type;
	private String buildStatus;
	private String buildStatusDescr;
	private String remark;
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBuildStatus() {
		return buildStatus;
	}
	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBuildStatusDescr() {
		return buildStatusDescr;
	}
	public void setBuildStatusDescr(String buildStatusDescr) {
		this.buildStatusDescr = buildStatusDescr;
	}
	
}
