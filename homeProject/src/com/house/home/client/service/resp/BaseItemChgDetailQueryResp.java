package com.house.home.client.service.resp;

public class BaseItemChgDetailQueryResp {
	private Integer pk;
	private String baseItemDescr;
	private String fixAreaDescr;
	private Double qty;
	private String uom;
	private Double lineAmount;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getBaseItemDescr() {
		return baseItemDescr;
	}
	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
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
