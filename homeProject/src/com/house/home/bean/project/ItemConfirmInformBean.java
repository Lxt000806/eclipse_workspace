package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemConfirmInform信息bean
 */
public class ItemConfirmInformBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="itemTimeCode", order=3)
	private String itemTimeCode;
    	@ExcelAnnotation(exportName="informDate", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date informDate;
    	@ExcelAnnotation(exportName="planComeDate", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date planComeDate;
	@ExcelAnnotation(exportName="informRemark", order=6)
	private String informRemark;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=7)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=8)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="actionLog", order=9)
	private String actionLog;
	@ExcelAnnotation(exportName="expired", order=10)
	private String expired;

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
	public void setItemTimeCode(String itemTimeCode) {
		this.itemTimeCode = itemTimeCode;
	}
	
	public String getItemTimeCode() {
		return this.itemTimeCode;
	}
	public void setInformDate(Date informDate) {
		this.informDate = informDate;
	}
	
	public Date getInformDate() {
		return this.informDate;
	}
	public void setPlanComeDate(Date planComeDate) {
		this.planComeDate = planComeDate;
	}
	
	public Date getPlanComeDate() {
		return this.planComeDate;
	}
	public void setInformRemark(String informRemark) {
		this.informRemark = informRemark;
	}
	
	public String getInformRemark() {
		return this.informRemark;
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
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}

}
