package com.house.home.client.service.resp;

/**
 * 
 *功能说明:获取工地是否验收通过
 *
 */
public class GetSiteConfirmStatusResp extends BaseResponse{
	
	private String exists;

	public String getExists() {
		return exists;
	}

	public void setExists(String exists) {
		this.exists = exists;
	}
	
	
}
