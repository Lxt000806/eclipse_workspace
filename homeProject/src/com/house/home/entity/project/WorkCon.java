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

/**
 * WorkCon信息
 */
@Entity
@Table(name = "tWorkCon")
public class WorkCon extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "WorkType2")
	private String workType2;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "Area")
	private Integer area;
	@Column(name = "ConstructDay")
	private Integer constructDay;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "WorkName")
	private String workName;
	
	@Transient
	private String workType1;
	@Transient
	private String custDescr;
	@Transient
	private String address;
	@Transient
	private String workType2Descr;
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}
	
	public String getWorkType2() {
		return this.workType2;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	
	public Integer getArea() {
		return this.area;
	}
	public void setConstructDay(Integer constructDay) {
		this.constructDay = constructDay;
	}
	
	public Integer getConstructDay() {
		return this.constructDay;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	public String getWorkName() {
		return this.workName;
	}

	public String getWorkType1() {
		return workType1;
	}

	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}

	public String getCustDescr() {
		return custDescr;
	}

	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWorkType2Descr() {
		return workType2Descr;
	}

	public void setWorkType2Descr(String workType2Descr) {
		this.workType2Descr = workType2Descr;
	}
	
}
