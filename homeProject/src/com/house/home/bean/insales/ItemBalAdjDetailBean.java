package com.house.home.bean.insales;

import com.house.framework.commons.excel.ExcelAnnotation;

public class ItemBalAdjDetailBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="PK", order=1)
	private String pk;
	@ExcelAnnotation(exportName="仓库调整编号", order=2)
	private String IBDNo;
	@ExcelAnnotation(exportName="材料编号", order=3)
	private String itCode;
	@ExcelAnnotation(exportName="调整数量", order=4)
	private String adjQty;	
	@ExcelAnnotation(exportName="变动后数量", order=5)
	private String qty;
	@ExcelAnnotation(exportName="备注", order=6)
	private String remarks;
	@ExcelAnnotation(exportName="最后修改时间", order=7)
	private String lastUpdate;
	@ExcelAnnotation(exportName="最后修改人", order=8)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="操作日志", order=9)
	private String actionLog;
	@ExcelAnnotation(exportName="调整金额", order=10)
	private String cost;
	@ExcelAnnotation(exportName="调整后金额", order=11)
	private String aftCost;
	@ExcelAnnotation(exportName="成本单价", order=12)
	private String adjCost;
	@ExcelAnnotation(exportName="材料名称",order=13)
	private String itemDescr;
	@ExcelAnnotation(exportName="单位",order=14)
	private String uomDescr;
	
	
	
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public String getUomDescr() {
		return uomDescr;
	}
	public void setUomDescr(String uomDescr) {
		this.uomDescr = uomDescr;
	}
	public String getAdjCost() {
		return adjCost;
	}
	public void setAdjCost(String adjCost) {
		this.adjCost = adjCost;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getIBDNo() {
		return IBDNo;
	}
	public void setIBDNo(String iBDNo) {
		IBDNo = iBDNo;
	}
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	public String getAdjQty() {
		return adjQty;
	}
	public void setAdjQty(String adjQty) {
		this.adjQty = adjQty;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getAftCost() {
		return aftCost;
	}
	public void setAftCost(String aftCost) {
		this.aftCost = aftCost;
	}




}
