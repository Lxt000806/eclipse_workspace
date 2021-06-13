package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CustItemConfProgDt信息bean
 */
public class CustItemConfProgDtBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="confProgNo", order=2)
	private String confProgNo;
	@ExcelAnnotation(exportName="confItemType", order=3)
	private String confItemType;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=4)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="actionLog", order=5)
	private String actionLog;
	@ExcelAnnotation(exportName="expired", order=6)
	private String expired;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=7)
	private Date lastUpdate;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setConfProgNo(String confProgNo) {
		this.confProgNo = confProgNo;
	}
	
	public String getConfProgNo() {
		return this.confProgNo;
	}
	public void setConfItemType(String confItemType) {
		this.confItemType = confItemType;
	}
	
	public String getConfItemType() {
		return this.confItemType;
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
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

}
