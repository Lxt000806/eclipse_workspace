package com.house.home.entity.finance;

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
 * IntPerfDetail信息
 */
@Entity
@Table(name = "tIntPerfDetail")
public class IntPerfDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "Type")
	private String type;
	@Column(name = "IsCupboard")
	private String isCupboard;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "DesignMan")
	private String designMan;
	@Column(name = "SaleAmount")
	private Double saleAmount;
	@Column(name = "DiscAmount")
	private Double discAmount;
	@Column(name = "PerfAmount")
	private Double perfAmount;
	@Column(name = "PerfAmount_Set")
	private Double perfAmountSet;
	@Column(name = "TotalPerfAmount")
	private Double totalPerfAmount;
	@Column(name = "IsModified")
	private String isModified;
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
	private String department2;
	@Transient
	private String isReportView;
	@Transient
	private String disc;
	@Transient
	private Date intPerfEndDate;//集成业绩周期截止时间
	@Transient
	private String custType;
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	
	public String getIsCupboard() {
		return this.isCupboard;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}
	
	public String getDesignMan() {
		return this.designMan;
	}
	public void setSaleAmount(Double saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	public Double getSaleAmount() {
		return this.saleAmount;
	}
	public void setDiscAmount(Double discAmount) {
		this.discAmount = discAmount;
	}
	
	public Double getDiscAmount() {
		return this.discAmount;
	}
	public void setPerfAmount(Double perfAmount) {
		this.perfAmount = perfAmount;
	}
	
	public Double getPerfAmount() {
		return this.perfAmount;
	}
	public void setPerfAmountSet(Double perfAmountSet) {
		this.perfAmountSet = perfAmountSet;
	}
	
	public Double getPerfAmountSet() {
		return this.perfAmountSet;
	}
	public void setTotalPerfAmount(Double totalPerfAmount) {
		this.totalPerfAmount = totalPerfAmount;
	}
	
	public Double getTotalPerfAmount() {
		return this.totalPerfAmount;
	}
	public void setIsModified(String isModified) {
		this.isModified = isModified;
	}
	
	public String getIsModified() {
		return this.isModified;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getIsReportView() {
		return isReportView;
	}

	public void setIsReportView(String isReportView) {
		this.isReportView = isReportView;
	}

	public String getDisc() {
		return disc;
	}

	public void setDisc(String disc) {
		this.disc = disc;
	}

	public Date getIntPerfEndDate() {
		return intPerfEndDate;
	}

	public void setIntPerfEndDate(Date intPerfEndDate) {
		this.intPerfEndDate = intPerfEndDate;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}
	
}
