package com.house.home.bean.supplier;

import java.util.Date;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemAppDetail信息bean
 */
public class ItemAppDetailBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="审核日期", order=1)
	private Date confirmDate;
	@ExcelAnnotation(exportName="领料单号", order=2)
	private String no;
	@ExcelAnnotation(exportName="往来单位编号", order=3)
	private String relNO;
	@ExcelAnnotation(exportName="材料编号", order=4)
	private String itemCode;
	@ExcelAnnotation(exportName="材料名称", order=5)
	private String itemDescr;
	@ExcelAnnotation(exportName="数量", order=6)
	private Double qty;
	@ExcelAnnotation(exportName="单位", order=7)
	private String uomDescr; 
	@ExcelAnnotation(exportName="其他费用", order=8)
	private Double processCost;
	@ExcelAnnotation(exportName="仓库名称", order=8)
	private String whCodeDescr;
	@ExcelAnnotation(exportName="备注", order=9)
	private String remarks;
	@ExcelAnnotation(exportName="项目经理", order=10)
	private String projectMan;
	@ExcelAnnotation(exportName="电话", order=11)
	private String phone;
	@ExcelAnnotation(exportName="楼盘", order=12)
	private String address;
	@ExcelAnnotation(exportName="编号", order=13)
	private String documentNo;
	
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getRelNO() {
		return relNO;
	}
	public void setRelNO(String relNO) {
		this.relNO = relNO;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getUomDescr() {
		return uomDescr;
	}
	public void setUomDescr(String uomDescr) {
		this.uomDescr = uomDescr;
	}
	public Double getProcessCost() {
		return processCost;
	}
	public void setProcessCost(Double processCost) {
		this.processCost = processCost;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getWhCodeDescr() {
		return whCodeDescr;
	}
	public void setWhCodeDescr(String whCodeDescr) {
		this.whCodeDescr = whCodeDescr;
	}
}
