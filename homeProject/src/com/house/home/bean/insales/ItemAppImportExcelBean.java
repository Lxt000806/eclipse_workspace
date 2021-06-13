package com.house.home.bean.insales;

import com.house.framework.commons.excel.ExcelAnnotation;
/**
 * 领料管理导入Excel
 * @author zzr
 *
 */
public class ItemAppImportExcelBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="材料编号", order=1)
	private String itemCode;
	@ExcelAnnotation(exportName="名称", order=2)
	private String descr;
	@ExcelAnnotation(exportName="采购数量", order=3)
	private Double qty;
	@ExcelAnnotation(exportName="备注", order=4)
	private String remarks;
	@ExcelAnnotation(exportName="成本", order=5)
	private Double cost;
	@ExcelAnnotation(exportName="项目经理结算价", order=6)
	private Double projectcost;
	@ExcelAnnotation(exportName="单位", order=7)
	private String uomdescr;
	@ExcelAnnotation(exportName="材料类型1", order=8)
	private String itemtype1descr;
	@ExcelAnnotation(exportName="材料类型2", order=9)
	private String itemtype2descr;
	@ExcelAnnotation(exportName="材料类型3", order=10)
	private String itemtype3descr;
	@ExcelAnnotation(exportName="品牌", order=11)
	private String sqldescr;
	@ExcelAnnotation(exportName="发货类型", order=12)
	private String sendtypedescr;
	@ExcelAnnotation(exportName="颜色", order=13)
	private String color;
	@ExcelAnnotation(exportName="型号", order=14)
	private String model;
	@ExcelAnnotation(exportName="规格", order=15)
	private String sizedesc;
	@ExcelAnnotation(exportName="条码", order=16)
	private String barcode;
	@ExcelAnnotation(exportName="是否固定价", order=17)
	private String isfixpricedescr;
	@ExcelAnnotation(exportName="单价", order=18)
	private Double price;
	@ExcelAnnotation(exportName="错误信息", order=19)
	private String error;
	@ExcelAnnotation(exportName="成品名称", order=20)
	private String productName;
	@ExcelAnnotation(exportName="项目", order=21)
	private String item;
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getProjectcost() {
		return projectcost;
	}
	public void setProjectcost(Double projectcost) {
		this.projectcost = projectcost;
	}
	public String getUomdescr() {
		return uomdescr;
	}
	public void setUomdescr(String uomdescr) {
		this.uomdescr = uomdescr;
	}
	public String getItemtype1descr() {
		return itemtype1descr;
	}
	public void setItemtype1descr(String itemtype1descr) {
		this.itemtype1descr = itemtype1descr;
	}
	public String getItemtype2descr() {
		return itemtype2descr;
	}
	public void setItemtype2descr(String itemtype2descr) {
		this.itemtype2descr = itemtype2descr;
	}
	public String getItemtype3descr() {
		return itemtype3descr;
	}
	public void setItemtype3descr(String itemtype3descr) {
		this.itemtype3descr = itemtype3descr;
	}
	public String getSqldescr() {
		return sqldescr;
	}
	public void setSqldescr(String sqldescr) {
		this.sqldescr = sqldescr;
	}
	public String getSendtypedescr() {
		return sendtypedescr;
	}
	public void setSendtypedescr(String sendtypedescr) {
		this.sendtypedescr = sendtypedescr;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSizedesc() {
		return sizedesc;
	}
	public void setSizedesc(String sizedesc) {
		this.sizedesc = sizedesc;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getIsfixpricedescr() {
		return isfixpricedescr;
	}
	public void setIsfixpricedescr(String isfixpricedescr) {
		this.isfixpricedescr = isfixpricedescr;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
}
