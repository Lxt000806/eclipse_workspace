package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemAppTempArea信息bean
 */
public class ItemAppTempAreaBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="iatno", order=2)
	private String iatno;
	@ExcelAnnotation(exportName="fromArea", order=3)
	private Integer fromArea;
	@ExcelAnnotation(exportName="toArea", order=4)
	private Integer toArea;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=6)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=7)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=8)
	private String actionLog;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setIatno(String iatno) {
		this.iatno = iatno;
	}
	
	public String getIatno() {
		return this.iatno;
	}
	public void setFromArea(Integer fromArea) {
		this.fromArea = fromArea;
	}
	
	public Integer getFromArea() {
		return this.fromArea;
	}
	public void setToArea(Integer toArea) {
		this.toArea = toArea;
	}
	
	public Integer getToArea() {
		return this.toArea;
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
