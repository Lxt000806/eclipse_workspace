package com.house.home.client.service.resp;

public class ItemBatchDetailQueryResp {
	private String code;
	private String descr;
	private String itemType1;
	private String isSetItem;
	private String uomDescr;
	private String remarks;
	private Integer bdpk;
	private String remark;//旧的字段
	private String itemType2;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
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
	public String getUomDescr() {
		return uomDescr;
	}
	public void setUomDescr(String uomDescr) {
		this.uomDescr = uomDescr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getBdpk() {
		return bdpk;
	}
	public void setBdpk(Integer bdpk) {
		this.bdpk = bdpk;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	
}
