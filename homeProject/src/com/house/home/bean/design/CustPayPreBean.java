package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CustPayPre信息bean
 */
public class CustPayPreBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="payType", order=3)
	private String payType;
	@ExcelAnnotation(exportName="basePer", order=4)
	private Double basePer;
	@ExcelAnnotation(exportName="itemPer", order=5)
	private Double itemPer;
	@ExcelAnnotation(exportName="designFee", order=6)
	private Double designFee;
	@ExcelAnnotation(exportName="prePay", order=7)
	private Double prePay;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=9)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=10)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=11)
	private String actionLog;
	@ExcelAnnotation(exportName="basePay", order=12)
	private Double basePay;
	@ExcelAnnotation(exportName="itemPay", order=13)
	private Double itemPay;

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
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public String getPayType() {
		return this.payType;
	}
	public void setBasePer(Double basePer) {
		this.basePer = basePer;
	}
	
	public Double getBasePer() {
		return this.basePer;
	}
	public void setItemPer(Double itemPer) {
		this.itemPer = itemPer;
	}
	
	public Double getItemPer() {
		return this.itemPer;
	}
	public void setDesignFee(Double designFee) {
		this.designFee = designFee;
	}
	
	public Double getDesignFee() {
		return this.designFee;
	}
	public void setPrePay(Double prePay) {
		this.prePay = prePay;
	}
	
	public Double getPrePay() {
		return this.prePay;
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
	public void setBasePay(Double basePay) {
		this.basePay = basePay;
	}
	
	public Double getBasePay() {
		return this.basePay;
	}
	public void setItemPay(Double itemPay) {
		this.itemPay = itemPay;
	}
	
	public Double getItemPay() {
		return this.itemPay;
	}

}
