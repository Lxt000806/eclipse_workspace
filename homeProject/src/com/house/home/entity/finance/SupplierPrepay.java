package com.house.home.entity.finance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * payManage信息
 */
@Entity
@Table(name = "tSupplierPrepay")
public class SupplierPrepay extends BaseEntity {
	
	private static final long serialVersionUID = 1L;	
    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Status")
	private String status;	
	@Column(name = "Type")
	private String type;	 
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "AppEmp")
	private String appEmp;	
	@Column(name = "AppDate")
	private Date appDate;
	@Column(name = "ConfirmEmp")
	private String confirmEmp;	
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "PayEmp")
	private String payEmp;	
	@Column(name = "PayDate")
	private Date payDate;
	@Column(name = "DocumentNo")
	private String documentNo;		
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
	private Date appdate1;
	@Transient
	private String appEmpDescr;
	@Transient
	private String confirmEmpDescr;
	@Transient
	private String payEmpDescr;
	@Transient
	private String puNo;
	@Transient
	private String splCode;
	@Transient
	private String splDescr;
	@Transient
	private String code;
	@Transient
	private String descr;
	@Transient
	private String sType;
	@Transient
	private Date payDateFrom;
	@Transient
	private Date payDateTo;
	@Transient
	private String readonly;
	@Transient
	private String wfProcInstNo;
	
	public String getWfProcInstNo() {
		return wfProcInstNo;
	}

	public void setWfProcInstNo(String wfProcInstNo) {
		this.wfProcInstNo = wfProcInstNo;
	}

	public String getSupplierPrepayDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAppEmp() {
		return appEmp;
	}

	public void setAppEmp(String appEmp) {
		this.appEmp = appEmp;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public String getConfirmEmp() {
		return confirmEmp;
	}

	public void setConfirmEmp(String confirmEmp) {
		this.confirmEmp = confirmEmp;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getPayEmp() {
		return payEmp;
	}

	public void setPayEmp(String payEmp) {
		this.payEmp = payEmp;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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

	public Date getAppdate1() {
		return appdate1;
	}

	public void setAppdate1(Date appdate1) {
		this.appdate1 = appdate1;
	}

	public String getAppEmpDescr() {
		return appEmpDescr;
	}

	public void setAppEmpDescr(String appEmpDescr) {
		this.appEmpDescr = appEmpDescr;
	}

	public String getConfirmEmpDescr() {
		return confirmEmpDescr;
	}

	public void setConfirmEmpDescr(String confirmEmpDescr) {
		this.confirmEmpDescr = confirmEmpDescr;
	}

	public String getPayEmpDescr() {
		return payEmpDescr;
	}

	public void setPayEmpDescr(String payEmpDescr) {
		this.payEmpDescr = payEmpDescr;
	}

	public String getPuNo() {
		return puNo;
	}

	public void setPuNo(String puNo) {
		this.puNo = puNo;
	}

	public String getSplCode() {
		return splCode;
	}

	public void setSplCode(String splCode) {
		this.splCode = splCode;
	}

	public String getSplDescr() {
		return splDescr;
	}

	public void setSplDescr(String splDescr) {
		this.splDescr = splDescr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
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
