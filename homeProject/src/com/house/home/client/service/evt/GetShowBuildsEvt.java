package com.house.home.client.service.evt;

public class GetShowBuildsEvt extends BaseQueryEvt {
	
	private String prjItem1;
	private String areaSizeType;
	private String m_status;
	private String buildCode;
	
	
	public String getBuildCode() {
		return buildCode;
	}
	public void setBuildCode(String buildCode) {
		this.buildCode = buildCode;
	}
	public String getM_status() {
		return m_status;
	}
	public void setM_status(String m_status) {
		this.m_status = m_status;
	}
	public String getPrjItem1() {
		return prjItem1;
	}
	public void setPrjItem1(String prjItem1) {
		this.prjItem1 = prjItem1;
	}
	public String getAreaSizeType() {
		return areaSizeType;
	}
	public void setAreaSizeType(String areaSizeType) {
		this.areaSizeType = areaSizeType;
	}


}
