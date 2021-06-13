package com.house.home.bean.insales;

import java.io.Serializable;
import java.util.Date;

import com.house.framework.commons.excel.ExcelAnnotation;

public class ItemWHBalBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ExcelAnnotation(exportName="材料编号",order=1)
	private String itCode;
	@ExcelAnnotation(exportName="材料类型2", order=2)
	private String itemType2Descr;
    	@ExcelAnnotation(exportName="材料名称",order=3)
	private String descr;
	@ExcelAnnotation(exportName="数量", order=4)
	private Double qtyCal;
	@ExcelAnnotation(exportName="单价", order=5)
	private Double avgCost;
    	@ExcelAnnotation(exportName="库存成本金额", order=6)
	private Double costAmount;
	@ExcelAnnotation(exportName="最后更新时间",pattern="yyyy-MM-dd HH:mm:ss",order=7)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="最后更新人员",order=8)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="是否过期",order=9)
	private String expired;
	@ExcelAnnotation(exportName="操作", order=10)
	private String actionLog;
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	public String getItemType2Descr() {
		return itemType2Descr;
	}
	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Double getQtyCal() {
		return qtyCal;
	}
	public void setQtyCal(Double qtyCal) {
		this.qtyCal = qtyCal;
	}
	public Double getAvgCost() {
		return avgCost;
	}
	public void setAvgCost(Double avgCost) {
		this.avgCost = avgCost;
	}
	public Double getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
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
