package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class UpdateSiteModifyEvt extends BaseEvt {
	@NotEmpty(message="巡检编号不能为空")
	private String no;
	private String remarks;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
