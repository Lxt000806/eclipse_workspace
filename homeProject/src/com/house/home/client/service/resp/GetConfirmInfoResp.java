package com.house.home.client.service.resp;

import java.util.Date;

public class GetConfirmInfoResp extends BaseResponse {
	private String no;
	private Integer unPassCount;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getUnPassCount() {
		return unPassCount;
	}
	public void setUnPassCount(Integer unPassCount) {
		this.unPassCount = unPassCount;
	}
	
}
