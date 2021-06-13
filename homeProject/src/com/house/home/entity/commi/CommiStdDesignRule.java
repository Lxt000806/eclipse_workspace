package com.house.home.entity.commi;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CommiStdDesignRule信息
 */
@Entity
@Table(name = "tCommiStdDesignRule")
public class CommiStdDesignRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "StdDesignFeeAmount")
	private Double stdDesignFeeAmount;
	@Column(name = "StdDesignFeePrice")
	private Double stdDesignFeePrice;
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
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setStdDesignFeeAmount(Double stdDesignFeeAmount) {
		this.stdDesignFeeAmount = stdDesignFeeAmount;
	}
	
	public Double getStdDesignFeeAmount() {
		return this.stdDesignFeeAmount;
	}
	public void setStdDesignFeePrice(Double stdDesignFeePrice) {
		this.stdDesignFeePrice = stdDesignFeePrice;
	}
	
	public Double getStdDesignFeePrice() {
		return this.stdDesignFeePrice;
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
