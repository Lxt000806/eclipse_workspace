package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CustCon信息bean
 */
public class CustConBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@ExcelAnnotation(exportName="客户编号",order=1)
	private String custCode;
	@ExcelAnnotation(exportName="客户名称", order=2)
	private String descr;
    	@ExcelAnnotation(exportName="楼盘",order=3)
	private String custAddress;
	@ExcelAnnotation(exportName="客户状态", order=4)
	private String statusDescr;
	@ExcelAnnotation(exportName="联系时间",pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date conDate;
    	@ExcelAnnotation(exportName="联系人", order=6)
	private String conManDescr;
	@ExcelAnnotation(exportName="设计师", order=7)
	private String designManDescr;
	@ExcelAnnotation(exportName="业务员", order=8)
	private String businessManDescr;
	@ExcelAnnotation(exportName="联系内容",order=9)
	private String remarks;
	@ExcelAnnotation(exportName="最后修改时间",pattern="yyyy-MM-dd HH:mm:ss",order=10)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="修改人",order=11)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="是否过期",order=12)
	private String expired;
	@ExcelAnnotation(exportName="操作", order=13)
	private String actionLog;
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public Date getConDate() {
		return conDate;
	}
	public void setConDate(Date conDate) {
		this.conDate = conDate;
	}
	public String getConManDescr() {
		return conManDescr;
	}
	public void setConManDescr(String conManDescr) {
		this.conManDescr = conManDescr;
	}
	public String getDesignManDescr() {
		return designManDescr;
	}
	public void setDesignManDescr(String designManDescr) {
		this.designManDescr = designManDescr;
	}
	public String getBusinessManDescr() {
		return businessManDescr;
	}
	public void setBusinessManDescr(String businessManDescr) {
		this.businessManDescr = businessManDescr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}

}
