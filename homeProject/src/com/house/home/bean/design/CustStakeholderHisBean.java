package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CustStakeholderHis信息bean
 */
public class CustStakeholderHisBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="operType", order=2)
	private String operType;
	@ExcelAnnotation(exportName="role", order=3)
	private String role;
	@ExcelAnnotation(exportName="oldRole", order=4)
	private String oldRole;
	@ExcelAnnotation(exportName="empCode", order=5)
	private String empCode;
	@ExcelAnnotation(exportName="oldEmpCode", order=6)
	private String oldEmpCode;
	@ExcelAnnotation(exportName="custCode", order=7)
	private String custCode;
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
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	public String getOperType() {
		return this.operType;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
	public void setOldRole(String oldRole) {
		this.oldRole = oldRole;
	}
	
	public String getOldRole() {
		return this.oldRole;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getEmpCode() {
		return this.empCode;
	}
	public void setOldEmpCode(String oldEmpCode) {
		this.oldEmpCode = oldEmpCode;
	}
	
	public String getOldEmpCode() {
		return this.oldEmpCode;
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
