package com.house.home.client.service.resp;

public class GetActivityGiftListResp {
	private Integer pk;
	private String ticketNo;
	private String descr;
	private String itemCodeDescr;
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
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
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

}
