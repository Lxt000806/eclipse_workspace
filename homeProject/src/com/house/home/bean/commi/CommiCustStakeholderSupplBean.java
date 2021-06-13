package com.house.home.bean.commi;

import java.io.Serializable;
import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

public class CommiCustStakeholderSupplBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ExcelAnnotation(exportName = "销售日期", pattern = "yyyy-MM-dd", order = 1)
	private Date date;
	
	@ExcelAnnotation(exportName = "材料类型1", order = 2)
	private String itemType1;
	
	@ExcelAnnotation(exportName = "供应商编号", order = 3)
	private String supplCode;
	
	@ExcelAnnotation(exportName = "客户编号", order = 4)
	private String custCode;

    @ExcelAnnotation(exportName = "材料名称", order = 5)
    private String itemDescr;
	
    @ExcelAnnotation(exportName = "返利总金额", order = 6)
    private Double amount;
    
    @ExcelAnnotation(exportName = "干系人编号", order = 7)
    private String empCode;
    
    @ExcelAnnotation(exportName = "业务员提成", order = 8)
    private Double commiAmount;

	@ExcelAnnotation(exportName = "备注", order = 9)
	private String remarks;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getItemType1() {
        return itemType1;
    }

    public void setItemType1(String itemType1) {
        this.itemType1 = itemType1;
    }

    public String getSupplCode() {
        return supplCode;
    }

    public void setSupplCode(String supplCode) {
        this.supplCode = supplCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getItemDescr() {
        return itemDescr;
    }

    public void setItemDescr(String itemDescr) {
        this.itemDescr = itemDescr;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Double getCommiAmount() {
        return commiAmount;
    }

    public void setCommiAmount(Double commiAmount) {
        this.commiAmount = commiAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
