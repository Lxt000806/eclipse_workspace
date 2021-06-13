package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * Roll信息bean
 */
public class RollBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="code", order=1)
	private String code;
	@ExcelAnnotation(exportName="descr", order=2)
	private String descr;
	@ExcelAnnotation(exportName="childCode", order=3)
	private String childCode;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=5)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=6)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=7)
	private String actionLog;
	@ExcelAnnotation(exportName="department1", order=8)
	private String department1;
	@ExcelAnnotation(exportName="department2", order=9)
	private String department2;
	@ExcelAnnotation(exportName="department3", order=10)
	private String department3;
	@ExcelAnnotation(exportName="isSale", order=11)
	private String isSale;

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
	public void setChildCode(String childCode) {
		this.childCode = childCode;
	}
	
	public String getChildCode() {
		return this.childCode;
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
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	
	public String getDepartment1() {
		return this.department1;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	
	public String getDepartment2() {
		return this.department2;
	}
	public void setDepartment3(String department3) {
		this.department3 = department3;
	}
	
	public String getDepartment3() {
		return this.department3;
	}
	public void setIsSale(String isSale) {
		this.isSale = isSale;
	}
	
	public String getIsSale() {
		return this.isSale;
	}

}
