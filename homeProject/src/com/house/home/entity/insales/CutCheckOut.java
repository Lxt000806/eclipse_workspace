package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * CutCheckOut信息
 */
@Entity
@Table(name = "tCutCheckOut")
public class CutCheckOut extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CrtDate")
	private Date crtDate;
	@Column(name = "CrtCZY")
	private String crtCzy;
	@Column(name = "Status")
	private String status;
	@Column(name = "SubmitDate")
	private Date submitDate;
	@Column(name = "CompleteDate")
	private Date completeDate;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private Date completeDateFrom;
	@Transient
	private Date completeDateTo;
	@Transient
	private Date crtDateFrom;
	@Transient
	private Date crtDateTo;
	@Transient
	private String iano;
	@Transient
	private String crtCzyDescr;
	@Transient
	private Double cutFee;
	@Transient
	private String refpks;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private String sendType;
	@Transient
	private String size;
	@Transient
	private String cutCheckOutDetailJson;
	@Transient
	private String address;
	@Transient
	private String isComplete;
	@Transient
	private String pks;
	@Transient
	private String cutType;
	@Transient
	private String isCut;
	@Transient
	private Date submitDateFrom;
	@Transient
	private Date submitDateTo;
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	
	public Date getCrtDate() {
		return this.crtDate;
	}
	public void setCrtCzy(String crtCzy) {
		this.crtCzy = crtCzy;
	}
	
	public String getCrtCzy() {
		return this.crtCzy;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	
	public Date getSubmitDate() {
		return this.submitDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
	public Date getCompleteDate() {
		return this.completeDate;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
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

	public Date getCompleteDateFrom() {
		return completeDateFrom;
	}

	public void setCompleteDateFrom(Date completeDateFrom) {
		this.completeDateFrom = completeDateFrom;
	}

	public Date getCompleteDateTo() {
		return completeDateTo;
	}

	public void setCompleteDateTo(Date completeDateTo) {
		this.completeDateTo = completeDateTo;
	}

	public Date getCrtDateFrom() {
		return crtDateFrom;
	}

	public void setCrtDateFrom(Date crtDateFrom) {
		this.crtDateFrom = crtDateFrom;
	}

	public Date getCrtDateTo() {
		return crtDateTo;
	}

	public void setCrtDateTo(Date crtDateTo) {
		this.crtDateTo = crtDateTo;
	}

	public String getIano() {
		return iano;
	}

	public void setIano(String iano) {
		this.iano = iano;
	}

	public String getCrtCzyDescr() {
		return crtCzyDescr;
	}

	public void setCrtCzyDescr(String crtCzyDescr) {
		this.crtCzyDescr = crtCzyDescr;
	}

	public Double getCutFee() {
		return cutFee;
	}

	public void setCutFee(Double cutFee) {
		this.cutFee = cutFee;
	}

	public String getRefpks() {
		return refpks;
	}

	public void setRefpks(String refpks) {
		this.refpks = refpks;
	}

	public Date getConfirmDateFrom() {
		return confirmDateFrom;
	}

	public void setConfirmDateFrom(Date confirmDateFrom) {
		this.confirmDateFrom = confirmDateFrom;
	}

	public Date getConfirmDateTo() {
		return confirmDateTo;
	}

	public void setConfirmDateTo(Date confirmDateTo) {
		this.confirmDateTo = confirmDateTo;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCutCheckOutDetailJson() {
		return XmlConverUtil.jsonToXmlNoHead(cutCheckOutDetailJson);
	}

	public void setCutCheckOutDetailJson(String cutCheckOutDetailJson) {
		this.cutCheckOutDetailJson = cutCheckOutDetailJson;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public String getPks() {
		return pks;
	}

	public void setPks(String pks) {
		this.pks = pks;
	}

	public String getCutType() {
		return cutType;
	}

	public void setCutType(String cutType) {
		this.cutType = cutType;
	}

	public String getIsCut() {
		return isCut;
	}

	public void setIsCut(String isCut) {
		this.isCut = isCut;
	}

	public Date getSubmitDateFrom() {
		return submitDateFrom;
	}

	public void setSubmitDateFrom(Date submitDateFrom) {
		this.submitDateFrom = submitDateFrom;
	}

	public Date getSubmitDateTo() {
		return submitDateTo;
	}

	public void setSubmitDateTo(Date submitDateTo) {
		this.submitDateTo = submitDateTo;
	}
	
}
