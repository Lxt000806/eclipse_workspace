package com.house.home.bean.insales;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * 供应商促销导入
 * 
 * @author created by zb
 * @date 2019-7-15--下午2:39:56
 */
public class SupplPromBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@ExcelAnnotation(exportName = "品牌", order = 1)
	private String itemtype3;
	@ExcelAnnotation(exportName = "品名", order = 2)
	private String itemdescr;
	@ExcelAnnotation(exportName = "型号", order = 3)
	private String model;
	@ExcelAnnotation(exportName = "单位", order = 4)
	private String uom;
	@ExcelAnnotation(exportName = "规格", order = 5)
	private String itemsize;
	@ExcelAnnotation(exportName = "有家直供价", order = 6)
	private Double unitprice;
	@ExcelAnnotation(exportName = "活动价", order = 7)
	private Double promprice;
	@ExcelAnnotation(exportName = "常规成本", order = 8)
	private Double cost;
	@ExcelAnnotation(exportName = "活动成本", order = 9)
	private Double promcost;
	@ExcelAnnotation(exportName = "备注", order = 10)
	private String remarks;
	@ExcelAnnotation(exportName = "错误信息", order = 11)
	private String error;

	public String getItemtype3() {
		return itemtype3;
	}

	public void setItemtype3(String itemtype3) {
		this.itemtype3 = itemtype3;
	}

	public String getItemdescr() {
		return itemdescr;
	}

	public void setItemdescr(String itemdescr) {
		this.itemdescr = itemdescr;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getItemsize() {
		return itemsize;
	}

	public void setItemsize(String itemsize) {
		this.itemsize = itemsize;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Double getPromprice() {
		return promprice;
	}

	public void setPromprice(Double promprice) {
		this.promprice = promprice;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getPromcost() {
		return promcost;
	}

	public void setPromcost(Double promcost) {
		this.promcost = promcost;
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
