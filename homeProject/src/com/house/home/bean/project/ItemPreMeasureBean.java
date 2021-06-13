package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemPreMeasure信息bean
 */
public class ItemPreMeasureBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="supplCode", order=2)
	private String supplCode;
	@ExcelAnnotation(exportName="status", order=3)
	private String status;
	@ExcelAnnotation(exportName="remarks", order=4)
	private String remarks;
	@ExcelAnnotation(exportName="preAppNo", order=5)
	private String preAppNo;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=6)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=7)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=8)
	private String actionLog;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=9)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="measureRemark", order=10)
	private String measureRemark;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	
	public String getSupplCode() {
		return this.supplCode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setPreAppNo(String preAppNo) {
		this.preAppNo = preAppNo;
	}
	
	public String getPreAppNo() {
		return this.preAppNo;
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
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setMeasureRemark(String measureRemark) {
		this.measureRemark = measureRemark;
	}
	
	public String getMeasureRemark() {
		return this.measureRemark;
	}

}
