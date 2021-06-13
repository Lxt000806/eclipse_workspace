package com.house.framework.commons.orm;

import java.util.Date;

/**
 * 实体基类
 */
public class BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	private String fetchId;//返回值的id
	
	private String oldStatus;//旧状态
	
	private String m_umState;//操作标志
	
	private int pageSize;
	
	private int pageNo;
	
	private String pageOrderBy;
	
	private String pageOrder;
	
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getFetchId() {
		return fetchId;
	}
	public void setFetchId(String fetchId) {
		this.fetchId = fetchId;
	}
	public String getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	public String getM_umState() {
		return m_umState;
	}
	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}
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
	public String getPageOrderBy() {
		return pageOrderBy;
	}
	public void setPageOrderBy(String pageOrderBy) {
		this.pageOrderBy = pageOrderBy;
	}
	public String getPageOrder() {
		return pageOrder;
	}
	public void setPageOrder(String pageOrder) {
		this.pageOrder = pageOrder;
	}

}
