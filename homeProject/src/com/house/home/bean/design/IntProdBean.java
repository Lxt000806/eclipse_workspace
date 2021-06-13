package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * IntProd信息bean
 */
public class IntProdBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="descr", order=2)
	private String descr;
	@ExcelAnnotation(exportName="fixAreaPk", order=3)
	private Integer fixAreaPk;
	@ExcelAnnotation(exportName="isCupboard", order=4)
	private String isCupboard;
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
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setFixAreaPk(Integer fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}
	
	public Integer getFixAreaPk() {
		return this.fixAreaPk;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	
	public String getIsCupboard() {
		return this.isCupboard;
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
