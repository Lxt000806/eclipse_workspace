package com.house.home.entity.salary;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tSalaryItem")
public class SalaryItem extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "ItemLevel")
	private Integer itemLevel;
	@Column(name = "IsAdjustable")
	private String isAdjustable;
	@Column(name = "IsSysRetention")
	private String isSysRetention;
	@Column(name = "ItemGroup")
	private String itemGroup;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Status")
	private String status;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Precision")
	private Integer precision;
	@Column(name = "Type")
	private String type;
	@Column(name = "PlusMinus")
	private Integer plusMinus;
	@Transient
	private String queryCondition;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPrecision() {
		return precision;
	}
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	public String getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Integer getItemLevel() {
		return itemLevel;
	}
	public void setItemLevel(Integer itemLevel) {
		this.itemLevel = itemLevel;
	}
	public String getIsAdjustable() {
		return isAdjustable;
	}
	public void setIsAdjustable(String isAdjustable) {
		this.isAdjustable = isAdjustable;
	}
	public String getIsSysRetention() {
		return isSysRetention;
	}
	public void setIsSysRetention(String isSysRetention) {
		this.isSysRetention = isSysRetention;
	}
	public String getItemGroup() {
		return itemGroup;
	}
	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Integer getPlusMinus() {
		return plusMinus;
	}
	public void setPlusMinus(Integer plusMinus) {
		this.plusMinus = plusMinus;
	}
	

	
}
