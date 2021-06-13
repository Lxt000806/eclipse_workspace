package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class GetCustServiceDetailResp extends BaseResponse {
	
	private String address;
	private String statusDescr;    //处理状态
	private String type;
	private String typeDescr;
	private String remarks;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date repDate;
	private String serviceManDescr;
	private String dealManDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date dealDate;
	private String status;
	private String custName;	//客户姓名
	private String mobile1;		//客户电话1
	private String projectManDescr;    //项目经理
	private String phone;       //项目经理电话
	private String undertakerDescr;    //承担人
	private String feedBackRemark;     //售后反馈
	private String dealStatusDescr;    
	private String endDate;     //结算日期
	private String no;			//记录编号
	private String addressStatus;   //楼盘状态
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getRepDate() {
		return repDate;
	}
	public void setRepDate(Date repDate) {
		this.repDate = repDate;
	}
	public String getServiceManDescr() {
		return serviceManDescr;
	}
	public void setServiceManDescr(String serviceManDescr) {
		this.serviceManDescr = serviceManDescr;
	}
	public String getDealManDescr() {
		return dealManDescr;
	}
	public void setDealManDescr(String dealManDescr) {
		this.dealManDescr = dealManDescr;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getProjectManDescr() {
		return projectManDescr;
	}
	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUndertakerDescr() {
		return undertakerDescr;
	}
	public void setUndertakerDescr(String undertakerDescr) {
		this.undertakerDescr = undertakerDescr;
	}
	public String getFeedBackRemark() {
		return feedBackRemark;
	}
	public void setFeedBackRemark(String feedBackRemark) {
		this.feedBackRemark = feedBackRemark;
	}
	public String getDealStatusDescr() {
		return dealStatusDescr;
	}
	public void setDealStatusDescr(String dealStatusDescr) {
		this.dealStatusDescr = dealStatusDescr;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getAddressStatus() {
		return addressStatus;
	}
	public void setAddressStatus(String addressStatus) {
		this.addressStatus = addressStatus;
	}

}
