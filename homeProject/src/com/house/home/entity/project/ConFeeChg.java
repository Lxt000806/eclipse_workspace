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
 * ConFeeChg信息
 */
@Entity
@Table(name = "tConFeeChg")
public class ConFeeChg extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "ChgType")
	private String chgType;
	@Column(name = "ChgAmount")
	private Double chgAmount;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Status")
	private String status;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "Date")
	private Date date;
	@Column(name = "ConfirmCZY")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "IsService")
	private String isService;
	@Column(name = "IsCupboard")
	private String isCupboard;
	@Column(name = "ChgNo")
	private String chgNo;
	@Column(name = "PerfPK")
	private Integer perfPk;
	@Column(name = "IscalPerf")
	private String iscalPerf;
	
	@Transient
	private String address;
	@Transient
	private String custDescr;
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
	public void setChgType(String chgType) {
		this.chgType = chgType;
	}
	
	public String getChgType() {
		return this.chgType;
	}
	public void setChgAmount(Double chgAmount) {
		this.chgAmount = chgAmount;
	}
	
	public Double getChgAmount() {
		return this.chgAmount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
	public String getConfirmCzy() {
		return this.confirmCzy;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
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
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setIsService(String isService) {
		this.isService = isService;
	}
	
	public String getIsService() {
		return this.isService;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	
	public String getIsCupboard() {
		return this.isCupboard;
	}
	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}
	
	public String getChgNo() {
		return this.chgNo;
	}
	public void setPerfPk(Integer perfPk) {
		this.perfPk = perfPk;
	}
	
	public Integer getPerfPk() {
		return this.perfPk;
	}
	public void setIscalPerf(String iscalPerf) {
		this.iscalPerf = iscalPerf;
	}
	
	public String getIscalPerf() {
		return this.iscalPerf;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustDescr() {
		return custDescr;
	}

	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}

}
