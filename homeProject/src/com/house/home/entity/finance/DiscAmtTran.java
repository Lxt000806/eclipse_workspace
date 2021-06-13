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

@Entity
@Table(name = "tDiscAmtTran")
public class DiscAmtTran extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Type")
	private String type;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "IsRiskFund")
	private String isRiskFund;
	@Column(name = "CustGiftPK")
	private Integer custGiftPK;
	@Column(name = "FixDutyManPK")
	private Integer fixDutyManPK;
	@Column(name = "IsExtra")
	private String isExtra;
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
	
	@Transient
	private String custName;	//客户姓名
	@Transient
	private String address;	//楼盘地址
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getIsRiskFund() {
		return isRiskFund;
	}
	public void setIsRiskFund(String isRiskFund) {
		this.isRiskFund = isRiskFund;
	}
	public Integer getCustGiftPK() {
		return custGiftPK;
	}
	public void setCustGiftPK(Integer custGiftPK) {
		this.custGiftPK = custGiftPK;
	}
	public Integer getFixDutyManPK() {
		return fixDutyManPK;
	}
	public void setFixDutyManPK(Integer fixDutyManPK) {
		this.fixDutyManPK = fixDutyManPK;
	}
	public String getIsExtra() {
		return isExtra;
	}
	public void setIsExtra(String isExtra) {
		this.isExtra = isExtra;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

}
