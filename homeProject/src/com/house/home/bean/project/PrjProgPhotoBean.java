package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * PrjProgPhoto信息bean
 */
public class PrjProgPhotoBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="prjItem", order=3)
	private String prjItem;
	@ExcelAnnotation(exportName="photoName", order=4)
	private String photoName;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=5)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=6)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=7)
	private String actionLog;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date lastUpdate;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	
	public String getPrjItem() {
		return this.prjItem;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
	public String getPhotoName() {
		return this.photoName;
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

}
