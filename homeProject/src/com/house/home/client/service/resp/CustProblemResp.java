package com.house.home.client.service.resp;

@SuppressWarnings("rawtypes")
public class CustProblemResp extends BaseListQueryResp{

	private String address;
	private String statusDescr;
	private String status;
	private Integer pk;
	private String custCode;
	private String remarks;
	private String dealCzyDescr;
	
	public String getDealCzyDescr() {
		return dealCzyDescr;
	}
	public void setDealCzyDescr(String dealCzyDescr) {
		this.dealCzyDescr = dealCzyDescr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
}
