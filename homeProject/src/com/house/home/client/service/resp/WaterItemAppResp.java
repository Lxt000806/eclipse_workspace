package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class WaterItemAppResp extends BaseResponse {
	private String no;
	private double projectAmount;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date date;
	private String statusDescr;
	private String workerCode;
	private String remarks;
	private String appCzy;
	private String appCzyDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date confirmDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date sendDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date arriveDate;
	private Integer pk;
	private String itemCodeDescr;
	private Double qty;
	private Double sendQty;
	private String uom;
	private String isSetItem;
	private Double allProjectCost;
	private String address;
	
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public double getProjectAmount() {
		return projectAmount;
	}
	public void setProjectAmount(double projectAmount) {
		this.projectAmount = projectAmount;
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
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAppCzy() {
		return appCzy;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	public String getAppCzyDescr() {
		return appCzyDescr;
	}
	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getItemCodeDescr() {
		return itemCodeDescr;
	}
	public void setItemCodeDescr(String itemCodeDescr) {
		this.itemCodeDescr = itemCodeDescr;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getSendQty() {
		return sendQty;
	}
	public void setSendQty(Double sendQty) {
		this.sendQty = sendQty;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getIsSetItem() {
		return isSetItem;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	public Double getAllProjectCost() {
		return allProjectCost;
	}
	public void setAllProjectCost(Double allProjectCost) {
		this.allProjectCost = allProjectCost;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Date getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	
	
	
}	
