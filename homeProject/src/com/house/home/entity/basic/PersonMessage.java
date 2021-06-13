package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * PersonMessage信息
 */
@Entity
@Table(name = "tPersonMessage")
public class PersonMessage extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "MsgType")
	private String msgType;
	@Column(name = "MsgText")
	private String msgText;
	@Column(name = "MsgRelNo")
	private String msgRelNo;
	@Column(name = "MsgRelCustCode")
	private String msgRelCustCode;
	@Column(name = "CrtDate")
	private Date crtDate;
	
	@Column(name = "SendDate")//触发时间从
	private Date sendDate;
	@Column(name = "RcvType")
	private String rcvType;
	@Column(name = "RcvDate")
	private Date rcvDate;
	@Column(name = "RcvStatus")
	private String rcvStatus;
	@Column(name = "IsPush")
	private String isPush;
	@Column(name = "PushStatus")
	private String pushStatus;

	@Column(name = "ProgmsgType")
	private String progmsgType;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "ItemType2")
	private String itemType2;
	@Column(name = "WorkType12")
	private String workType12;
	@Column(name = "PrjItem")
	private String prjItem;
	@Column(name = "JobType")
	private String jobType;
	@Column(name = "PlanDealDate")
	private Date planDealDate;
	@Column(name = "PlanArrDate")
	private Date planArrDate;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Title")
	private String title;
	
	@Column(name = "DealNo")
	private String dealNo; 
	
	@Transient
	private String ProgmsgTypeDescr;
	@Transient
	private String ItemType1Descr;
	@Transient
	private String ItemType2Descr;
	@Transient
	private String WorkType12Descr;
	@Transient
	private String PrjItemDescr;
	@Transient
	private String JobTypeDescr;
	@Transient
	private String  Deal;   //是否超时
	@Transient
	private Date  sendDate1;//触发时间至 
	@Transient
	private String  Address;//楼盘
	@Transient
	private String  Department2;//二级部门
	@Transient
	private String  department1;//二级部门
	@Transient
	private String  Department2Descr;//二级部门
	@Transient
	private String RcvCzyDescr;//执行人
	@Transient 
	private String progMsgTypeDescr;
	@Transient 
	private String bMsgType;
	@Transient 
	private String bRcvType;
	@Transient 
	private String bRcvCZY;
	@Transient 
	private String bRcvStatus;
	@Transient 
	private String bIsPush;
	@Transient
	private String bPushStatus;
	@Transient
	private Date  sendDateFrom;
	@Transient
	private Date  sendDateTo;
	@Transient
	private Date  crtDateFrom;
	@Transient
	private Date  crtDateTo;
	@Transient  
	private String timeoutFlag;
	@Transient
	private String prjStatus;
	
	@Column(name = "RcvCzy")
	private String rcvCzy;
	@Transient  
	private String prjDept;
	@Transient  
	private String authId;
	@Transient  
	private String delayRemarks;
	@Transient  
	private String lastUpdatedBy;
	@Transient
	private String sysTrigger;
	@Transient
	private String befTaskCompleted;
	@Transient
	private String custType;
	
	public String getBefTaskCompleted() {
		return befTaskCompleted;
	}

	public void setBefTaskCompleted(String befTaskCompleted) {
		this.befTaskCompleted = befTaskCompleted;
	}

	public String getSysTrigger() {
		return sysTrigger;
	}

	public void setSysTrigger(String sysTrigger) {
		this.sysTrigger = sysTrigger;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public String getMsgType() {
		return this.msgType;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	
	public String getMsgText() {
		return this.msgText;
	}
	public void setMsgRelNo(String msgRelNo) {
		this.msgRelNo = msgRelNo;
	}
	
	public String getMsgRelNo() {
		return this.msgRelNo;
	}
	public void setMsgRelCustCode(String msgRelCustCode) {
		this.msgRelCustCode = msgRelCustCode;
	}
	
	public String getMsgRelCustCode() {
		return this.msgRelCustCode;
	}

	
	
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	public Date getSendDate() {
		return this.sendDate;
	}
	public void setRcvType(String rcvType) {
		this.rcvType = rcvType;
	}
	
	public String getRcvType() {
		return this.rcvType;
	}
	
	public void setRcvDate(Date rcvDate) {
		this.rcvDate = rcvDate;
	}
	
	public Date getRcvDate() {
		return this.rcvDate;
	}

	public String getRcvStatus() {
		return rcvStatus;
	}

	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
	}

	public String getIsPush() {
		return isPush;
	}

	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getProgmsgType() {
		return progmsgType;
	}

	public void setProgmsgType(String progmsgType) {
		this.progmsgType = progmsgType;
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

	public String getWorkType12() {
		return workType12;
	}

	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}

	public String getPrjItem() {
		return prjItem;
	}

	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Date getPlanDealDate() {
		return planDealDate;
	}

	public void setPlanDealDate(Date planDealDate) {
		this.planDealDate = planDealDate;
	}

	public Date getPlanArrDate() {
		return planArrDate;
	}

	public void setPlanArrDate(Date planArrDate) {
		this.planArrDate = planArrDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProgmsgTypeDescr() {
		return ProgmsgTypeDescr;
	}

	public void setProgmsgTypeDescr(String progmsgTypeDescr) {
		ProgmsgTypeDescr = progmsgTypeDescr;
	}

	public String getItemType1Descr() {
		return ItemType1Descr;
	}

	public void setItemType1Descr(String itemType1Descr) {
		ItemType1Descr = itemType1Descr;
	}

	public String getItemType2Descr() {
		return ItemType2Descr;
	}

	public void setItemType2Descr(String itemType2Descr) {
		ItemType2Descr = itemType2Descr;
	}

	public String getWorkType12Descr() {
		return WorkType12Descr;
	}

	public void setWorkType12Descr(String workType12Descr) {
		WorkType12Descr = workType12Descr;
	}

	public String getPrjItemDescr() {
		return PrjItemDescr;
	}

	public void setPrjItemDescr(String prjItemDescr) {
		PrjItemDescr = prjItemDescr;
	}

	public String getJobTypeDescr() {
		return JobTypeDescr;
	}

	public void setJobTypeDescr(String jobTypeDescr) {
		JobTypeDescr = jobTypeDescr;
	}

	public String getDeal() {
		return Deal;
	}

	public void setDeal(String deal) {
		Deal = deal;
	}

	
	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getDepartment2() {
		return Department2;
	}

	public void setDepartment2(String department2) {
		Department2 = department2;
	}

	public String getDepartment2Descr() {
		return Department2Descr;
	}

	public void setDepartment2Descr(String department2Descr) {
		Department2Descr = department2Descr;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getRcvCzyDescr() {
		return RcvCzyDescr;
	}

	public void setRcvCzyDescr(String rcvCzyDescr) {
		RcvCzyDescr = rcvCzyDescr;
	}

	public String getTimeoutFlag() {
		return timeoutFlag;
	}

	public void setTimeoutFlag(String timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
	}

	public Date getSendDate1() {
		return sendDate1;
	}

	public void setSendDate1(Date sendDate1) {
		this.sendDate1 = sendDate1;
	}

	public String getDealNo() {
		return dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public String getbRcvType() {
		return bRcvType;
	}

	public void setbRcvType(String bRcvType) {
		this.bRcvType = bRcvType;
	}

	public String getProgMsgTypeDescr() {
		return progMsgTypeDescr;
	}

	public void setProgMsgTypeDescr(String progMsgTypeDescr) {
		this.progMsgTypeDescr = progMsgTypeDescr;
	}

	public String getbMsgType() {
		return bMsgType;
	}

	public void setbMsgType(String bMsgType) {
		this.bMsgType = bMsgType;
	}

	public String getbRcvCZY() {
		return bRcvCZY;
	}

	public void setbRcvCZY(String bRcvCZY) {
		this.bRcvCZY = bRcvCZY;
	}

	public String getbRcvStatus() {
		return bRcvStatus;
	}

	public void setbRcvStatus(String bRcvStatus) {
		this.bRcvStatus = bRcvStatus;
	}

	public String getbIsPush() {
		return bIsPush;
	}

	public void setbIsPush(String bIsPush) {
		this.bIsPush = bIsPush;
	}

	public String getbPushStatus() {
		return bPushStatus;
	}

	public void setbPushStatus(String bPushStatus) {
		this.bPushStatus = bPushStatus;
	}

	public Date getSendDateFrom() {
		return sendDateFrom;
	}

	public void setSendDateFrom(Date sendDateFrom) {
		this.sendDateFrom = sendDateFrom;
	}

	public Date getSendDateTo() {
		return sendDateTo;
	}

	public void setSendDateTo(Date sendDateTo) {
		this.sendDateTo = sendDateTo;
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

	public String getRcvCzy() {
		return rcvCzy;
	}

	public void setRcvCzy(String rcvCzy) {
		this.rcvCzy = rcvCzy;
	}

	public String getPrjStatus() {
		return prjStatus;
	}

	public void setPrjStatus(String prjStatus) {
		this.prjStatus = prjStatus;
	}

	public String getPrjDept() {
		return prjDept;
	}

	public void setPrjDept(String prjDept) {
		this.prjDept = prjDept;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getDelayRemarks() {
		return delayRemarks;
	}

	public void setDelayRemarks(String delayRemarks) {
		this.delayRemarks = delayRemarks;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}
	
}
