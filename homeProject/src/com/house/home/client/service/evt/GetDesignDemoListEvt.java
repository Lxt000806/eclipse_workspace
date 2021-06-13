package com.house.home.client.service.evt;

public class GetDesignDemoListEvt extends BaseQueryEvt {
	
	private String layout;
	private String style;
	private String areaSizeType;
	private String m_status;
	private String builderCode;
	
	
	
	public String getBuilderCode() {
		return builderCode;
	}
	public void setBuilderCode(String builderCode) {
		this.builderCode = builderCode;
	}
	public String getM_status() {
		return m_status;
	}
	public void setM_status(String m_status) {
		this.m_status = m_status;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getAreaSizeType() {
		return areaSizeType;
	}
	public void setAreaSizeType(String areaSizeType) {
		this.areaSizeType = areaSizeType;
	}


}
