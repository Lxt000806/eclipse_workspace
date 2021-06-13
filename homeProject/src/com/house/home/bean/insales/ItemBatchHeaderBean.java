package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemBatchHeader信息bean
 */
public class ItemBatchHeaderBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
    	@ExcelAnnotation(exportName="date", pattern="yyyy-MM-dd HH:mm:ss", order=2)
	private Date date;
	@ExcelAnnotation(exportName="crtCzy", order=3)
	private String crtCzy;
	@ExcelAnnotation(exportName="itemType1", order=4)
	private String itemType1;
	@ExcelAnnotation(exportName="remarks", order=5)
	private String remarks;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=7)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=8)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=9)
	private String actionLog;
	@ExcelAnnotation(exportName="batchType", order=10)
	private String batchType;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setCrtCzy(String crtCzy) {
		this.crtCzy = crtCzy;
	}
	
	public String getCrtCzy() {
		return this.crtCzy;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
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
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	
	public String getBatchType() {
		return this.batchType;
	}

}
