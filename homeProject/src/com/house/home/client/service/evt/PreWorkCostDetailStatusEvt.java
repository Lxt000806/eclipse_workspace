package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class PreWorkCostDetailStatusEvt extends BaseEvt{
	
	@NotNull(message="pk不能为空")
	private Integer pk;
	@NotEmpty(message="状态不能为空")
	private String status;
	
	private Integer checkStatus;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

}
