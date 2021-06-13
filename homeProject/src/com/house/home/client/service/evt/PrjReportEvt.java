package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;

public class PrjReportEvt extends BaseEvt{
	
	@NotNull(message="客户编号不能为空")
	private String custCode;
	private Integer pageNo;
	private Integer pageSize;
	private String typeCode;
	private int custScore;
	private String custRemarks;
	private int pks;
	private String no;
	
	
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public void setPks(int pks) {
		this.pks = pks;
	}
	public int getPks() {
		return pks;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public int getCustScore() {
		return custScore;
	}
	public void setCustScore(int custScore) {
		this.custScore = custScore;
	}
	public String getCustRemarks() {
		return custRemarks;
	}
	public void setCustRemarks(String custRemarks) {
		this.custRemarks = custRemarks;
	}
	
}
