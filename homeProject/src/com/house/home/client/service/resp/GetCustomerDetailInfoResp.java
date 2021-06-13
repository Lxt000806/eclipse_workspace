package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GetCustomerDetailInfoResp extends BaseResponse {
	
	private String custCode;
	private List<Map<String, Object>> datas;

	
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}
	
}
