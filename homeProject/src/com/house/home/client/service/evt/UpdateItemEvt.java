package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class UpdateItemEvt extends BaseEvt {
	@NotEmpty(message="批次编号不能为空")
	private String ibdNo;
	private String remarks;
	private String batchDetail;
	private String pk;
	public String getIbdNo() {
		return ibdNo;
	}
	public void setIbdNo(String ibdNo) {
		this.ibdNo = ibdNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getBatchDetail() {
		return batchDetail;
	}
	public void setBatchDetail(String batchDetail) {
		this.batchDetail = batchDetail;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	
}
