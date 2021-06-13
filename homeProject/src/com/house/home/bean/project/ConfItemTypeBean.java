package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ConfItemType信息bean
 */
public class ConfItemTypeBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="code", order=1)
	private String code;
	@ExcelAnnotation(exportName="descr", order=2)
	private String descr;
	@ExcelAnnotation(exportName="itemTimeCode", order=3)
	private String itemTimeCode;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=5)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="actionLog", order=6)
	private String actionLog;
	@ExcelAnnotation(exportName="expired", order=7)
	private String expired;

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setItemTimeCode(String itemTimeCode) {
		this.itemTimeCode = itemTimeCode;
	}
	
	public String getItemTimeCode() {
		return this.itemTimeCode;
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
