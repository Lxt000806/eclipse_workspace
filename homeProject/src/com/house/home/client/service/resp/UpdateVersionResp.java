package com.house.home.client.service.resp;

public class UpdateVersionResp extends BaseResponse{
	
	private String name;
	private String versionNo;
	private String compatible;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getCompatible() {
		return compatible;
	}
	public void setCompatible(String compatible) {
		this.compatible = compatible;
	}
	
	
}
