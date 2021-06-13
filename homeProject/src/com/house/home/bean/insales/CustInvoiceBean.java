package com.house.home.bean.insales;

import java.util.Date;

import com.house.framework.commons.excel.ExcelAnnotation;
/**
 * 开票明细导入
 * @author created by zb
 * @date   2019-12-7--下午4:15:39
 */
public class CustInvoiceBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="客户编号", order=1)
	private String custcode;
	@ExcelAnnotation(exportName="开票日期", order=2)
	private Date date;
	@ExcelAnnotation(exportName="发票号", order=3)
	private String invoiceno;
	@ExcelAnnotation(exportName="发票代码", order=4)
	private String invoicecode;
	@ExcelAnnotation(exportName="应税服务名称", order=5)
	private String taxservice;
	@ExcelAnnotation(exportName="购买方", order=6)
	private String buyer;
	@ExcelAnnotation(exportName="开票金额", order=7)
	private Double amount;
	@ExcelAnnotation(exportName="税率", order=8)
	private Double taxper;
	@ExcelAnnotation(exportName="不含税金额", order=9)
	private Double notaxamount;
	@ExcelAnnotation(exportName="税额", order=10)
	private Double taxamount;
	@ExcelAnnotation(exportName="备注", order=11)
	private String remarks;
	@ExcelAnnotation(exportName="错误信息", order=19)
	private String error;
	
	public String getCustcode() {
		return custcode;
	}
	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getInvoiceno() {
		return invoiceno;
	}
	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	public String getInvoicecode() {
		return invoicecode;
	}
	public void setInvoicecode(String invoicecode) {
		this.invoicecode = invoicecode;
	}
	public String getTaxservice() {
		return taxservice;
	}
	public void setTaxservice(String taxservice) {
		this.taxservice = taxservice;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getTaxper() {
		return taxper;
	}
	public void setTaxper(Double taxper) {
		this.taxper = taxper;
	}
	public Double getNotaxamount() {
		return notaxamount;
	}
	public void setNotaxamount(Double notaxamount) {
		this.notaxamount = notaxamount;
	}
	public Double getTaxamount() {
		return taxamount;
	}
	public void setTaxamount(Double taxamount) {
		this.taxamount = taxamount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
