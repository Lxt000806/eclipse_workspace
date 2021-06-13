package com.house.home.client.service.evt;

import java.util.Date;

public class GetCustomerDetailInfoEvt extends BaseEvt {
	private String custCode;
	private Date dateFrom;
	private Date dateTo;
	private String prjItems;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getPrjItems() {
		return prjItems;
	}
	public void setPrjItems(String prjItems) {
		this.prjItems = prjItems;
	}

	
}
