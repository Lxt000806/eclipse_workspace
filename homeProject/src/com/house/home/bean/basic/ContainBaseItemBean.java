package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ContainBaseItem信息bean
 */
public class ContainBaseItemBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="baseItemCode", order=2)
	private String baseItemCode;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=3)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=4)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=5)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=6)
	private String actionLog;
	@ExcelAnnotation(exportName="custType", order=7)
	private String custType;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}
	
	public String getBaseItemCode() {
		return this.baseItemCode;
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
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}

}
