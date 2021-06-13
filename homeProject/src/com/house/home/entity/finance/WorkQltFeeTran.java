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
 * WorkQltFeeTran信息
 */
@Entity
@Table(name = "tWorkQltFeeTran")
public class WorkQltFeeTran extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "WorkerCode")
	private String workerCode;
	@Column(name = "Date")
	private Date date;
	@Column(name = "TryFee")
	private Double tryFee;
	@Column(name = "AftFee")
	private Double aftFee;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "RefPk")
	private String refPk;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Type")
    private String type;
	
	@Transient 
    private String code;
	@Transient 
    private String NameChi;
	@Transient 
    private String Descr;
	@Transient 
    private Double QualityFee;
	@Transient 
    private Double accidentFee;
	@Transient
	private String empCode;

	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	
	public String getWorkerCode() {
		return this.workerCode;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setTryFee(Double tryFee) {
		this.tryFee = tryFee;
	}
	
	public Double getTryFee() {
		return this.tryFee;
	}
	public void setAftFee(Double aftFee) {
		this.aftFee = aftFee;
	}
	
	public Double getAftFee() {
		return this.aftFee;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setRefPk(String refPk) {
		this.refPk = refPk;
	}
	
	public String getRefPk() {
		return this.refPk;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNameChi() {
		return NameChi;
	}

	public void setNameChi(String nameChi) {
		NameChi = nameChi;
	}

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}

	public Double getQualityFee() {
		return QualityFee;
	}

	public void setQualityFee(Double qualityFee) {
		QualityFee = qualityFee;
	}

	public Double getAccidentFee() {
		return accidentFee;
	}

	public void setAccidentFee(Double accidentFee) {
		this.accidentFee = accidentFee;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
}
