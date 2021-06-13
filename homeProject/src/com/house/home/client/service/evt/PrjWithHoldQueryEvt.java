package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class PrjWithHoldQueryEvt extends BaseQueryEvt{
	
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="工种类型2不能为空")
	private String workType2;
	private String workType2Descr;
	private String withHoldNo;
	
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
	public String getWorkType2Descr() {
		return workType2Descr;
	}
	public void setWorkType2Descr(String workType2Descr) {
		this.workType2Descr = workType2Descr;
	}
	public String getWithHoldNo() {
		return withHoldNo;
	}
	public void setWithHoldNo(String withHoldNo) {
		this.withHoldNo = withHoldNo;
	}
	
	
}
