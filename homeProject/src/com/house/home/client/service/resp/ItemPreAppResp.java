package com.house.home.client.service.resp;

import java.util.Date;

public class ItemPreAppResp extends BaseResponse{
	private String no;
	private String custCode;
	private String address;
	private String itemType1;
	private String itemType1Desc;
	private String isSetItem;
	private String isSetItemDesc;
	private String remarks;
	private Date date;
	private String statusDescr;
	private String confirmCzy;
	private String confirmCzyDescr;
	private Date confirmDate;
	private String endCodeDescr;
//	private List<String> photoList;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getIsSetItem() {
		return isSetItem;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getConfirmCzy() {
		return confirmCzy;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
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
	public String getItemType1Desc() {
		return itemType1Desc;
	}
	public void setItemType1Desc(String itemType1Desc) {
		this.itemType1Desc = itemType1Desc;
	}
	public String getIsSetItemDesc() {
		return isSetItemDesc;
	}
	public void setIsSetItemDesc(String isSetItemDesc) {
		this.isSetItemDesc = isSetItemDesc;
	}
	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}
	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}
	public String getEndCodeDescr() {
		return endCodeDescr;
	}
	public void setEndCodeDescr(String endCodeDescr) {
		this.endCodeDescr = endCodeDescr;
	}

}
