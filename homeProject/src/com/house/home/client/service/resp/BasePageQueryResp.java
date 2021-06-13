package com.house.home.client.service.resp;

public class BasePageQueryResp<T> extends BaseListQueryResp<T>{
	
	private Long totalPage;
	//是否有下一页
	private boolean hasNext;
	
	private int pageNo;
	
	private String noListTip;
	
	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getNoListTip() {
		return noListTip;
	}

	public void setNoListTip(String noListTip) {
		this.noListTip = noListTip;
	}

}
