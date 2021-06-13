package com.house.home.client.service.resp;

public class BasePageQueryAliYunResp<T> extends BaseListQueryResp<T>{
	//是否有下一页
	private boolean hasNext;
	
	private String marker;
	
	private String nextMarker;
	
	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getNextMarker() {
		return nextMarker;
	}

	public void setNextMarker(String nextMarker) {
		this.nextMarker = nextMarker;
	}

}
