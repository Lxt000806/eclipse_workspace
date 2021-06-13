package com.house.home.bean.driver;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemReturnArrive信息bean
 */
public class ItemReturnArriveBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="no", order=1)
	private String no;
	@ExcelAnnotation(exportName="returnNo", order=2)
	private String returnNo;
	@ExcelAnnotation(exportName="driverCode", order=3)
	private String driverCode;
	@ExcelAnnotation(exportName="address", order=4)
	private String address;
	@ExcelAnnotation(exportName="driverRemark", order=5)
	private String driverRemark;
    	@ExcelAnnotation(exportName="arriveDate", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date arriveDate;
	@ExcelAnnotation(exportName="actionLog", order=7)
	private String actionLog;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=9)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=10)
	private String expired;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}
	
	public String getReturnNo() {
		return this.returnNo;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	
	public String getDriverCode() {
		return this.driverCode;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setDriverRemark(String driverRemark) {
		this.driverRemark = driverRemark;
	}
	
	public String getDriverRemark() {
		return this.driverRemark;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	
	public Date getArriveDate() {
		return this.arriveDate;
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

}
