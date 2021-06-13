package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * PrjJobPhoto信息bean
 */
public class PrjJobPhotoBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="prjJobNo", order=2)
	private String prjJobNo;
	@ExcelAnnotation(exportName="type", order=3)
	private String type;
	@ExcelAnnotation(exportName="photoName", order=4)
	private String photoName;
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
	public void setPrjJobNo(String prjJobNo) {
		this.prjJobNo = prjJobNo;
	}
	
	public String getPrjJobNo() {
		return this.prjJobNo;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
	public String getPhotoName() {
		return this.photoName;
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
