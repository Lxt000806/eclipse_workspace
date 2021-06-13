package com.house.home.client.service.resp;

public class ItemAppDetailQueryResp {
	private Integer pk;
	private String itemCodeDescr;
	private Double qty;
	private Double sendQty;
	private String uom;
	private String isSetItem;
	private Double allProjectCost;
	private String address;
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	

	
	
}
