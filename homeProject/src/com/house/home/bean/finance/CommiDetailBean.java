package com.house.home.bean.finance;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CommiDetail信息bean
 */
public class CommiDetailBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="calNo", order=2)
	private String calNo;
	@ExcelAnnotation(exportName="refRulePk", order=3)
	private Integer refRulePk;
	@ExcelAnnotation(exportName="type", order=4)
	private String type;
	@ExcelAnnotation(exportName="custCode", order=5)
	private String custCode;
	@ExcelAnnotation(exportName="sino", order=6)
	private String sino;
	@ExcelAnnotation(exportName="role", order=7)
	private String role;
	@ExcelAnnotation(exportName="empCode", order=8)
	private String empCode;
	@ExcelAnnotation(exportName="period", order=9)
	private String period;
	@ExcelAnnotation(exportName="amount", order=10)
	private Double amount;
	@ExcelAnnotation(exportName="remarks", order=11)
	private String remarks;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=12)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=13)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=14)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=15)
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCalNo(String calNo) {
		this.calNo = calNo;
	}
	
	public String getCalNo() {
		return this.calNo;
	}
	public void setRefRulePk(Integer refRulePk) {
		this.refRulePk = refRulePk;
	}
	
	public Integer getRefRulePk() {
		return this.refRulePk;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setSino(String sino) {
		this.sino = sino;
	}
	
	public String getSino() {
		return this.sino;
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
	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getPeriod() {
		return this.period;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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
