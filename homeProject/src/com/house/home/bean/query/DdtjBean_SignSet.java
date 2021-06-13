package com.house.home.bean.query;

import java.io.Serializable;
import java.util.Date;

import com.house.framework.commons.excel.ExcelAnnotation;

public class DdtjBean_SignSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="楼盘", order=1)
	private String address;
	@ExcelAnnotation(exportName="设计师", order=2)
	private String designManDescr;
	@ExcelAnnotation(exportName="业务员", order=3)
	private String businessManDescr;
	@ExcelAnnotation(exportName="下定时间",pattern="yyyy-MM-dd", order=4)
	private Date setDate;
	@ExcelAnnotation(exportName="签单时间",pattern="yyyy-MM-dd", order=5)
	private Date signDate;
	@ExcelAnnotation(exportName="结转时间",pattern="yyyy-MM-dd", order=6)
	private Date endDate;
	@ExcelAnnotation(exportName="客户状态", order=7)
	private String statusDescr;
	@ExcelAnnotation(exportName="结束原因", order=8)
	private String endCodeDescr;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public Date getSetDate() {
		return setDate;
	}
	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getEndCodeDescr() {
		return endCodeDescr;
	}
	public void setEndCodeDescr(String endCodeDescr) {
		this.endCodeDescr = endCodeDescr;
	}
	
}
