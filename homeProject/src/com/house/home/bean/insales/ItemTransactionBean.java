package com.house.home.bean.insales;

import java.io.Serializable;
import java.util.Date;

import com.house.framework.commons.excel.ExcelAnnotation;

public class ItemTransactionBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="流水号", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="产品编号", order=2)
	private String itCode;
	@ExcelAnnotation(exportName="产品名称", order=3)
	private String descr;
	@ExcelAnnotation(exportName="出库|数量", order=4)
	private Double ckQty;
	@ExcelAnnotation(exportName="出库|成本", order=5)
	private Double ckCost;
	@ExcelAnnotation(exportName="出库|金额", order=6)
	private Double ckAmount;
	@ExcelAnnotation(exportName="入库|数量", order=7)
	private Double lkQty;
	@ExcelAnnotation(exportName="入库|成本", order=8)
	private Double lkCost;
	@ExcelAnnotation(exportName="入库|金额", order=9)
	private Double lkAmount;
	@ExcelAnnotation(exportName="变动后|数量", order=10)
	private Double aftAllQty;
	@ExcelAnnotation(exportName="变动后|成本", order=11)
	private Double aftCost;
	@ExcelAnnotation(exportName="变动日期", pattern="yyyy-MM-dd HH:mm:ss", order=12)
	private Date date;
	@ExcelAnnotation(exportName="单据类型", order=13)
	private String prefixDesc;
	@ExcelAnnotation(exportName="档案号", order=14)
	private String document;
	@ExcelAnnotation(exportName="参考档案号", order=15)
	private String refDocument;
	@ExcelAnnotation(exportName="备注", order=16)
	private String remarks;
    	@ExcelAnnotation(exportName="最后更新时间", pattern="yyyy-MM-dd HH:mm:ss", order=17)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="最后更新人员", order=18)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="是否过期", order=19)
	private String expired;
	@ExcelAnnotation(exportName="操作", order=20)
	private String actionLog;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Double getCkQty() {
		return ckQty;
	}
	public void setCkQty(Double ckQty) {
		this.ckQty = ckQty;
	}
	public Double getCkCost() {
		return ckCost;
	}
	public void setCkCost(Double ckCost) {
		this.ckCost = ckCost;
	}
	public Double getCkAmount() {
		return ckAmount;
	}
	public void setCkAmount(Double ckAmount) {
		this.ckAmount = ckAmount;
	}
	public Double getLkQty() {
		return lkQty;
	}
	public void setLkQty(Double lkQty) {
		this.lkQty = lkQty;
	}
	public Double getLkCost() {
		return lkCost;
	}
	public void setLkCost(Double lkCost) {
		this.lkCost = lkCost;
	}
	public Double getLkAmount() {
		return lkAmount;
	}
	public void setLkAmount(Double lkAmount) {
		this.lkAmount = lkAmount;
	}
	public Double getAftAllQty() {
		return aftAllQty;
	}
	public void setAftAllQty(Double aftAllQty) {
		this.aftAllQty = aftAllQty;
	}
	public Double getAftCost() {
		return aftCost;
	}
	public void setAftCost(Double aftCost) {
		this.aftCost = aftCost;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPrefixDesc() {
		return prefixDesc;
	}
	public void setPrefixDesc(String prefixDesc) {
		this.prefixDesc = prefixDesc;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getRefDocument() {
		return refDocument;
	}
	public void setRefDocument(String refDocument) {
		this.refDocument = refDocument;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
}
