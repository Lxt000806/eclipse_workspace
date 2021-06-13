package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class DealPrjJobQueryEvt extends  BaseQueryEvt {
	@NotEmpty(message="处理人不能为空")
	private String dealCzy;
	private String address;
	private String status;
	private String jobType;
	public String getDealCzy() {
		return dealCzy;
	}
	public void setDealCzy(String dealCzy) {
		this.dealCzy = dealCzy;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setJobType(String jobType){
		this.jobType = jobType;
	}
	public String getJobType(){
		return jobType;
	}
}
