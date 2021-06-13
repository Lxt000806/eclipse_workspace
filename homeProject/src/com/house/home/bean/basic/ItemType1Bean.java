package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemType1信息bean
 */
public class ItemType1Bean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="code", order=1)
	private String code;
	@ExcelAnnotation(exportName="descr", order=2)
	private String descr;
	@ExcelAnnotation(exportName="dispSeq", order=3)
	private Integer dispSeq;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=5)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=6)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=7)
	private String actionLog;

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
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
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
