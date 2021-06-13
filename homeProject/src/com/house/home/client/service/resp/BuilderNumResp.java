package com.house.home.client.service.resp;


@SuppressWarnings("rawtypes")
public class BuilderNumResp extends BaseListQueryResp{
	
	private String builderNum;
	private String builderDescr;
	private boolean hasNext;
	
	
	public String getBuilderNum() {
		return builderNum;
	}
	public void setBuilderNum(String builderNum) {
		this.builderNum = builderNum;
	}
	public String getBuilderDescr() {
		return builderDescr;
	}
	public void setBuilderDescr(String builderDescr) {
		this.builderDescr = builderDescr;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	
	
	
	
}
