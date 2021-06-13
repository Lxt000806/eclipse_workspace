package com.house.home.entity.salary;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * InjuryInsurParam信息
 */
@Entity
@Table(name = "tInjuryInsurParam")
public class InjuryInsurParam extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ConSignCmp")
	private String conSignCmp;
	@Column(name = "InjuryInsurBaseMin")
	private Double injuryInsurBaseMin;
	@Column(name = "InjuryInsurRate")
	private Double injuryInsurRate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	public void setConSignCmp(String conSignCmp) {
		this.conSignCmp = conSignCmp;
	}
	
	public String getConSignCmp() {
		return this.conSignCmp;
	}
	public void setInjuryInsurBaseMin(Double injuryInsurBaseMin) {
		this.injuryInsurBaseMin = injuryInsurBaseMin;
	}
	
	public Double getInjuryInsurBaseMin() {
		return this.injuryInsurBaseMin;
	}
	public void setInjuryInsurRate(Double injuryInsurRate) {
		this.injuryInsurRate = injuryInsurRate;
	}
	
	public Double getInjuryInsurRate() {
		return this.injuryInsurRate;
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
