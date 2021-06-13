package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class IsCommitEvt extends BaseEvt{
	
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="工种类型2不能为空")
	private String workType2;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getWorkType2() {
		return workType2;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}

}
