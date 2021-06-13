package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;

public class PersonMessageUpdateEvt extends BaseEvt{
	
	@NotNull(message="pk不能为空")
	private Integer pk;
	private String rcvStatus;
	private String remarks;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getRcvStatus() {
		return rcvStatus;
	}
	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
