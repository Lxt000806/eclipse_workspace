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

@Entity
@Table(name = "tIntProduce")
public class IntProduce extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name="CustCode")
	private String custCode;
	@Column(name="IsCupboard")
	private String isCupboard;
	@Column(name="SupplCode")
	private String supplCode;
	@Column(name="SetBoardDate")
	private Date setBoardDate;
	@Column(name = "ArrBoardDate")
	private Date arrBoardDate;
	@Column(name="OpenMaterialDate")
	private Date openMaterialDate;
	@Column(name="SealingSideDate")
	private Date sealingSideDate;
	@Column(name="ExHoleDate")
	private Date exHoleDate;
	@Column(name="PackDate")
	private Date packDate;
	@Column(name="InWHDate")
	private Date inWhDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String address;
	@Transient
	private String custName;
	
	@Transient
	private String includeShipped;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getIsCupboard() {
		return isCupboard;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	public Date getSetBoardDate() {
		return setBoardDate;
	}
	public void setSetBoardDate(Date setBoardDate) {
		this.setBoardDate = setBoardDate;
	}
	public Date getArrBoardDate() {
		return arrBoardDate;
	}
	public void setArrBoardDate(Date arrBoardDate) {
		this.arrBoardDate = arrBoardDate;
	}
	public Date getOpenMaterialDate() {
		return openMaterialDate;
	}
	public void setOpenMaterialDate(Date openMaterialDate) {
		this.openMaterialDate = openMaterialDate;
	}
	public Date getSealingSideDate() {
		return sealingSideDate;
	}
	public void setSealingSideDate(Date sealingSideDate) {
		this.sealingSideDate = sealingSideDate;
	}
	public Date getExHoleDate() {
		return exHoleDate;
	}
	public void setExHoleDate(Date exHoleDate) {
		this.exHoleDate = exHoleDate;
	}
	public Date getPackDate() {
		return packDate;
	}
	public void setPackDate(Date packDate) {
		this.packDate = packDate;
	}
	public Date getInWhDate() {
		return inWhDate;
	}
	public void setInWhDate(Date inWhDate) {
		this.inWhDate = inWhDate;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
    public String getIncludeShipped() {
        return includeShipped;
    }
    public void setIncludeShipped(String includeShipped) {
        this.includeShipped = includeShipped;
    }
	
}
