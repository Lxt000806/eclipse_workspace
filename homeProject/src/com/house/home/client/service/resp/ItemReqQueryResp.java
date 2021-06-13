package com.house.home.client.service.resp;

import java.util.List;

public class ItemReqQueryResp {
	private Integer pk;
	private String itemDescr;
	private String fixAreaDescr;
	private Double qty;
	private Double isCheckOutQty;
	private String custCode;
	private String itemType1;
	private String uom;
	private Double lineAmount;
	private String value;
	private String descr;
	private List<ItemReqQueryResp> itemType2Datas;
	private String intProdDescr;
	private double sendQty;
	
	

	public double getSendQty() {
		return sendQty;
	}
	public void setSendQty(double sendQty) {
		this.sendQty = sendQty;
	}
	public String getIntProdDescr() {
		return intProdDescr;
	}
	public void setIntProdDescr(String intProdDescr) {
		this.intProdDescr = intProdDescr;
	}
	public List<ItemReqQueryResp> getItemType2Datas() {
		return itemType2Datas;
	}
	public void setItemType2Datas(List<ItemReqQueryResp> itemType2Datas) {
		this.itemType2Datas = itemType2Datas;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public String getFixAreaDescr() {
		return fixAreaDescr;
	}
	public void setFixAreaDescr(String fixAreaDescr) {
		this.fixAreaDescr = fixAreaDescr;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getIsCheckOutQty() {
		return isCheckOutQty;
	}
	public void setIsCheckOutQty(Double isCheckOutQty) {
		this.isCheckOutQty = isCheckOutQty;
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
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Double getLineAmount() {
		return lineAmount;
	}
	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}

}
