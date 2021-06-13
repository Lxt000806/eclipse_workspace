package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * PrjJob信息bean
 */
public class PrjJobBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="status", order=2)
	private String status;
	@ExcelAnnotation(exportName="custCode", order=3)
	private String custCode;
	@ExcelAnnotation(exportName="itemType1", order=4)
	private String itemType1;
	@ExcelAnnotation(exportName="jobType", order=5)
	private String jobType;
	@ExcelAnnotation(exportName="appCzy", order=6)
	private String appCzy;
    	@ExcelAnnotation(exportName="date", pattern="yyyy-MM-dd HH:mm:ss", order=7)
	private Date date;
	@ExcelAnnotation(exportName="remarks", order=8)
	private String remarks;
	@ExcelAnnotation(exportName="dealCzy", order=9)
	private String dealCzy;
    	@ExcelAnnotation(exportName="planDate", pattern="yyyy-MM-dd HH:mm:ss", order=10)
	private Date planDate;
    	@ExcelAnnotation(exportName="dealDate", pattern="yyyy-MM-dd HH:mm:ss", order=11)
	private Date dealDate;
	@ExcelAnnotation(exportName="dealRemark", order=12)
	private String dealRemark;
	@ExcelAnnotation(exportName="endCode", order=13)
	private String endCode;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=14)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=15)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=16)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=17)
	private String actionLog;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	public String getJobType() {
		return this.jobType;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setDealCzy(String dealCzy) {
		this.dealCzy = dealCzy;
	}
	
	public String getDealCzy() {
		return this.dealCzy;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	
	public Date getPlanDate() {
		return this.planDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	
	public Date getDealDate() {
		return this.dealDate;
	}
	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
	}
	
	public String getDealRemark() {
		return this.dealRemark;
	}
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
	
	public String getEndCode() {
		return this.endCode;
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
