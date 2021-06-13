package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class PreWorkCostDetailQueryEvt extends BaseQueryEvt{
	
	@NotEmpty(message="申请人不能为空")
	private String applyMan;
	private String address;
	private String status;
	
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
	public String getApplyMan() {
		return applyMan;
	}
	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}


}
