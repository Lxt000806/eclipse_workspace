package com.house.home.client.service.resp;

import java.util.List;
import java.util.Map;

public class GoJqGridByProcInstNoResp extends BaseResponse {
	private List<Map<String, Object>> datas;

	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}
	
}
