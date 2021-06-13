package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * WorkCost信息
 */
@Entity
@Table(name = "tWorkCost")
public class WorkCost extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Type")
	private String type;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "Date")
	private Date date;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Status")
	private String status;
	@Column(name = "ConfirmCZY")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "DocumentNo")
	private String documentNo;
	@Column(name = "PayCZY")
	private String payCzy;
	@Column(name = "PayDate")
	private Date payDate;
	@Column(name = "IsSysCrt")
	private String isSysCrt;
	@Column(name = "CommiNo")
	private String commiNo;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Transient
	private String TypeDescr;
	@Transient
	private String AppCZYDescr;
	@Transient
	private String StatusDescr;
	@Transient
	private String ConfirmCZYDescr;
	@Transient
	private String PayCZYDescr;
	@Transient
	private String IsSysCrtDescr;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private Date payDateFrom;
	@Transient
	private Date payDateTo;
	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	@Transient
	private String nos;
	@Transient
	private String ActName;
	@Transient
	private String CardID;
	@Transient
	private String ConfirmAmount;
	@Transient
	private String button;
	@Transient
	private String workCostDetailJson;
	@Transient
	private String laborCmpCode;
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
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	
	public String getDocumentNo() {
		return this.documentNo;
	}
	public void setPayCzy(String payCzy) {
		this.payCzy = payCzy;
	}
	
	public String getPayCzy() {
		return this.payCzy;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	public Date getPayDate() {
		return this.payDate;
	}
	public void setIsSysCrt(String isSysCrt) {
		this.isSysCrt = isSysCrt;
	}
	
	public String getIsSysCrt() {
		return this.isSysCrt;
	}
	public void setCommiNo(String commiNo) {
		this.commiNo = commiNo;
	}
	
	public String getCommiNo() {
		return this.commiNo;
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

	public String getTypeDescr() {
		return TypeDescr;
	}

	public void setTypeDescr(String typeDescr) {
		TypeDescr = typeDescr;
	}

	public String getAppCZYDescr() {
		return AppCZYDescr;
	}

	public void setAppCZYDescr(String appCZYDescr) {
		AppCZYDescr = appCZYDescr;
	}

	public String getStatusDescr() {
		return StatusDescr;
	}

	public void setStatusDescr(String statusDescr) {
		StatusDescr = statusDescr;
	}

	public String getConfirmCZYDescr() {
		return ConfirmCZYDescr;
	}

	public void setConfirmCZYDescr(String confirmCZYDescr) {
		ConfirmCZYDescr = confirmCZYDescr;
	}

	public String getPayCZYDescr() {
		return PayCZYDescr;
	}

	public void setPayCZYDescr(String payCZYDescr) {
		PayCZYDescr = payCZYDescr;
	}

	public String getIsSysCrtDescr() {
		return IsSysCrtDescr;
	}

	public void setIsSysCrtDescr(String isSysCrtDescr) {
		IsSysCrtDescr = isSysCrtDescr;
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

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getNos() {
		return nos;
	}

	public void setNos(String nos) {
		this.nos = nos;
	}

	public String getActName() {
		return ActName;
	}

	public void setActName(String actName) {
		ActName = actName;
	}

	public String getCardID() {
		return CardID;
	}

	public void setCardID(String cardID) {
		CardID = cardID;
	}

	public String getConfirmAmount() {
		return ConfirmAmount;
	}

	public void setConfirmAmount(String confirmAmount) {
		ConfirmAmount = confirmAmount;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}
	public String getWorkCostDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(workCostDetailJson);
    	return xml;
	}

	public void setWorkCostDetailJson(String workCostDetailJson) {
		this.workCostDetailJson = workCostDetailJson;
	}

	public String getLaborCmpCode() {
		return laborCmpCode;
	}

	public void setLaborCmpCode(String laborCmpCode) {
		this.laborCmpCode = laborCmpCode;
	}
	
}
