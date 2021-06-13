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

@Entity
@Table(name = "tProgTempAlarm")
public class ProgTempAlarm extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name="TempNo")
	private String tempNo;
	@Column(name="PrjItem")
	private String prjItem;
	@Column(name="DayType")
	private String dayType;
	@Column(name="AddDay")
	private Integer addDay;
	@Column(name="MsgTextTemp")
	private String msgTextTemp;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String ActionLog;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="Role")
	private String role;
	@Column(name="Type")
	private String type;
	@Column(name="DealDay")
	private Integer dealDay;
	@Column(name="CompleteDay")
	private Integer completeDay;
	@Column(name="PrePK")
	private Integer prePK;
	@Column(name="ItemType1")
	private String itemType1;
	@Column(name="ItemType2")
	private String itemType2;
	@Column(name="IsNeedReq")
	private String isNeedReq;
	@Column(name="MsgTextTemp2")
	private String msgTextTemp2;
	@Column(name="WorkType1")
	private String workType1;
	@Column(name="WorkType12")
	private String workType12;
	@Column(name="JobType")
	private String jobType;
	@Column(name="Title")
	private String title;
	@Column(name="IsAutoJob")
	private String isAutoJob;
	@Column(name="CZYBH")
	private String czybh;
	@Column(name="RemindCZYType")
	private String remindCzyType;
	@Column(name="OfferWorkType2")
	private String offerWorkType2;
	
	@Transient
	private String m_umState;
	@Transient
	private String mm_umState;
	@Transient
	private String prjItems;
	@Transient
	private String rowId;
	@Transient
	private Integer nowPk;
	@Transient
	private String roleDescr;
	@Transient
	private String czyDescr;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getTempNo() {
		return tempNo;
	}
	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public Integer getAddDay() {
		return addDay;
	}
	public void setAddDay(Integer addDay) {
		this.addDay = addDay;
	}
	public String getMsgTextTemp() {
		return msgTextTemp;
	}
	public void setMsgTextTemp(String msgTextTemp) {
		this.msgTextTemp = msgTextTemp;
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
		return ActionLog;
	}
	public void setActionLog(String actionLog) {
		ActionLog = actionLog;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getDealDay() {
		return dealDay;
	}
	public void setDealDay(Integer dealDay) {
		this.dealDay = dealDay;
	}
	public Integer getCompleteDay() {
		return completeDay;
	}
	public void setCompleteDay(Integer completeDay) {
		this.completeDay = completeDay;
	}
	public Integer getPrePK() {
		return prePK;
	}
	public void setPrePK(Integer prePK) {
		this.prePK = prePK;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public String getIsNeedReq() {
		return isNeedReq;
	}
	public void setIsNeedReq(String isNeedReq) {
		this.isNeedReq = isNeedReq;
	}
	public String getMsgTextTemp2() {
		return msgTextTemp2;
	}
	public void setMsgTextTemp2(String msgTextTemp2) {
		this.msgTextTemp2 = msgTextTemp2;
	}
	public String getWorkType1() {
		return workType1;
	}
	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsAutoJob() {
		return isAutoJob;
	}
	public void setIsAutoJob(String isAutoJob) {
		this.isAutoJob = isAutoJob;
	}
	public String getM_umState() {
		return m_umState;
	}
	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}
	public String getMm_umState() {
		return mm_umState;
	}
	public void setMm_umState(String mm_umState) {
		this.mm_umState = mm_umState;
	}
	public String getPrjItems() {
		return prjItems;
	}
	public void setPrjItems(String prjItems) {
		this.prjItems = prjItems;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public Integer getNowPk() {
		return nowPk;
	}
	public void setNowPk(Integer nowPk) {
		this.nowPk = nowPk;
	}
	public String getRoleDescr() {
		return roleDescr;
	}
	public void setRoleDescr(String roleDescr) {
		this.roleDescr = roleDescr;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getRemindCzyType() {
		return remindCzyType;
	}
	public void setRemindCzyType(String remindCzyType) {
		this.remindCzyType = remindCzyType;
	}
	public String getCzyDescr() {
		return czyDescr;
	}
	public void setCzyDescr(String czyDescr) {
		this.czyDescr = czyDescr;
	}
	public String getOfferWorkType2() {
		return offerWorkType2;
	}
	public void setOfferWorkType2(String offerWorkType2) {
		this.offerWorkType2 = offerWorkType2;
	}
	
}



