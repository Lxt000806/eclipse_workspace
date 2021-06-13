package com.house.home.client.service.resp;

@SuppressWarnings("rawtypes")
public class RegionResp extends BaseListQueryResp{
	private String code;
	private String descr;
	private boolean hasNext;
	
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

	
}
