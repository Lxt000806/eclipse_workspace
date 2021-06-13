package com.house.home.client.service.resp;

public class ItemAppTempAreaDetailQueryResp {
	private Integer pk;
	private String itCode;
	private Double qty;
	private String ataNo;
	private String descr;
	private String uomDescr;
	private String remarks;
	private String itemType2;
	private Double sugQty;
	private Double needQty;
	private String confCode;
	private String fixAreaDescr;
	private String isConfirm;
	
	
	public String getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
	public String getFixAreaDescr() {
		return fixAreaDescr;
	}
	public void setFixAreaDescr(String fixAreaDescr) {
		this.fixAreaDescr = fixAreaDescr;
	}
	public String getConfCode() {
		return confCode;
	}
	public void setConfCode(String confCode) {
		this.confCode = confCode;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getAtaNo() {
		return ataNo;
	}
	public void setAtaNo(String ataNo) {
		this.ataNo = ataNo;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
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
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public Double getSugQty() {
		return sugQty;
	}
	public void setSugQty(Double sugQty) {
		this.sugQty = sugQty;
	}
	public Double getNeedQty() {
		return needQty;
	}
	public void setNeedQty(Double needQty) {
		this.needQty = needQty;
	}
	
}
