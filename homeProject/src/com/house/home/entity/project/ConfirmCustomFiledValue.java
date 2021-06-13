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

@Entity
@Table(name = "tConfirmCustomFiledValue")
public class ConfirmCustomFiledValue extends BaseEntity {
	private static final long serialVersionUID = 1L;

		@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private int pk;
	@Column(name = "PrjProgConfirmNo")
	private String prjProgConfirmNo;
	@Column(name = "ConfirmCustomFiledCode")
	private String confirmCustomFiledCode;
	@Column(name = "Value")
	private String value;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getPrjProgConfirmNo() {
		return prjProgConfirmNo;
	}
	public void setPrjProgConfirmNo(String prjProgConfirmNo) {
		this.prjProgConfirmNo = prjProgConfirmNo;
	}
	public String getConfirmCustomFiledCode() {
		return confirmCustomFiledCode;
	}
	public void setConfirmCustomFiledCode(String confirmCustomFiledCode) {
		this.confirmCustomFiledCode = confirmCustomFiledCode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
