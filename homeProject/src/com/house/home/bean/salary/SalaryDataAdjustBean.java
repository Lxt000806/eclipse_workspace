package com.house.home.bean.salary;

import com.house.framework.commons.excel.ExcelAnnotation;

public class SalaryDataAdjustBean  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="身份证号", order=1)
	private String idNum;
	@ExcelAnnotation(exportName="金额", order=2)
	private Double adjustValue;
	@ExcelAnnotation(exportName="备注", order=3)
	private String remarks;

	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public Double getAdjustValue() {
		return adjustValue;
	}
	public void setAdjustValue(Double adjustValue) {
		this.adjustValue = adjustValue;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
