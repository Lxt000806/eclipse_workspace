package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tCustCheckData")
public class CustCheckData extends BaseEntity{
 	
	private static final long serialVersionUID = 1L;
	
	@Id
 	@Column(name = "CustCode")
	private String custCode;
 	@Column(name = "ToiletNum")
	private Integer toiletNum;
 	@Column(name = "BedroomNum")
	private Integer bedroomNum;
 	@Column(name = "KitchDoorType")
	private String kitchDoorType;
 	@Column(name = "BalconyNum")
	private Integer balconyNum;
 	@Column(name = "BalconyTitle")
	private String balconyTitle;
 	@Column(name = "IsWood")
	private String isWood;
 	@Column(name = "LastUpdate")
	private Date lastUpdate;
 	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
 	@Column(name = "Expired")
	private String expired;
 	@Column(name = "ActionLog")
	private String actionLog;
 	@Column(name = "IsBuildWall")
	private String isBuildWall;
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Integer getToiletNum() {
		return toiletNum;
	}
	public void setToiletNum(Integer toiletNum) {
		this.toiletNum = toiletNum;
	}
	public Integer getBedroomNum() {
		return bedroomNum;
	}
	public void setBedroomNum(Integer bedroomNum) {
		this.bedroomNum = bedroomNum;
	}
	public String getKitchDoorType() {
		return kitchDoorType;
	}
	public void setKitchDoorType(String kitchDoorType) {
		this.kitchDoorType = kitchDoorType;
	}
	public Integer getBalconyNum() {
		return balconyNum;
	}
	public void setBalconyNum(Integer balconyNum) {
		this.balconyNum = balconyNum;
	}
	public String getBalconyTitle() {
		return balconyTitle;
	}
	public void setBalconyTitle(String balconyTitle) {
		this.balconyTitle = balconyTitle;
	}
	public String getIsWood() {
		return isWood;
	}
	public void setIsWood(String isWood) {
		this.isWood = isWood;
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
	public String getIsBuildWall() {
		return isBuildWall;
	}
	public void setIsBuildWall(String isBuildWall) {
		this.isBuildWall = isBuildWall;
	}
 	
 	
 	
 	
}
