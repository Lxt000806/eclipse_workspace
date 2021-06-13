package com.house.home.client.service.evt;

/**
 * 
 *功能说明:基础查询请求事件
 *
 */
public class BaseQueryEvt extends BaseEvt{
	
	//查询条件
    private String id;
    //查询页号
    private int pageNo = 1;
    //每页显示条数
    private int pageSize = 10;
    
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    

}
