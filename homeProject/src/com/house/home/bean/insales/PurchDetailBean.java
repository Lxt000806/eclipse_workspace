package com.house.home.bean.insales;

import com.house.framework.commons.excel.ExcelAnnotation;

public class PurchDetailBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@ExcelAnnotation(exportName="材料编号", order=1)
	private String itcode;
	@ExcelAnnotation(exportName="材料名称", order=2)
	private String itdescr;
	@ExcelAnnotation(exportName="品牌", order=3)
	private String sqlCodeDescr;
	@ExcelAnnotation(exportName="颜色", order=4)
	private String Color;
	@ExcelAnnotation(exportName="当前库存", order=5)
	private String allqty;
	@ExcelAnnotation(exportName="退回数量", order=6)
	private String qtyCal;
	@ExcelAnnotation(exportName="已到货", order=7)
	private String arrivqty;
	@ExcelAnnotation(exportName="单位", order=8)
	private String uniDescr;
	@ExcelAnnotation(exportName="备注", order=9)
	private String remarks;
	
	public String getItcode() {
		return itcode;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	public String getItdescr() {
		return itdescr;
	}
	public void setItdescr(String itdescr) {
		this.itdescr = itdescr;
	}
	public String getSqlCodeDescr() {
		return sqlCodeDescr;
	}
	public void setSqlCodeDescr(String sqlCodeDescr) {
		this.sqlCodeDescr = sqlCodeDescr;
	}
	
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
	public String getAllqty() {
		return allqty;
	}
	public void setAllqty(String allqty) {
		this.allqty = allqty;
	}
	public String getQtyCal() {
		return qtyCal;
	}
	public void setQtyCal(String qtyCal) {
		this.qtyCal = qtyCal;
	}
	public String getArrivqty() {
		return arrivqty;
	}
	public void setArrivqty(String arrivqty) {
		this.arrivqty = arrivqty;
	}
	public String getUniDescr() {
		return uniDescr;
	}
	public void setUniDescr(String uniDescr) {
		this.uniDescr = uniDescr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
