package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * PurchaseDetail信息bean
 */
public class PurchaseDetailBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="puno", order=2)
	private String puno;
	@ExcelAnnotation(exportName="itcode", order=3)
	private String itcode;
	@ExcelAnnotation(exportName="qtyCal", order=4)
	private Double qtyCal;
	@ExcelAnnotation(exportName="unitPrice", order=5)
	private Double unitPrice;
	@ExcelAnnotation(exportName="amount", order=6)
	private Double amount;
	@ExcelAnnotation(exportName="remarks", order=7)
	private String remarks;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=9)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=10)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=11)
	private String actionLog;
	@ExcelAnnotation(exportName="arrivQty", order=12)
	private Double arrivQty;
	@ExcelAnnotation(exportName="projectCost", order=13)
	private Double projectCost;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setPuno(String puno) {
		this.puno = puno;
	}
	
	public String getPuno() {
		return this.puno;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	
	public String getItcode() {
		return this.itcode;
	}
	public void setQtyCal(Double qtyCal) {
		this.qtyCal = qtyCal;
	}
	
	public Double getQtyCal() {
		return this.qtyCal;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Double getUnitPrice() {
		return this.unitPrice;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
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
	public void setArrivQty(Double arrivQty) {
		this.arrivQty = arrivQty;
	}
	
	public Double getArrivQty() {
		return this.arrivQty;
	}
	public void setProjectCost(Double projectCost) {
		this.projectCost = projectCost;
	}
	
	public Double getProjectCost() {
		return this.projectCost;
	}

}
