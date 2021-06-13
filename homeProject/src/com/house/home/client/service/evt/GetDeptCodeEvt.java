package com.house.home.client.service.evt;

public class GetDeptCodeEvt extends BaseEvt{
	
	private String czybh;
	private String wfProcNo;
	
	public String getWfProcNo() {
		return wfProcNo;
	}

	public void setWfProcNo(String wfProcNo) {
		this.wfProcNo = wfProcNo;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

}
