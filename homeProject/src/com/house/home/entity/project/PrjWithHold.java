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
 * PrjWithHold信息
 */
@Entity
@Table(name = "tPrjWithHold")
public class PrjWithHold extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "WorkType2")
	private String workType2;
	@Column(name = "Type")
	private String type;
	@Column(name = "Amount")
	private Double amount;
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
	@Column(name = "ItemAppNo")
	private String itemAppNo;
	@Column(name = "IsCreate")
	private String isCreate;
	
	@Transient
	private String workType2Descr;
	@Transient
	private String CustDescr;
	@Transient
	private String Address;
	@Transient
	private String WorkType1;
	@Transient
	private String WorkType1Descr;
	@Transient
	private String TypeDescr;
	@Transient
	private String isSpcprjWithHold;
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
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
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
	public void setItemAppNo(String itemAppNo) {
		this.itemAppNo = itemAppNo;
	}
	
	public String getItemAppNo() {
		return this.itemAppNo;
	}
	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}
	
	public String getIsCreate() {
		return this.isCreate;
	}

	public String getWorkType2Descr() {
		return workType2Descr;
	}

	public void setWorkType2Descr(String workType2Descr) {
		this.workType2Descr = workType2Descr;
	}

	public String getCustDescr() {
		return CustDescr;
	}

	public void setCustDescr(String custDescr) {
		CustDescr = custDescr;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getWorkType1() {
		return WorkType1;
	}

	public void setWorkType1(String workType1) {
		WorkType1 = workType1;
	}

	public String getWorkType1Descr() {
		return WorkType1Descr;
	}

	public void setWorkType1Descr(String workType1Descr) {
		WorkType1Descr = workType1Descr;
	}

	public String getTypeDescr() {
		return TypeDescr;
	}

	public void setTypeDescr(String typeDescr) {
		TypeDescr = typeDescr;
	}
	
}
