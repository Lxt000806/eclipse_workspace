package com.house.home.bean.finance;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * WHCheckOut信息bean
 */

public class WHCheckOutBean_Detail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="记账单号", order=1)
	private String wcoNo;
	@ExcelAnnotation(exportName="账单状态", order=2)
	private String statusDescr;
	@ExcelAnnotation(exportName="凭证号", order=3)
	private String documentNo;
	@ExcelAnnotation(exportName="记账日期", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date checkDate;
	@ExcelAnnotation(exportName="备注", order=5)
	private String remarks;
	@ExcelAnnotation(exportName="仓库", order=6)
	private String whDescr;
	@ExcelAnnotation(exportName="开单人", order=7)
	private String appczyDescr;
	@ExcelAnnotation(exportName="申请日期", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date date;
	@ExcelAnnotation(exportName="审核人员", order=9)
	private String confirmczyDescr;
	@ExcelAnnotation(exportName="审核日期", pattern="yyyy-MM-dd HH:mm:ss", order=10)
	private Date confirmDate;
	@ExcelAnnotation(exportName="发货单号", order=11)
	private String no;
	@ExcelAnnotation(exportName="领料单号", order=11)
	private String iano;
	@ExcelAnnotation(exportName="材料类型1", order=14)
	private String itemtype1descr;
	@ExcelAnnotation(exportName="材料类型2", order=14)
	private String itemtype2descr;
	@ExcelAnnotation(exportName="总金额", order=28)
	private Double sumcost;
	@ExcelAnnotation(exportName="项目经理结算价", order=28)
	private Double projectamount;
	@ExcelAnnotation(exportName="档案号", order=14)
	private String custdocumentno;
	@ExcelAnnotation(exportName="楼盘", order=14)
	private String address;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getWhDescr() {
		return whDescr;
	}
	public void setWhDescr(String whDescr) {
		this.whDescr = whDescr;
	}
	public String getAppczyDescr() {
		return appczyDescr;
	}
	public void setAppczyDescr(String appczyDescr) {
		this.appczyDescr = appczyDescr;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getConfirmczyDescr() {
		return confirmczyDescr;
	}
	public void setConfirmczyDescr(String confirmczyDescr) {
		this.confirmczyDescr = confirmczyDescr;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getIano() {
		return iano;
	}
	public void setIano(String iano) {
		this.iano = iano;
	}
	public String getWcoNo() {
		return wcoNo;
	}
	public void setWcoNo(String wcoNo) {
		this.wcoNo = wcoNo;
	}
	public String getItemtype1descr() {
		return itemtype1descr;
	}
	public void setItemtype1descr(String itemtype1descr) {
		this.itemtype1descr = itemtype1descr;
	}
	public String getItemtype2descr() {
		return itemtype2descr;
	}
	public void setItemtype2descr(String itemtype2descr) {
		this.itemtype2descr = itemtype2descr;
	}
	public Double getSumcost() {
		return sumcost;
	}
	public void setSumcost(Double sumcost) {
		this.sumcost = sumcost;
	}
	public Double getProjectamount() {
		return projectamount;
	}
	public void setProjectamount(Double projectamount) {
		this.projectamount = projectamount;
	}
	public String getCustdocumentno() {
		return custdocumentno;
	}
	public void setCustdocumentno(String custdocumentno) {
		this.custdocumentno = custdocumentno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
		
    
}
