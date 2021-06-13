package com.house.home.client.service.resp;

public class CustComplaintResp {

	private String remarks;
	private Integer prjScope;
	private Integer designScope;
	private String canUpdate;
	private String no;
	
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCanUpdate() {
		return canUpdate;
	}
	public void setCanUpdate(String canUpdate) {
		this.canUpdate = canUpdate;
	}
	public Integer getPrjScope() {
		return prjScope;
	}
	public void setPrjScope(Integer prjScope) {
		this.prjScope = prjScope;
	}
	public Integer getDesignScope() {
		return designScope;
	}
	public void setDesignScope(Integer designScope) {
		this.designScope = designScope;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
