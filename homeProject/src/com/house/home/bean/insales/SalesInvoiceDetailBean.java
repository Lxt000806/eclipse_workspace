package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * SalesInvoiceDetail信息bean
 */
public class SalesInvoiceDetailBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="sino", order=2)
	private String sino;
	@ExcelAnnotation(exportName="itcode", order=3)
	private String itcode;
	@ExcelAnnotation(exportName="qty", order=4)
	private Double qty;
	@ExcelAnnotation(exportName="bcost", order=5)
	private Double bcost;
	@ExcelAnnotation(exportName="unitPrice", order=6)
	private Double unitPrice;
	@ExcelAnnotation(exportName="befLineAmount", order=7)
	private Double befLineAmount;
	@ExcelAnnotation(exportName="markup", order=8)
	private Double markup;
	@ExcelAnnotation(exportName="lineAmount", order=9)
	private Double lineAmount;
	@ExcelAnnotation(exportName="remarks", order=10)
	private String remarks;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=11)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=12)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=13)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=14)
	private String actionLog;
	@ExcelAnnotation(exportName="saleQty", order=15)
	private Double saleQty;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setSino(String sino) {
		this.sino = sino;
	}
	
	public String getSino() {
		return this.sino;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	
	public String getItcode() {
		return this.itcode;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
	}
	public void setBcost(Double bcost) {
		this.bcost = bcost;
	}
	
	public Double getBcost() {
		return this.bcost;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Double getUnitPrice() {
		return this.unitPrice;
	}
	public void setBefLineAmount(Double befLineAmount) {
		this.befLineAmount = befLineAmount;
	}
	
	public Double getBefLineAmount() {
		return this.befLineAmount;
	}
	public void setMarkup(Double markup) {
		this.markup = markup;
	}
	
	public Double getMarkup() {
		return this.markup;
	}
	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}
	
	public Double getLineAmount() {
		return this.lineAmount;
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
	public void setSaleQty(Double saleQty) {
		this.saleQty = saleQty;
	}
	
	public Double getSaleQty() {
		return this.saleQty;
	}

}
