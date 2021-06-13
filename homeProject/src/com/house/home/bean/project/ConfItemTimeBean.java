package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ConfItemTime信息bean
 */
public class ConfItemTimeBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="code", order=1)
	private String code;
	@ExcelAnnotation(exportName="descr", order=2)
	private String descr;
	@ExcelAnnotation(exportName="prjItem", order=3)
	private String prjItem;
	@ExcelAnnotation(exportName="dayType", order=4)
	private String dayType;
	@ExcelAnnotation(exportName="addDay", order=5)
	private Integer addDay;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=7)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="actionLog", order=8)
	private String actionLog;
	@ExcelAnnotation(exportName="expired", order=9)
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
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	
	public String getPrjItem() {
		return this.prjItem;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	
	public String getDayType() {
		return this.dayType;
	}
	public void setAddDay(Integer addDay) {
		this.addDay = addDay;
	}
	
	public Integer getAddDay() {
		return this.addDay;
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
