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
 * IntReplenishDT信息
 */
@Entity
@Table(name = "tIntReplenish")
public class IntReplenish extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "WKPBNo")
	private String wkpbNo;
	@Column(name = "IsCupboard")
	private String isCupboard;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "Source")
	private String source;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "BoardOKDate")
	private Date boardOKDate;
	@Column(name = "BoardOKRemarks")
	private String boardOKRemarks;
	@Column(name = "ReadyDate")
	private Date readyDate;
	@Column(name = "ServiceDate")
	private Date serviceDate;
	@Column(name = "ServiceManType")
	private String serviceManType;
	@Column(name = "ServiceMan")
	private String serviceMan;
	@Column(name = "ResPart")
	private String resPart;
	@Column(name = "FinishDate")
	private Date finishDate;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "Date")
	private Date Date;
	@Column(name = "FixDutyMan")
	private String fixDutyMan;
	@Column(name = "FixDutyDate")
	private Date fixDutyDate;
	@Transient
	private String custDescr;
	@Transient
	private String resPartDescr;
	@Transient
	private String serviceManDescr;
	@Transient
	private String address;
	@Transient
	private Date boardOKDateFrom;
	@Transient
	private Date boardOKDateTo;
	@Transient
	private Date readyDateFrom;
	@Transient
	private Date readyDateTo;
	@Transient
	private Date finishDateFrom;
	@Transient
	private Date finishDateTo;
	@Transient
	private String detailJson; // 批量json转xml
	@Transient
	private String worker;
	@Transient
	private String workerDescr;
	@Transient
	private Date endDate;
	@Transient
	private String intDepartment2;//集成部
	@Transient
	private String isFixDuty;
	@Transient
	private String region;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getWkpbNo() {
		return wkpbNo;
	}
	public void setWkpbNo(String wkpbNo) {
		this.wkpbNo = wkpbNo;
	}
	public String getIsCupboard() {
		return isCupboard;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getBoardOKDate() {
		return boardOKDate;
	}
	public void setBoardOKDate(Date boardOKDate) {
		this.boardOKDate = boardOKDate;
	}
	public String getBoardOKRemarks() {
		return boardOKRemarks;
	}
	public void setBoardOKRemarks(String boardOKRemarks) {
		this.boardOKRemarks = boardOKRemarks;
	}
	public Date getReadyDate() {
		return readyDate;
	}
	public void setReadyDate(Date readyDate) {
		this.readyDate = readyDate;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	public String getServiceManType() {
		return serviceManType;
	}
	public void setServiceManType(String serviceManType) {
		this.serviceManType = serviceManType;
	}
	public String getServiceMan() {
		return serviceMan;
	}
	public void setServiceMan(String serviceMan) {
		this.serviceMan = serviceMan;
	}
	public String getResPart() {
		return resPart;
	}
	public void setResPart(String resPart) {
		this.resPart = resPart;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBoardOKDateFrom() {
		return boardOKDateFrom;
	}
	public void setBoardOKDateFrom(Date boardOKDateFrom) {
		this.boardOKDateFrom = boardOKDateFrom;
	}
	public Date getBoardOKDateTo() {
		return boardOKDateTo;
	}
	public void setBoardOKDateTo(Date boardOKDateTo) {
		this.boardOKDateTo = boardOKDateTo;
	}
	public Date getReadyDateFrom() {
		return readyDateFrom;
	}
	public void setReadyDateFrom(Date readyDateFrom) {
		this.readyDateFrom = readyDateFrom;
	}
	public Date getReadyDateTo() {
		return readyDateTo;
	}
	public void setReadyDateTo(Date readyDateTo) {
		this.readyDateTo = readyDateTo;
	}
	public Date getFinishDateFrom() {
		return finishDateFrom;
	}
	public void setFinishDateFrom(Date finishDateFrom) {
		this.finishDateFrom = finishDateFrom;
	}
	public Date getFinishDateTo() {
		return finishDateTo;
	}
	public void setFinishDateTo(Date finishDateTo) {
		this.finishDateTo = finishDateTo;
	}
	public String getCustDescr() {
		return custDescr;
	}
	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}
	public String getResPartDescr() {
		return resPartDescr;
	}
	public String getServiceManDescr() {
		return serviceManDescr;
	}
	public void setServiceManDescr(String serviceManDescr) {
		this.serviceManDescr = serviceManDescr;
	}
	public void setResPartDescr(String resPartDescr) {
		this.resPartDescr = resPartDescr;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getWorkerDescr() {
		return workerDescr;
	}
	public void setWorkerDescr(String workerDescr) {
		this.workerDescr = workerDescr;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getIntDepartment2() {
		return intDepartment2;
	}
	public void setIntDepartment2(String intDepartment2) {
		this.intDepartment2 = intDepartment2;
	}
	public String getAppCzy() {
		return appCzy;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public String getFixDutyMan() {
		return fixDutyMan;
	}
	public void setFixDutyMan(String fixDutyMan) {
		this.fixDutyMan = fixDutyMan;
	}
	public Date getFixDutyDate() {
		return fixDutyDate;
	}
	public void setFixDutyDate(Date fixDutyDate) {
		this.fixDutyDate = fixDutyDate;
	}
	public String getIsFixDuty() {
		return isFixDuty;
	}
	public void setIsFixDuty(String isFixDuty) {
		this.isFixDuty = isFixDuty;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	
}
