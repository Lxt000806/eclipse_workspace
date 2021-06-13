package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CzyDept信息bean
 */
public class CzyDeptBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="czybh", order=2)
	private String czybh;
	@ExcelAnnotation(exportName="department1", order=3)
	private String department1;
	@ExcelAnnotation(exportName="department2", order=4)
	private String department2;
	@ExcelAnnotation(exportName="department3", order=5)
	private String department3;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=7)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=8)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=9)
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
	public String getCzybh() {
		return this.czybh;
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
