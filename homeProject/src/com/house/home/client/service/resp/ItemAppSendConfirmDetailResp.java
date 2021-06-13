package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ItemAppSendConfirmDetailResp extends BaseResponse {
	private String address;
	private String confirmStatusDescr;
	private String no;
	private Date date;
	private String itemType1Descr;
	private String itemType2Descr;
	private String confirmReason;
	private String projectManRemark;
	private Double otherCost;
	private Double otherCostAdj;
	private List<Map<String,Object>> materialDetail;
	private Date appDate;
	
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getConfirmStatusDescr() {
		return confirmStatusDescr;
	}
	public void setConfirmStatusDescr(String confirmStatusDescr) {
		this.confirmStatusDescr = confirmStatusDescr;
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
	public String getItemType1Descr() {
		return itemType1Descr;
	}
	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}
	public String getItemType2Descr() {
		return itemType2Descr;
	}
	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}
	public String getConfirmReason() {
		return confirmReason;
	}
	public void setConfirmReason(String confirmReason) {
		this.confirmReason = confirmReason;
	}
	public String getProjectManRemark() {
		return projectManRemark;
	}
	public void setProjectManRemark(String projectManRemark) {
		this.projectManRemark = projectManRemark;
	}
	public List<Map<String, Object>> getMaterialDetail() {
		return materialDetail;
	}
	public void setMaterialDetail(List<Map<String, Object>> materialDetail) {
		this.materialDetail = materialDetail;
	}
	public Double getOtherCost() {
		return otherCost;
	}
	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}
	public Double getOtherCostAdj() {
		return otherCostAdj;
	}
	public void setOtherCostAdj(Double otherCostAdj) {
		this.otherCostAdj = otherCostAdj;
	}
	
}
