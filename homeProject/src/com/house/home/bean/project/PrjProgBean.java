package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * PrjProg信息bean
 */
public class PrjProgBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="prjItem", order=3)
	private String prjItem;
    	@ExcelAnnotation(exportName="planBegin", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date planBegin;
    	@ExcelAnnotation(exportName="beginDate", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date beginDate;
    	@ExcelAnnotation(exportName="planEnd", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date planEnd;
    	@ExcelAnnotation(exportName="endDate", pattern="yyyy-MM-dd HH:mm:ss", order=7)
	private Date endDate;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=9)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=10)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=11)
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	
	public String getPrjItem() {
		return this.prjItem;
	}
	public void setPlanBegin(Date planBegin) {
		this.planBegin = planBegin;
	}
	
	public Date getPlanBegin() {
		return this.planBegin;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getBeginDate() {
		return this.beginDate;
	}
	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}
	
	public Date getPlanEnd() {
		return this.planEnd;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}

}
