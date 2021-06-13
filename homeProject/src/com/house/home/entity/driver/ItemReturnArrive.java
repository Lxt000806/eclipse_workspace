package com.house.home.entity.driver;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemReturnArrive信息
 */
@Entity
@Table(name = "tItemReturnArrive")
public class ItemReturnArrive extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "ReturnNo")
	private String returnNo;
	@Column(name = "DriverCode")
	private String driverCode;
	@Column(name = "Address")
	private String address;
	@Column(name = "DriverRemark")
	private String driverRemark;
	@Column(name = "ArriveDate")
	private Date arriveDate;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}
	
	public String getReturnNo() {
		return this.returnNo;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	
	public String getDriverCode() {
		return this.driverCode;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setDriverRemark(String driverRemark) {
		this.driverRemark = driverRemark;
	}
	
	public String getDriverRemark() {
		return this.driverRemark;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	
	public Date getArriveDate() {
		return this.arriveDate;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
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

}
