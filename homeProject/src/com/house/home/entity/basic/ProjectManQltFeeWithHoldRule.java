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
 * TprojectManQltFeeWithHoldRule信息
 */
@Entity
@Table(name = "tProjectManQltFeeWithHoldRule")
public class ProjectManQltFeeWithHoldRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Type")
	private String type;
	@Column(name = "QltFeeFrom")
	private Double qltFeeFrom;
	@Column(name = "QltFeeTo")
	private Double qltFeeTo;
	@Column(name = "CommiAmountFrom")
	private Double commiAmountFrom;
	@Column(name = "CommiAmountTo")
	private Double commiAmountTo;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "QltFeeLimitAmount")
	private Double qltFeeLimitAmount;
	@Column(name = "IsSupvr")
	private String IsSupvr;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	public Double getQltFeeLimitAmount() {
		return qltFeeLimitAmount;
	}

	public void setQltFeeLimitAmount(Double qltFeeLimitAmount) {
		this.qltFeeLimitAmount = qltFeeLimitAmount;
	}

	public String getIsSupvr() {
		return IsSupvr;
	}

	public void setIsSupvr(String isSupvr) {
		IsSupvr = isSupvr;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setQltFeeFrom(Double qltFeeFrom) {
		this.qltFeeFrom = qltFeeFrom;
	}
	
	public Double getQltFeeFrom() {
		return this.qltFeeFrom;
	}
	public void setQltFeeTo(Double qltFeeTo) {
		this.qltFeeTo = qltFeeTo;
	}
	
	public Double getQltFeeTo() {
		return this.qltFeeTo;
	}
	public void setCommiAmountFrom(Double commiAmountFrom) {
		this.commiAmountFrom = commiAmountFrom;
	}
	
	public Double getCommiAmountFrom() {
		return this.commiAmountFrom;
	}
	public void setCommiAmountTo(Double commiAmountTo) {
		this.commiAmountTo = commiAmountTo;
	}
	
	public Double getCommiAmountTo() {
		return this.commiAmountTo;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
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
