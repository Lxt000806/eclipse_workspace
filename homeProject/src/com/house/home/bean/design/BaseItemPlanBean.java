package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * BaseItemPlan信息bean
 */
public class BaseItemPlanBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="fixAreaPk", order=3)
	private Integer fixAreaPk;
	@ExcelAnnotation(exportName="baseItemCode", order=4)
	private String baseItemCode;
	@ExcelAnnotation(exportName="qty", order=5)
	private Double qty;
	@ExcelAnnotation(exportName="cost", order=6)
	private Double cost;
	@ExcelAnnotation(exportName="unitPrice", order=7)
	private Double unitPrice;
	@ExcelAnnotation(exportName="lineAmount", order=8)
	private Double lineAmount;
	@ExcelAnnotation(exportName="remark", order=9)
	private String remark;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=10)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=11)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=12)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=13)
	private String actionLog;
	@ExcelAnnotation(exportName="dispSeq", order=14)
	private Integer dispSeq;
	@ExcelAnnotation(exportName="material", order=15)
	private Double material;
	@ExcelAnnotation(exportName="isCheck", order=16)
	private Integer isCheck;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setFixAreaPk(Integer fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}
	
	public Integer getFixAreaPk() {
		return this.fixAreaPk;
	}
	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}
	
	public String getBaseItemCode() {
		return this.baseItemCode;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Double getCost() {
		return this.cost;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Double getUnitPrice() {
		return this.unitPrice;
	}
	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}
	
	public Double getLineAmount() {
		return this.lineAmount;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
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
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setMaterial(Double material) {
		this.material = material;
	}
	
	public Double getMaterial() {
		return this.material;
	}
	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}
	
	public Integer getIsCheck() {
		return this.isCheck;
	}

}
