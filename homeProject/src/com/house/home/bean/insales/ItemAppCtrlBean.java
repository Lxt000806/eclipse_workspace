package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemAppCtrl信息bean
 */
public class ItemAppCtrlBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="custType", order=1)
	private String custType;
	@ExcelAnnotation(exportName="itemType1", order=2)
	private String itemType1;
	@ExcelAnnotation(exportName="canInPlan", order=3)
	private String canInPlan;
	@ExcelAnnotation(exportName="canOutPlan", order=4)
	private String canOutPlan;

	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setCanInPlan(String canInPlan) {
		this.canInPlan = canInPlan;
	}
	
	public String getCanInPlan() {
		return this.canInPlan;
	}
	public void setCanOutPlan(String canOutPlan) {
		this.canOutPlan = canOutPlan;
	}
	
	public String getCanOutPlan() {
		return this.canOutPlan;
	}

}
