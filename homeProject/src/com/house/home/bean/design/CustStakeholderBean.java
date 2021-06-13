package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CustStakeholder信息bean
 */
public class CustStakeholderBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="role", order=2)
	private String role;
	@ExcelAnnotation(exportName="empCode", order=3)
	private String empCode;
	@ExcelAnnotation(exportName="custCode", order=4)
	private String custCode;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=6)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=7)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=8)
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getEmpCode() {
		return this.empCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
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
