package com.house.home.entity.commi;

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
 * SoftBusiCommi信息
 */
@Entity
@Table(name = "tSoftBusiCommi")
public class SoftBusiCommi extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CommiNo")
	private String commiNo;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Type")
	private String type;
	@Column(name = "BusiType")
	private String busiType;
	@Column(name = "EmpCode")
	private String empCode;
	@Column(name = "Department")
	private String department;
	@Column(name = "ChgNo")
	private String chgNo;
	@Column(name = "TotalAmount")
	private Double totalAmount;
	@Column(name = "ShouldProvidePer")
	private Double shouldProvidePer;
	@Column(name = "ShouldProvideAmount")
	private Double shouldProvideAmount;
	@Column(name = "TotalProvideAmount")
	private Double totalProvideAmount;
	@Column(name = "ThisAmount")
	private Double thisAmount;
	@Column(name = "IsComplete")
	private String isComplete;
	@Column(name = "IsDesignSale")
	private String isDesignSale;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "OldCommiPK")
	private Integer oldCommiPk;
	@Column(name = "EndCommiNo")
	private String endCommiNo;

	@Transient
	private String address;
	@Transient
	private String empName;
	@Transient
	private Integer crtMon;
	@Transient
	private String no;
	@Transient
	private String isInd;
	@Transient
	private String costRight;
	@Transient
	private Integer beginMon;
	@Transient
	private Integer endMon;
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCommiNo(String commiNo) {
		this.commiNo = commiNo;
	}
	
	public String getCommiNo() {
		return this.commiNo;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getEmpCode() {
		return this.empCode;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getDepartment() {
		return this.department;
	}
	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}
	
	public String getChgNo() {
		return this.chgNo;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public Double getTotalAmount() {
		return this.totalAmount;
	}
	public void setShouldProvidePer(Double shouldProvidePer) {
		this.shouldProvidePer = shouldProvidePer;
	}
	
	public Double getShouldProvidePer() {
		return this.shouldProvidePer;
	}
	public void setShouldProvideAmount(Double shouldProvideAmount) {
		this.shouldProvideAmount = shouldProvideAmount;
	}
	
	public Double getShouldProvideAmount() {
		return this.shouldProvideAmount;
	}
	public void setTotalProvideAmount(Double totalProvideAmount) {
		this.totalProvideAmount = totalProvideAmount;
	}
	
	public Double getTotalProvideAmount() {
		return this.totalProvideAmount;
	}
	public void setThisAmount(Double thisAmount) {
		this.thisAmount = thisAmount;
	}
	
	public Double getThisAmount() {
		return this.thisAmount;
	}
	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
	
	public String getIsComplete() {
		return this.isComplete;
	}
	public void setIsDesignSale(String isDesignSale) {
		this.isDesignSale = isDesignSale;
	}
	
	public String getIsDesignSale() {
		return this.isDesignSale;
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
	public void setOldCommiPk(Integer oldCommiPk) {
		this.oldCommiPk = oldCommiPk;
	}
	
	public Integer getOldCommiPk() {
		return this.oldCommiPk;
	}
	public void setEndCommiNo(String endCommiNo) {
		this.endCommiNo = endCommiNo;
	}
	
	public String getEndCommiNo() {
		return this.endCommiNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getCrtMon() {
		return crtMon;
	}

	public void setCrtMon(Integer crtMon) {
		this.crtMon = crtMon;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getIsInd() {
		return isInd;
	}

	public void setIsInd(String isInd) {
		this.isInd = isInd;
	}

	public String getCostRight() {
		return costRight;
	}

	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}

	public Integer getBeginMon() {
		return beginMon;
	}

	public void setBeginMon(Integer beginMon) {
		this.beginMon = beginMon;
	}

	public Integer getEndMon() {
		return endMon;
	}

	public void setEndMon(Integer endMon) {
		this.endMon = endMon;
	}
	
}
