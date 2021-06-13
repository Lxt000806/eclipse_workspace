package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class GetActGiftDetailByPkResp extends BaseResponse {

	private Integer pk;
	private String ticketNo;
	private String actNo;
	private String itemCode;
	private String itemCodeDescr;
	private String custDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdate;
	private String lastUpdatedByDescr;
	private Double qty;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
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
	public String getCustDescr() {
		return custDescr;
	}
	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedByDescr() {
		return lastUpdatedByDescr;
	}
	public void setLastUpdatedByDescr(String lastUpdatedByDescr) {
		this.lastUpdatedByDescr = lastUpdatedByDescr;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
}
