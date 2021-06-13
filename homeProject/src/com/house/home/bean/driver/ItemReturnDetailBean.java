package com.house.home.bean.driver;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemReturnDetail信息bean
 */
public class ItemReturnDetailBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="no", order=2)
	private String no;
	@ExcelAnnotation(exportName="appDtpk", order=3)
	private Integer appDtpk;
	@ExcelAnnotation(exportName="itemCode", order=4)
	private String itemCode;
	@ExcelAnnotation(exportName="qty", order=5)
	private Double qty;
	@ExcelAnnotation(exportName="remarks", order=6)
	private String remarks;
	@ExcelAnnotation(exportName="arriveNo", order=7)
	private String arriveNo;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=9)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=10)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=11)
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setAppDtpk(Integer appDtpk) {
		this.appDtpk = appDtpk;
	}
	
	public Integer getAppDtpk() {
		return this.appDtpk;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemCode() {
		return this.itemCode;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setArriveNo(String arriveNo) {
		this.arriveNo = arriveNo;
	}
	
	public String getArriveNo() {
		return this.arriveNo;
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
