package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * SetMainItemPriceBean信息bean
 */
public class SetMainItemPriceBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="客户类型", order=2)
	private String custType;
	@ExcelAnnotation(exportName="起始面积", order=3)
	private Double fromArea;
	@ExcelAnnotation(exportName="截止面积", order=4)
	private Double toArea;
	@ExcelAnnotation(exportName="基准单价", order=5)
	private Double basePrice;
	@ExcelAnnotation(exportName="基准面积", order=6)
	private Double baseArea;
	@ExcelAnnotation(exportName="每平米单价", order=7)
	private Double unitPrice;
	@ExcelAnnotation(exportName="基准卫生间数", order=8)
	private Double baseToiletCount;
	@ExcelAnnotation(exportName="卫生间单价", order=9)
	private Double toiletPrice;
    @ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=10)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=11)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=12)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=13)
	private String actionLog;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Double getFromArea() {
		return fromArea;
	}
	public void setFromArea(Double fromArea) {
		this.fromArea = fromArea;
	}
	public Double getToArea() {
		return toArea;
	}
	public void setToArea(Double toArea) {
		this.toArea = toArea;
	}
	public Double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
	public Double getBaseArea() {
		return baseArea;
	}
	public void setBaseArea(Double baseArea) {
		this.baseArea = baseArea;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getBaseToiletCount() {
		return baseToiletCount;
	}
	public void setBaseToiletCount(Double baseToiletCount) {
		this.baseToiletCount = baseToiletCount;
	}
	public Double getToiletPrice() {
		return toiletPrice;
	}
	public void setToiletPrice(Double toiletPrice) {
		this.toiletPrice = toiletPrice;
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
