package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * WHPosiTran信息bean
 */
public class WHPosiTranBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="whppk", order=2)
	private Integer whppk;
	@ExcelAnnotation(exportName="itcode", order=3)
	private String itcode;
	@ExcelAnnotation(exportName="trsQty", order=4)
	private Double trsQty;
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
	public void setWhppk(Integer whppk) {
		this.whppk = whppk;
	}
	
	public Integer getWhppk() {
		return this.whppk;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	
	public String getItcode() {
		return this.itcode;
	}
	public void setTrsQty(Double trsQty) {
		this.trsQty = trsQty;
	}
	
	public Double getTrsQty() {
		return this.trsQty;
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
