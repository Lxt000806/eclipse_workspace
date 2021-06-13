package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * PrjWithHold信息bean
 */
public class PrjWithHoldBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="workType2", order=3)
	private String workType2;
	@ExcelAnnotation(exportName="type", order=4)
	private String type;
	@ExcelAnnotation(exportName="amount", order=5)
	private Double amount;
	@ExcelAnnotation(exportName="remarks", order=6)
	private String remarks;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=7)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=8)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=9)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=10)
	private String actionLog;
	@ExcelAnnotation(exportName="itemAppNo", order=11)
	private String itemAppNo;
	@ExcelAnnotation(exportName="isCreate", order=12)
	private String isCreate;

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
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	
	public String getWorkType2() {
		return this.workType2;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
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
	public void setItemAppNo(String itemAppNo) {
		this.itemAppNo = itemAppNo;
	}
	
	public String getItemAppNo() {
		return this.itemAppNo;
	}
	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}
	
	public String getIsCreate() {
		return this.isCreate;
	}

}
