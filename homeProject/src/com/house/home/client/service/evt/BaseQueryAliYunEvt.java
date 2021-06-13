package com.house.home.client.service.evt;

/**
 * 
 *功能说明:基础查询请求事件
 *
 */
public class BaseQueryAliYunEvt extends BaseEvt{
	
    //查询标记
    private String marker = "";
    //每页显示条数
    private int pageSize = 10;
    
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getMarker() {
		return marker;
	}
	public void setMarker(String marker) {
		this.marker = marker;
	}
    
}
