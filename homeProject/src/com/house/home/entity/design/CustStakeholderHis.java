package com.house.home.entity.design;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CustStakeholderHis信息
 */
@Entity
@Table(name = "tCustStakeholderHis")
public class CustStakeholderHis extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "OperType")
	private String operType;
	@Column(name = "Role")
	private String role;
	@Column(name = "OldRole")
	private String oldRole;
	@Column(name = "EmpCode")
	private String empCode;
	@Column(name = "OldEmpCode")
	private String oldEmpCode;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	public String getOperType() {
		return this.operType;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
	public void setOldRole(String oldRole) {
		this.oldRole = oldRole;
	}
	
	public String getOldRole() {
		return this.oldRole;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getEmpCode() {
		return this.empCode;
	}
	public void setOldEmpCode(String oldEmpCode) {
		this.oldEmpCode = oldEmpCode;
	}
	
	public String getOldEmpCode() {
		return this.oldEmpCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
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

}
