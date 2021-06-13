package com.house.home.bean.query;

import java.io.Serializable;

import com.house.framework.commons.excel.ExcelAnnotation;

public class CustCheckAnalysisBean_GroupByDept2 implements Serializable {
	private static final long serialVersionUID = 1L;
	@ExcelAnnotation(exportName="一级部门", order=1)
	private String depart1Descr;
	@ExcelAnnotation(exportName="二级部门", order=2)
	private String depart2Descr;
	@ExcelAnnotation(exportName="套数", order=3)
	private Double count;
	@ExcelAnnotation(exportName="结算金额", order=4)
	private Double checkAmount;
	public String getDepart1Descr() {
		return depart1Descr;
	}
	public void setDepart1Descr(String depart1Descr) {
		this.depart1Descr = depart1Descr;
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
	public String getDepart2Descr() {
		return depart2Descr;
	}
	public void setDepart2Descr(String depart2Descr) {
		this.depart2Descr = depart2Descr;
	}
	
	
}
