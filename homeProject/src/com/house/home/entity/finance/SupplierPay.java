package com.house.home.entity.finance;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name = "tSupplierPay")
public class SupplierPay extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CheckOutNo")
	private String checkOutNo;
	@Column(name = "Status")
	private String status;
	@Column(name = "PaidAmount")
	private Double paidAmount;
	@Column(name = "NowAmount")
	private Double nowAmount;
	@Column(name = "PreAmount")
	private Double preAmount;
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
	private Date appDateFrom; //申请日期从
	@Transient
	private Date appDateTo; //到
	@Transient
	private String splCode; //供应商编码
	@Transient
	private String itemType1; //材料类型1
	@Transient
	private String detailJson;// 批量json转xml
	
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getSplCode() {
		return splCode;
	}
	public void setSplCode(String splCode) {
		this.splCode = splCode;
	}
	public Date getAppDateFrom() {
		return appDateFrom;
	}
	public void setAppDateFrom(Date appDateFrom) {
		this.appDateFrom = appDateFrom;
	}
	public Date getAppDateTo() {
		return appDateTo;
	}
	public void setAppDateTo(Date appDateTo) {
		this.appDateTo = appDateTo;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCheckOutNo() {
		return checkOutNo;
	}
	public void setCheckOutNo(String checkOutNo) {
		this.checkOutNo = checkOutNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public Double getNowAmount() {
		return nowAmount;
	}
	public void setNowAmount(Double nowAmount) {
		this.nowAmount = nowAmount;
	}
	public Double getPreAmount() {
		return preAmount;
	}
	public void setPreAmount(Double preAmount) {
		this.preAmount = preAmount;
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
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

}
