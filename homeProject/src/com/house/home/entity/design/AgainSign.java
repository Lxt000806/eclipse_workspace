package com.house.home.entity.design;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * AgainSign信息
 */
@Entity
@Table(name = "tAgainSign")
public class AgainSign extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "ContractType")
	private String contractType;
	@Column(name = "ContractFee")
	private Double contractFee;
	@Column(name = "SignDate")
	private Date signDate;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "NewSignDate")
	private Date newSignDate;
	@Column(name = "HadCalcPerf")
	private String hadCalcPerf;
	@Column(name = "PerfPK")
	private Integer perfPk;
	@Column(name = "HadCalcBackPerf")
	private String hadCalcBackPerf;
	@Column(name = "BackPerfPK")
	private Integer backPerfPk;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
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
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	public String getContractType() {
		return this.contractType;
	}
	public void setContractFee(Double contractFee) {
		this.contractFee = contractFee;
	}
	
	public Double getContractFee() {
		return this.contractFee;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	public Date getSignDate() {
		return this.signDate;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setNewSignDate(Date newSignDate) {
		this.newSignDate = newSignDate;
	}
	
	public Date getNewSignDate() {
		return this.newSignDate;
	}
	public void setHadCalcPerf(String hadCalcPerf) {
		this.hadCalcPerf = hadCalcPerf;
	}
	
	public String getHadCalcPerf() {
		return this.hadCalcPerf;
	}
	public void setPerfPk(Integer perfPk) {
		this.perfPk = perfPk;
	}
	
	public Integer getPerfPk() {
		return this.perfPk;
	}
	public void setHadCalcBackPerf(String hadCalcBackPerf) {
		this.hadCalcBackPerf = hadCalcBackPerf;
	}
	
	public String getHadCalcBackPerf() {
		return this.hadCalcBackPerf;
	}
	public void setBackPerfPk(Integer backPerfPk) {
		this.backPerfPk = backPerfPk;
	}
	
	public Integer getBackPerfPk() {
		return this.backPerfPk;
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

}
