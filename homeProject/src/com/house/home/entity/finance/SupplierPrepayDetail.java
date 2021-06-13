package com.house.home.entity.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.house.framework.commons.orm.BaseEntity;

/**
 * payManage信息
 */
@Entity
@Table(name = "tSupplierPrepayDetail")
public class SupplierPrepayDetail extends BaseEntity {
	
	private static final long serialVersionUID = 1L;	
    	@Id
	@Column(name = "PK")
	private int pk;
    @Column(name = "No")
	private String no;
	@Column(name = "Status")
	private String status;	
	@Column(name = "PUNo")
	private String puNo;	 
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "AftAmount")
	private Double AftAmount;	
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
	private String detailJson;
	@Transient
	private String unSelected;
	@Transient
	private String descr1;
	@Transient
	private String itemtype1;
	@Transient
	private String splcode;
	@Transient
	private String spldescr;
	@Transient	
	private String type;
	@Transient	
	private String sType;
	@Transient	
	private Double firstamount;
	@Transient	
	private Double remainamount;
	@Transient	
	private Double prepaybalance;
	@Transient	
	private Double sumamount;
	@Transient
	private Date payDateFrom;
	@Transient
	private Date payDateTo;
	@Transient
	private String readonly;
	

	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPuNo() {
		return puNo;
	}
	public void setPuNo(String puNo) {
		this.puNo = puNo;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getAftAmount() {
		return AftAmount;
	}
	public void setAftAmount(Double aftAmount) {
		AftAmount = aftAmount;
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
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getUnSelected() {
		return unSelected;
	}
	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}
	public String getDescr1() {
		return descr1;
	}
	public void setDescr1(String descr1) {
		this.descr1 = descr1;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getItemtype1() {
		return itemtype1;
	}
	public void setItemtype1(String itemtype1) {
		this.itemtype1 = itemtype1;
	}
	public String getSplcode() {
		return splcode;
	}
	public void setSplcode(String splcode) {
		this.splcode = splcode;
	}
	public String getSpldescr() {
		return spldescr;
	}
	public void setSpldescr(String spldescr) {
		this.spldescr = spldescr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getFirstamount() {
		return firstamount;
	}
	public void setFirstamount(Double firstamount) {
		this.firstamount = firstamount;
	}
	public Double getRemainamount() {
		return remainamount;
	}
	public void setRemainamount(Double remainamount) {
		this.remainamount = remainamount;
	}
	public Double getPrepaybalance() {
		return prepaybalance;
	}
	public void setPrepaybalance(Double prepaybalance) {
		this.prepaybalance = prepaybalance;
	}
	public Double getSumamount() {
		return sumamount;
	}
	public void setSumamount(Double sumamount) {
		this.sumamount = sumamount;
	}
	public String getsType() {
		return sType;
	}
	public void setsType(String sType) {
		this.sType = sType;
	}
	public Date getPayDateFrom() {
		return payDateFrom;
	}
	public void setPayDateFrom(Date payDateFrom) {
		this.payDateFrom = payDateFrom;
	}
	public Date getPayDateTo() {
		return payDateTo;
	}
	public void setPayDateTo(Date payDateTo) {
		this.payDateTo = payDateTo;
	}
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	
}
