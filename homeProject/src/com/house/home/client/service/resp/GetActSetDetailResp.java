package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class GetActSetDetailResp extends BaseResponse {
	private Integer pk;
	private String ticketNo;
	private String actNo;
	private String supplType;
	private String supplCode;
	private String supplCodeDescr;
	private String descr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdate;
	private String lastUpdatedByDescr;
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
	public String getSupplType() {
		return supplType;
	}
	public void setSupplType(String supplType) {
		this.supplType = supplType;
	}
	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	public String getSupplCodeDescr() {
		return supplCodeDescr;
	}
	public void setSupplCodeDescr(String supplCodeDescr) {
		this.supplCodeDescr = supplCodeDescr;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
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
}
