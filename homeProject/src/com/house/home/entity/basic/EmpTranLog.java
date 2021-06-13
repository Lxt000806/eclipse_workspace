package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * EmpTranLog信息
 */
@Entity
@Table(name = "tEmpTranLog")
public class EmpTranLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Number")
	private String number;
	@Column(name = "Date")
	private Date date;
	@Column(name = "OldDept")
	private String oldDept;
	@Column(name = "Dept")
	private String dept;
	@Column(name = "OldPosition")
	private String oldPosition;
	@Column(name = "Position")
	private String position;
	@Column(name = "OldIsLead")
	private String oldIsLead;
	@Column(name = "IsLead")
	private String isLead;
	@Column(name = "OldLeadLevel")
	private String oldLeadLevel;
	@Column(name = "LeadLevel")
	private String leadLevel;
	@Column(name = "OldStatus")
	private String oldStatus;
	@Column(name = "Status")
	private String status;
	@Column(name = "ModifyCZY")
	private String modifyCzy;
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
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return this.number;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setOldDept(String oldDept) {
		this.oldDept = oldDept;
	}
	
	public String getOldDept() {
		return this.oldDept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	
	public String getDept() {
		return this.dept;
	}
	public void setOldPosition(String oldPosition) {
		this.oldPosition = oldPosition;
	}
	
	public String getOldPosition() {
		return this.oldPosition;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPosition() {
		return this.position;
	}
	public void setOldIsLead(String oldIsLead) {
		this.oldIsLead = oldIsLead;
	}
	
	public String getOldIsLead() {
		return this.oldIsLead;
	}
	public void setIsLead(String isLead) {
		this.isLead = isLead;
	}
	
	public String getIsLead() {
		return this.isLead;
	}
	public void setOldLeadLevel(String oldLeadLevel) {
		this.oldLeadLevel = oldLeadLevel;
	}
	
	public String getOldLeadLevel() {
		return this.oldLeadLevel;
	}
	public void setLeadLevel(String leadLevel) {
		this.leadLevel = leadLevel;
	}
	
	public String getLeadLevel() {
		return this.leadLevel;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	
	public String getOldStatus() {
		return this.oldStatus;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setModifyCzy(String modifyCzy) {
		this.modifyCzy = modifyCzy;
	}
	
	public String getModifyCzy() {
		return this.modifyCzy;
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
