package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemConfirmInform信息
 */
@Entity
@Table(name = "tItemConfirmInform")
public class ItemConfirmInform extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "ItemTimeCode")
	private String itemTimeCode;
	@Column(name = "InformDate")
	private Date informDate;
	@Column(name = "PlanComeDate")
	private Date planComeDate;
	@Column(name = "InformRemark")
	private String informRemark;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Transient
	private String address;
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
	public void setItemTimeCode(String itemTimeCode) {
		this.itemTimeCode = itemTimeCode;
	}
	
	public String getItemTimeCode() {
		return this.itemTimeCode;
	}
	public void setInformDate(Date informDate) {
		this.informDate = informDate;
	}
	
	public Date getInformDate() {
		return this.informDate;
	}
	public void setPlanComeDate(Date planComeDate) {
		this.planComeDate = planComeDate;
	}
	
	public Date getPlanComeDate() {
		return this.planComeDate;
	}
	public void setInformRemark(String informRemark) {
		this.informRemark = informRemark;
	}
	
	public String getInformRemark() {
		return this.informRemark;
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
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
