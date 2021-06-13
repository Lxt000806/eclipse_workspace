package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class JobOrderQueryEvt extends BaseQueryEvt{
	
	private String custCode;
	private String prjItem;
	private String workType12;
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
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	
	

}
