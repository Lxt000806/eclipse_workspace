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
 * CustContractValue信息
 */
@Entity
@Table(name = "tCustContractValue")
public class CustContractValue extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustContractPK")
	private Integer custContractPk;
	@Column(name = "TheKey")
	private String theKey;
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

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustContractPk(Integer custContractPk) {
		this.custContractPk = custContractPk;
	}
	
	public Integer getCustContractPk() {
		return this.custContractPk;
	}
	
	public String getTheKey() {
		return theKey;
	}

	public void setTheKey(String theKey) {
		this.theKey = theKey;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
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
