package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class WorkerItemAppResp extends BaseResponse {
	private String no;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date date;
	private String statusDescr;
	private String remark;
	private String remarks;
	private double stopDay;
	private String status;
	private Integer custWkPk;
	private String addres;
	private String itemType1Descr;
	private String isSetItem;
	private String recipient;
	private Date confirmDate;
	private String fixDescr;
	private double qty;
	private String itemDescr;
	private String itemType1;
	private String itemCode;
	private String workType12Descr;
	private double price;
	private double ratedMatrial;
	private Integer appliedMoney;
	
	
	
	
	
	public Integer getAppliedMoney() {
		return appliedMoney;
	}
	public void setAppliedMoney(Integer appliedMoney) {
		this.appliedMoney = appliedMoney;
	}
	public double getRatedMatrial() {
		return ratedMatrial;
	}
	public void setRatedMatrial(double ratedMatrial) {
		this.ratedMatrial = ratedMatrial;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getFixDescr() {
		return fixDescr;
	}
	public void setFixDescr(String fixDescr) {
		this.fixDescr = fixDescr;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getIsSetItem() {
		return isSetItem;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	public String getItemType1Descr() {
		return itemType1Descr;
	}
	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getStopDay() {
		return stopDay;
	}
	public void setStopDay(double stopDay) {
		this.stopDay = stopDay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public String getAddres() {
		return addres;
	}
	public void setAddres(String addres) {
		this.addres = addres;
	}
	
	
	
	
}
