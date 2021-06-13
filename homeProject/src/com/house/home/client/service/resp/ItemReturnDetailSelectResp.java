package com.house.home.client.service.resp;

import java.util.Date;

public class ItemReturnDetailSelectResp {
	private Integer pk;
	private String itemCode;
	private String itemCodeDescr;
	private String fixAreaDescr;
	private Double oldQty;
	private Double qty;
	private Double sendQty;
	private String uom;
	private String remarks;
	private Integer appDtpk;
	private Date sendDate;
	private Double cost;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemCodeDescr() {
		return itemCodeDescr;
	}
	public void setItemCodeDescr(String itemCodeDescr) {
		this.itemCodeDescr = itemCodeDescr;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getAppDtpk() {
		return appDtpk;
	}
	public void setAppDtpk(Integer appDtpk) {
		this.appDtpk = appDtpk;
	}
	public Double getOldQty() {
		return oldQty;
	}
	public void setOldQty(Double oldQty) {
		this.oldQty = oldQty;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	

}
