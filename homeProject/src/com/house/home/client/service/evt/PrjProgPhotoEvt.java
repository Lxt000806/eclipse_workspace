package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class PrjProgPhotoEvt extends BaseEvt{
	@NotEmpty
	private String custCode;
	@NotEmpty
	private String prjItem;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}

}
