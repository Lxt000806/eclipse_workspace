package com.house.home.entity.design;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * BaseItemPlanBak信息
 */
@Entity
@Table(name = "tBaseItemPlanBak")
public class BaseItemPlanBak extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "BaseTempNo")
	private String baseTempNo;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Transient
	private String isOutSet;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}
	public void setBaseTempNo(String baseTempNo) {
		this.baseTempNo = baseTempNo;
	}
	
	public String getBaseTempNo() {
		return this.baseTempNo;
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

	public String getIsOutSet() {
		return isOutSet;
	}

	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}

	
	
}
