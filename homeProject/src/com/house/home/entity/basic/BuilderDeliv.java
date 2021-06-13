package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * BuilderDeliv信息
 */
@Entity
@Table(name = "tBuilderDeliv")
public class BuilderDeliv extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "DelivDate")
	private Date delivDate;
	@Column(name = "DelivNum")
	private Integer delivNum;
	@Column(name = "BuilderCode")
	private String builderCode;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "BuilderType")
	private String builderType;
	@Column(name = "SpcBuilder")
	private String spcBuilder;

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDelivDate(Date delivDate) {
		this.delivDate = delivDate;
	}
	
	public Date getDelivDate() {
		return this.delivDate;
	}
	public void setDelivNum(Integer delivNum) {
		this.delivNum = delivNum;
	}
	
	public Integer getDelivNum() {
		return this.delivNum;
	}
	public void setBuilderCode(String builderCode) {
		this.builderCode = builderCode;
	}
	
	public String getBuilderCode() {
		return this.builderCode;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
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
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setBuilderType(String builderType) {
		this.builderType = builderType;
	}
	
	public String getBuilderType() {
		return this.builderType;
	}
	public void setSpcBuilder(String spcBuilder) {
		this.spcBuilder = spcBuilder;
	}
	
	public String getSpcBuilder() {
		return this.spcBuilder;
	}

}
