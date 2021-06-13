package com.house.home.bean.insales;

import com.house.framework.commons.excel.ExcelAnnotation;

public class PurchBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="采购单号", order=1)
	private String no;
	@ExcelAnnotation(exportName="采购单状态", order=2)
	private String statusdescr;
	@ExcelAnnotation(exportName="采购类型", order=3)
	private String typedescr;
	@ExcelAnnotation(exportName="采购日期", order=4)
	private String date;
	@ExcelAnnotation(exportName="到货日期", order=5)
	private String arrivedate;
	@ExcelAnnotation(exportName="仓库名称", order=6)
	private String whcodedescr;
	@ExcelAnnotation(exportName="备注", order=7)
	private String remarks;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatusdescr() {
		return statusdescr;
	}
	public void setStatusdescr(String statusdescr) {
		this.statusdescr = statusdescr;
	}
	public String getTypedescr() {
		return typedescr;
	}
	public void setTypedescr(String typedescr) {
		this.typedescr = typedescr;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getArrivedate() {
		return arrivedate;
	}
	public void setArrivedate(String arrivedate) {
		this.arrivedate = arrivedate;
	}
	public String getWhcodedescr() {
		return whcodedescr;
	}
	public void setWhcodedescr(String whcodedescr) {
		this.whcodedescr = whcodedescr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
