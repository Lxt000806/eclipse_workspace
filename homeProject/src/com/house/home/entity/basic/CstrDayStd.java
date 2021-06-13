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
 * CstrDayStd信息
 */
@Entity
@Table(name = "tCstrDayStd")
public class CstrDayStd extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "Layout")
	private String layout;
	@Column(name = "FromArea")
	private Integer fromArea;
	@Column(name = "ToArea")
	private Double toArea;
	@Column(name = "ConstructDay")
	private Integer constructDay;
	@Column(name = "Prior")
	private Integer prior;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
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
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	
	public String getLayout() {
		return this.layout;
	}
	public void setFromArea(Integer fromArea) {
		this.fromArea = fromArea;
	}
	
	public Integer getFromArea() {
		return this.fromArea;
	}
	public void setToArea(Double toArea) {
		this.toArea = toArea;
	}
	
	public Double getToArea() {
		return this.toArea;
	}
	public void setConstructDay(Integer constructDay) {
		this.constructDay = constructDay;
	}
	
	public Integer getConstructDay() {
		return this.constructDay;
	}
	public void setPrior(Integer prior) {
		this.prior = prior;
	}
	
	public Integer getPrior() {
		return this.prior;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
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
