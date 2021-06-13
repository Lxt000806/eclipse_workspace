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
 * 
 * <p>Description: </p>客户回访表
 * @author	created by zb
 * @date	2018-7-18--下午3:40:58
 */
@Entity
@Table(name = "tCustVisit")
public class CustVisit extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "VisitType")
	private String visitType;
	@Column(name = "CrtDate")
	private Date crtDate;
	@Column(name = "Status")
	private String status;
	@Column(name = "VisitCZY")
	private String visitCZY;
	@Column(name = "VisitDate")
	private Date visitDate;
	@Column(name = "Satisfaction")
	private String satisfaction;/*满意度*/
	@Column(name = "IsComplete")
	private String isComplete;
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
	private Date dateFrom;/*回访日期*/
	@Transient
	private Date dateTo;
	@Transient
	private String address;/*楼盘*/
	@Transient
	private String department2;/*二级部门*/
	@Transient
	private String promStatus;//问题状态
	@Transient
	private String detailJson;/*批量json*/
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getVisitType() {
		return visitType;
	}
	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVisitCZY() {
		return visitCZY;
	}
	public void setVisitCZY(String visitCZY) {
		this.visitCZY = visitCZY;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public String getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getPromStatus() {
		return promStatus;
	}
	public void setPromStatus(String promStatus) {
		this.promStatus = promStatus;
	}
	/**
	 * json转xml,用xml格式传入到pCustVisitSave_forXml的存储过程中
	 * @author	created by zb
	 * @date	2018-7-16--下午3:05:52
	 * @return
	 */
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

}
