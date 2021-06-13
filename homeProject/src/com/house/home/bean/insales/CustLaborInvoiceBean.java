package com.house.home.bean.insales;

import java.util.Date;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * 劳务分包开票
 */
public class CustLaborInvoiceBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="客户编号", order=1)
	private String custcode;
	@ExcelAnnotation(exportName="劳务开票日期", order=2)
	private Date date;
	@ExcelAnnotation(exportName="劳务开票金额", order=3)
	private Double amount;
	
    @ExcelAnnotation(exportName = "劳务分包公司", order = 4)
    private String laborcompny;
	
	@ExcelAnnotation(exportName="错误信息", order=5)
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
    public String getLaborcompny() {
        return laborcompny;
    }
    public void setLaborcompny(String laborcompny) {
        this.laborcompny = laborcompny;
    }
}
