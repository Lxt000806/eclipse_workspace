package com.house.home.client.service.resp;

public class ResrCustPoolResp extends BaseResponse {
	
	private String no;
	private String descr;
	private String type;
	private String isVirtualPhone;
	private String isHideChannel;
	private String defaultResrCustPoolNo;
	private String dispatchRule;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsVirtualPhone() {
		return isVirtualPhone;
	}
	public void setIsVirtualPhone(String isVirtualPhone) {
		this.isVirtualPhone = isVirtualPhone;
	}
	public String getIsHideChannel() {
		return isHideChannel;
	}
	public void setIsHideChannel(String isHideChannel) {
		this.isHideChannel = isHideChannel;
	}
	public String getDefaultResrCustPoolNo() {
		return defaultResrCustPoolNo;
	}
	public void setDefaultResrCustPoolNo(String defaultResrCustPoolNo) {
		this.defaultResrCustPoolNo = defaultResrCustPoolNo;
	}
	public String getDispatchRule() {
		return dispatchRule;
	}
	public void setDispatchRule(String dispatchRule) {
		this.dispatchRule = dispatchRule;
	}

	
}
