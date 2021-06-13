package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemAppDetail信息bean
 */
public class ItemAppDetailBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="PK", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="批次号", order=2)
	private String no;
	@ExcelAnnotation(exportName="领料需求标识", order=3)
	private Integer reqPk;
	@ExcelAnnotation(exportName="区域编码", order=4)
	private Integer fixAreaPk;
	@ExcelAnnotation(exportName="集成成品PK", order=5)
	private Integer intProdPk;
	@ExcelAnnotation(exportName="材料编号", order=6)
	private String itemCode;
	@ExcelAnnotation(exportName="数量", order=7)
	private Double qty;
	@ExcelAnnotation(exportName="Remarks", order=8)
	private String remarks;
    	@ExcelAnnotation(exportName="LastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=9)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="LastUpdatedBy", order=10)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="Expired", order=11)
	private String expired;
	@ExcelAnnotation(exportName="ActionLog", order=12)
	private String actionLog;
	@ExcelAnnotation(exportName="移动平均成本", order=13)
	private Double cost;
	@ExcelAnnotation(exportName="变动后数量", order=14)
	private Double aftQty;
	@ExcelAnnotation(exportName="变动后平均成本", order=15)
	private Double aftCost;
	@ExcelAnnotation(exportName="发货数量", order=16)
	private Double sendQty;
	@ExcelAnnotation(exportName="项目经理结算价", order=17)
	private Double projectCost;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setReqPk(Integer reqPk) {
		this.reqPk = reqPk;
	}
	
	public Integer getReqPk() {
		return this.reqPk;
	}
	public void setFixAreaPk(Integer fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}
	
	public Integer getFixAreaPk() {
		return this.fixAreaPk;
	}
	public void setIntProdPk(Integer intProdPk) {
		this.intProdPk = intProdPk;
	}
	
	public Integer getIntProdPk() {
		return this.intProdPk;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemCode() {
		return this.itemCode;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Double getCost() {
		return this.cost;
	}
	public void setAftQty(Double aftQty) {
		this.aftQty = aftQty;
	}
	
	public Double getAftQty() {
		return this.aftQty;
	}
	public void setAftCost(Double aftCost) {
		this.aftCost = aftCost;
	}
	
	public Double getAftCost() {
		return this.aftCost;
	}
	public void setSendQty(Double sendQty) {
		this.sendQty = sendQty;
	}
	
	public Double getSendQty() {
		return this.sendQty;
	}
	public void setProjectCost(Double projectCost) {
		this.projectCost = projectCost;
	}
	
	public Double getProjectCost() {
		return this.projectCost;
	}

}
