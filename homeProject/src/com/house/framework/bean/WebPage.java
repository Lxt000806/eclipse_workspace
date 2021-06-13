package com.house.framework.bean;

import java.util.List;

import com.house.framework.commons.orm.Page;

/** 
* @ClassName: WebPage 
* @Description: 封装jqGrid展示所需数据 
* @author liuxiaobin
* @date 2015年8月21日 上午9:49:53 
*  
*/
public class WebPage<T> {
	
	private int page;//第几页
	private long total;//总页数
	private long records;//总记录数
	private List<T> rows;//记录列表
	private StringBuilder hideColumns = new StringBuilder(); //隐藏列
	
	public WebPage(Page<T> page){
		this.page = page.getPageNo();
		this.total = page.getTotalPages();
		this.records = page.getTotalCount();
		this.rows = page.getResult();
	}
	
	public WebPage(Integer page,Integer total,Integer records,List<T> rows){
		this.page = page;
		this.total = total;
		this.records = records;
		this.rows = rows;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getRecords() {
		return records;
	}
	public void setRecords(long records) {
		this.records = records;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public String getHideColumns() {
		return hideColumns.toString();
	}

	public void setHideColumns(String hideColumns) {
		if(this.hideColumns.length() > 0){
			this.hideColumns.append("|");
		}
		this.hideColumns.append(hideColumns);
	}
	
}
