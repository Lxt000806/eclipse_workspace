package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * WfProcInst信息
 */
@Entity
@Table(name = "tWfProcInst")
public class WfProcInst extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "WfProcNo")
	private String wfProcNo;
	@Column(name = "Status")
	private String status;
	@Column(name = "IsPass")
	private String isPass;
	@Column(name = "ActProcInstId")
	private String actProcInstId;
	@Column(name = "ActProcDefId")
	private String actProcDefId;
	@Column(name = "StartUserId")
	private String startUserId;
	@Column(name = "StartTime")
	private Date startTime;
	@Column(name = "EndTime")
	private Date endTime;
	@Column(name = "BusinessKey")
	private String businessKey;
	@Column(name = "Summary")
	private String summary;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name="Department")
	private String department;
	@Column(name="PrintTimes")
	private Integer printTimes;
	
	@Transient
	private Integer type;
	@Transient
	private String userId;
	@Transient
	private Date startTimeFrom;
	@Transient
	private Date startTimeTo;
	@Transient
	private Date endTimeFrom;
	@Transient
	private Date endTimeTo;
	@Transient
	private String procID;
	@Transient
	private String procKey;
	@Transient
	private String procDescr;
	@Transient
	private String appType;
	@Transient
	private String descr;
	@Transient
	private String rcvStatus;
	@Transient
	private String detailJson;
	@Transient 
	private String empComment;
	@Transient
	private String czybh;
	
	
	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getEmpComment() {
		return empComment;
	}

	public void setEmpComment(String empComment) {
		this.empComment = empComment;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getRcvStatus() {
		return rcvStatus;
	}

	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getProcDescr() {
		return procDescr;
	}

	public void setProcDescr(String procDescr) {
		this.procDescr = procDescr;
	}

	public String getProcKey() {
		return procKey;
	}

	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}

	public String getProcID() {
		return procID;
	}

	public void setProcID(String procID) {
		this.procID = procID;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setWfProcNo(String wfProcNo) {
		this.wfProcNo = wfProcNo;
	}
	
	public String getWfProcNo() {
		return this.wfProcNo;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	
	public String getIsPass() {
		return this.isPass;
	}
	public void setActProcInstId(String actProcInstId) {
		this.actProcInstId = actProcInstId;
	}
	
	public String getActProcInstId() {
		return this.actProcInstId;
	}
	public void setActProcDefId(String actProcDefId) {
		this.actProcDefId = actProcDefId;
	}
	
	public String getActProcDefId() {
		return this.actProcDefId;
	}
	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}
	
	public String getStartUserId() {
		return this.startUserId;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
	public String getBusinessKey() {
		return this.businessKey;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getSummary() {
		return this.summary;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartTimeFrom() {
		return startTimeFrom;
	}

	public void setStartTimeFrom(Date startTimeFrom) {
		this.startTimeFrom = startTimeFrom;
	}

	public Date getStartTimeTo() {
		return startTimeTo;
	}

	public void setStartTimeTo(Date startTimeTo) {
		this.startTimeTo = startTimeTo;
	}

	public Date getEndTimeFrom() {
		return endTimeFrom;
	}

	public void setEndTimeFrom(Date endTimeFrom) {
		this.endTimeFrom = endTimeFrom;
	}

	public Date getEndTimeTo() {
		return endTimeTo;
	}

	public void setEndTimeTo(Date endTimeTo) {
		this.endTimeTo = endTimeTo;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public Integer getPrintTimes() {
		return printTimes;
	}

	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}
	
}
