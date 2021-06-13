package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name="tSegDiscRule")
public class SegDiscRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PK")
    private Integer pk;
	@Column(name = "CustType")
    private String custType;
	@Column(name = "DiscAmtType")
    private String discAmtType;
	@Column(name = "MinArea")
    private Integer minArea;
	@Column(name = "MaxArea")
    private Integer maxArea;
	@Column(name = "MaxDiscAmount")
    private Double maxDiscAmount;
	@Column(name = "LastUpdate")
    private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
	@Column(name = "Expired")
    private String expired;
	@Column(name = "ActionLog")
    private String actionLog;
	@Column(name = "DirectorDiscAmount")
    private Double directorDiscAmount;
	
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getDiscAmtType() {
		return discAmtType;
	}
	public void setDiscAmtType(String discAmtType) {
		this.discAmtType = discAmtType;
	}
	public Integer getMinArea() {
		return minArea;
	}
	public void setMinArea(Integer minArea) {
		this.minArea = minArea;
	}
	public Integer getMaxArea() {
		return maxArea;
	}
	public void setMaxArea(Integer maxArea) {
		this.maxArea = maxArea;
	}
	public Double getMaxDiscAmount() {
		return maxDiscAmount;
	}
	public void setMaxDiscAmount(Double maxDiscAmount) {
		this.maxDiscAmount = maxDiscAmount;
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
	public Double getDirectorDiscAmount() {
		return directorDiscAmount;
	}
	public void setDirectorDiscAmount(Double directorDiscAmount) {
		this.directorDiscAmount = directorDiscAmount;
	}
	
}
