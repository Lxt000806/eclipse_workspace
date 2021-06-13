package com.house.home.bean.query;

import java.io.Serializable;

import com.house.framework.commons.excel.ExcelAnnotation;

public class CustCheckAnalysisBean_GrouyByCustType implements Serializable {
	private static final long serialVersionUID = 1L;
	@ExcelAnnotation(exportName="客户类型", order=1)
	private String custtypedescr;
	@ExcelAnnotation(exportName="套数", order=2)
	private Double count;
	@ExcelAnnotation(exportName="结算金额", order=3)
	private Double checkAmount;
	
	public String getCusttypedescr() {
		return custtypedescr;
	}
	public void setCusttypedescr(String custtypedescr) {
		this.custtypedescr = custtypedescr;
	}
	public Double getCount() {
		return count;
	}
	public void setCount(Double count) {
		this.count = count;
	}
	public Double getCheckAmount() {
		return checkAmount;
	}
	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}
	
	
}
