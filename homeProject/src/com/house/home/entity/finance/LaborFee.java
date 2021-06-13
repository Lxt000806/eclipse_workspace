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
@Table(name = "tLaborFee")
public class LaborFee extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "No")
	private String no;
	@Column(name="ItemType1")
	private String itemType1;
	@Column(name="Status")
	private String status;
	@Column(name="ActName")
	private String actName;
	@Column(name="CardID")
	private String cardID;
	@Column(name="Remarks")
	private String remarks;
	@Column(name="AppCZY")
	private String appCZY;
	@Column(name="Date")
	private Date date;
	@Column(name="ConfirmCZY")
	private String confirmCZY;
	@Column(name="ConfirmDate")
	private Date confirmDate;
	@Column(name="PayCZY")
	private String payCZY ;
	@Column(name="PayDate")
	private Date payDate;
	@Column(name="DocumentNo")
	private String documentNo;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="bank")
	private String bank;
	
	@Transient
	private String itemType1Descr;
	@Transient
	private String custCode;
	@Transient
	private String detailRemarks;
	@Transient
	private String sendNoHaveAmount;
	@Transient
	private String haveAmount;
	@Transient
	private String amount;
	@Transient
	private String feeType;
	@Transient 
	private String appSendNo;
	@Transient
	private String address;
	@Transient
	private String detailJson;
	@Transient
	private String accountDetailJson;
	@Transient
	private String checkStatus;
	@Transient 
	private Date checkDate;
	@Transient 
	private Date confirmDateFrom;
	@Transient 
	private Date confirmDateTo;
	@Transient
	private String iaNo;
	@Transient
	private Date payDateFrom;
	@Transient
	private Date payDateTo;
	@Transient
	private String itemType2;
	@Transient 
	private String isSetItem;
	@Transient 
	private String driver;
	@Transient 
	private String nos;
	@Transient
	private String workerCode;
	@Transient
	private String custCodes;
	@Transient
	private String itemRight;
	@Transient
	private String toilet;//马桶材料类型3
	@Transient
	private String cabinet;//浴室柜材料类型3
	@Transient
	private String bathSet;//卫浴套餐材料类型3 
	@Transient
	private String faultType;
	@Transient
	private String faultMan;
	@Transient
	private String refCustCode;
	
	@Transient
	private String refCustAddress;
	
	@Transient
	private String faultTypeDescr;
	@Transient
	private String faultManDescr;
	@Transient
	private Double prjQualityFee;
	@Transient
	private String refCustDescr;
	@Transient
	private String projectMan;
	@Transient
	private String projectManDescr;
	@Transient
	private String isNotApp;
	@Transient
	private String pks;
	@Transient 
	private String wfProcInstNo;
	@Transient
	private String actProcDefId;
	/**
	 * 客户档案编号，注意区分凭证号
	 */
	@Transient
	private String custDocumentNo;
	@Transient
	private String regionDescr;
	@Transient
	private String jcazbtCustCodes;
	@Transient
	private String jcazjfCustCodes;
	@Transient
	private String cgazbtCustCodes;
	@Transient
	private String cgazjfCustCodes;
	
	public String getActProcDefId() {
		return actProcDefId;
	}
	public void setActProcDefId(String actProcDefId) {
		this.actProcDefId = actProcDefId;
	}
	public String getWfProcInstNo() {
		return wfProcInstNo;
	}
	
	public void setWfProcInstNo(String wfProcInstNo) {
		this.wfProcInstNo = wfProcInstNo;
	}
	public String getAccountDetailJson() {
		return accountDetailJson;
	}
	public void setAccountDetailJson(String accountDetailJson) {
		this.accountDetailJson = accountDetailJson;
	}
	public String getPks() {
		return pks;
	}
	public void setPks(String pks) {
		this.pks = pks;
	}
	public String getItemRight() {
		return itemRight;
	}
	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}
	public String getCustCodes() {
		return custCodes;
	}
	public void setCustCodes(String custCodes) {
		this.custCodes = custCodes;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getIsSetItem() {
		return isSetItem;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
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
	public String getIaNo() {
		return iaNo;
	}
	public void setIaNo(String iaNo) {
		this.iaNo = iaNo;
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
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getDetailRemarks() {
		return detailRemarks;
	}
	public void setDetailRemarks(String detailRemarks) {
		this.detailRemarks = detailRemarks;
	}
	public String getSendNoHaveAmount() {
		return sendNoHaveAmount;
	}
	public void setSendNoHaveAmount(String sendNoHaveAmount) {
		this.sendNoHaveAmount = sendNoHaveAmount;
	}
	public String getHaveAmount() {
		return haveAmount;
	}
	public void setHaveAmount(String haveAmount) {
		this.haveAmount = haveAmount;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getAppSendNo() {
		return appSendNo;
	}
	public void setAppSendNo(String appSendNo) {
		this.appSendNo = appSendNo;
	}
	public String getItemType1Descr() {
		return itemType1Descr;
	}
	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAppCZY() {
		return appCZY;
	}
	public void setAppCZY(String appCZY) {
		this.appCZY = appCZY;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getConfirmCZY() {
		return confirmCZY;
	}
	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getPayCZY() {
		return payCZY;
	}
	public void setPayCZY(String payCZY) {
		this.payCZY = payCZY;
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
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getNos() {
		return nos;
	}
	public void setNos(String nos) {
		this.nos = nos;
	}
	public String getLaborFeeDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getLaborFeeAccountDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(accountDetailJson);
		return xml;
	}
	public String getToilet() {
		return toilet;
	}
	public void setToilet(String toilet) {
		this.toilet = toilet;
	}
	public String getCabinet() {
		return cabinet;
	}
	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
	}
	public String getBathSet() {
		return bathSet;
	}
	public void setBathSet(String bathSet) {
		this.bathSet = bathSet;
	}
	public String getFaultType() {
		return faultType;
	}
	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}
	public String getFaultMan() {
		return faultMan;
	}
	public void setFaultMan(String faultMan) {
		this.faultMan = faultMan;
	}
	public String getRefCustCode() {
		return refCustCode;
	}
	public void setRefCustCode(String refCustCode) {
		this.refCustCode = refCustCode;
	}
	public String getFaultTypeDescr() {
		return faultTypeDescr;
	}
	public void setFaultTypeDescr(String faultTypeDescr) {
		this.faultTypeDescr = faultTypeDescr;
	}
	public String getFaultManDescr() {
		return faultManDescr;
	}
	public void setFaultManDescr(String faultManDescr) {
		this.faultManDescr = faultManDescr;
	}
	public Double getPrjQualityFee() {
		return prjQualityFee;
	}
	public void setPrjQualityFee(Double prjQualityFee) {
		this.prjQualityFee = prjQualityFee;
	}
	public String getRefCustDescr() {
		return refCustDescr;
	}
	public void setRefCustDescr(String refCustDescr) {
		this.refCustDescr = refCustDescr;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getProjectManDescr() {
		return projectManDescr;
	}
	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}
	public String getIsNotApp() {
		return isNotApp;
	}
	public void setIsNotApp(String isNotApp) {
		this.isNotApp = isNotApp;
	}
    public String getCustDocumentNo() {
        return custDocumentNo;
    }
    public void setCustDocumentNo(String custDocumentNo) {
        this.custDocumentNo = custDocumentNo;
    }
    public String getRegionDescr() {
        return regionDescr;
    }
    public void setRegionDescr(String regionDescr) {
        this.regionDescr = regionDescr;
    }
	public String getJcazbtCustCodes() {
		return jcazbtCustCodes;
	}
	public void setJcazbtCustCodes(String jcazbtCustCodes) {
		this.jcazbtCustCodes = jcazbtCustCodes;
	}
	public String getJcazjfCustCodes() {
		return jcazjfCustCodes;
	}
	public void setJcazjfCustCodes(String jcazjfCustCodes) {
		this.jcazjfCustCodes = jcazjfCustCodes;
	}
	public String getCgazbtCustCodes() {
		return cgazbtCustCodes;
	}
	public void setCgazbtCustCodes(String cgazbtCustCodes) {
		this.cgazbtCustCodes = cgazbtCustCodes;
	}
	public String getCgazjfCustCodes() {
		return cgazjfCustCodes;
	}
	public void setCgazjfCustCodes(String cgazjfCustCodes) {
		this.cgazjfCustCodes = cgazjfCustCodes;
	}

    public String getRefCustAddress() {
        return refCustAddress;
    }

    public void setRefCustAddress(String refCustAddress) {
        this.refCustAddress = refCustAddress;
    }

}
