package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * WareHouse信息bean
 */
public class WareHouseBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="仓库编号", order=1)
	private String code;
	@ExcelAnnotation(exportName="仓库名称", order=2)
	private String desc1;
    	@ExcelAnnotation(exportName="最后更新时间", pattern="yyyy-MM-dd HH:mm:ss", order=3)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="最后更新人员", order=4)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="是否过期", order=5)
	private String expired;
	@ExcelAnnotation(exportName="操作", order=6)
	private String actionLog;

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	
	public String getDesc1() {
		return this.desc1;
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
