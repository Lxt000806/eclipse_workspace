package com.house.home.client.service.resp;

import java.util.Map;


public class GetWfProcInstDataResp extends BaseResponse {
	
	private Map<String, Object> datas;
	private Map<String, Object> detailDatas;
	private String procKey;
	
	public Map<String, Object> getDatas() {
		return datas;
	}
	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}
	public Map<String, Object> getDetailDatas() {
		return detailDatas;
	}
	public void setDetailDatas(Map<String, Object> detailDatas) {
		this.detailDatas = detailDatas;
	}
	public String getProcKey() {
		return procKey;
	}
	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}
	
}
