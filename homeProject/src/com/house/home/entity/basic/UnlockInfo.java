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
 * 开门记录
 * created by zb
 */
@Entity
@Table(name = "tUnlockInfo")
public class UnlockInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "LocalId")
	private String localId;
	@Column(name = "UnlockType")
	private String unlockType;
	@Column(name = "UnlockTime")
	private Date unlockTime;
	@Column(name = "UnlockUser")
	private String unlockUser;
	@Column(name = "CmpCode")
	private String cmpCode;
	@Column(name = "UnlockCZYType")
	private String unlockCZYType;
	@Column(name = "UnlockCZY")
	private String unlockCZY;
	@Column(name = "IsUnlock")
	private String isUnlock;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getUnlockType() {
		return unlockType;
	}
	public void setUnlockType(String unlockType) {
		this.unlockType = unlockType;
	}
	public Date getUnlockTime() {
		return unlockTime;
	}
	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}
	public String getUnlockUser() {
		return unlockUser;
	}
	public void setUnlockUser(String unlockUser) {
		this.unlockUser = unlockUser;
	}
	public String getCmpCode() {
		return cmpCode;
	}
	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}
	public String getUnlockCZYType() {
		return unlockCZYType;
	}
	public void setUnlockCZYType(String unlockCZYType) {
		this.unlockCZYType = unlockCZYType;
	}
	public String getUnlockCZY() {
		return unlockCZY;
	}
	public void setUnlockCZY(String unlockCZY) {
		this.unlockCZY = unlockCZY;
	}
	public String getIsUnlock() {
		return isUnlock;
	}
	public void setIsUnlock(String isUnlock) {
		this.isUnlock = isUnlock;
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
