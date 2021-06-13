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
 * SalesInvoice信息
 */
@Entity
@Table(name = "tSalesInvoice")
public class SalesInvoice extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Status")
	private String status;
	@Column(name = "Type")
	private String type;
	@Column(name = "Date")
	private Date date;
	@Column(name = "GetItemDate")
	private Date getItemDate;
	@Column(name = "WHCode")
	private String whcode;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "CustName")
	private String custName;
	@Column(name = "BefAmount")
	private Double befAmount;
	@Column(name = "CalOnDiscAmt")
	private String calOnDiscAmt;
	@Column(name = "DiscPercentage")
	private Double discPercentage;
	@Column(name = "DiscAmount")
	private Double discAmount;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "FirstAmount")
	private Double firstAmount;
	@Column(name = "FirstCash")
	private Double firstCash;
	@Column(name = "SecondAmount")
	private Double secondAmount;
	@Column(name = "SecondCash")
	private Double secondCash;
	@Column(name = "RemainAmount")
	private Double remainAmount;
	@Column(name = "SaleMan")
	private String saleMan;
	@Column(name = "SoftDesign")
	private String softDesign;
	@Column(name = "SaleDirt")
	private String saleDirt;
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
	@Column(name = "IsCal")
	private String isCal;
	@Column(name = "CalNo")
	private String calNo;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "OldNo")
	private String oldNo;
	@Column(name = "OtherAmount")
	private Double otherAmount;
	@Column(name = "IsCheckOut")
	private String isCheckOut;
	@Column(name = "WHCheckOutNo")
	private String whcheckOutNo;
	@Column(name = "CheckSeq")
	private Integer checkSeq;
	@Column(name = "IsAuthorized")
	private String isAuthorized;
	@Column(name = "Authorizer")
	private String authorizer;
	
	@Transient
	private String unSelected;//by zjf
	@Transient
	private String itCode;
	@Transient
	private String itCodeDescr;
	@Transient
	private String detailJson;// 批量json转xml
	@Transient
	private String isEditDiscPercentage;//折扣比率修改权限
	@Transient
	private String chageStatus;//状态修改权限
	@Transient
	private String itemRight;//材料权限
	@Transient
	private String isItemProcess;// 加工材料
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setGetItemDate(Date getItemDate) {
		this.getItemDate = getItemDate;
	}
	
	public Date getGetItemDate() {
		return this.getItemDate;
	}
	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}
	
	public String getWhcode() {
		return this.whcode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getCustName() {
		return this.custName;
	}
	public void setBefAmount(Double befAmount) {
		this.befAmount = befAmount;
	}
	
	public Double getBefAmount() {
		return this.befAmount;
	}
	public void setCalOnDiscAmt(String calOnDiscAmt) {
		this.calOnDiscAmt = calOnDiscAmt;
	}
	
	public String getCalOnDiscAmt() {
		return this.calOnDiscAmt;
	}
	public void setDiscPercentage(Double discPercentage) {
		this.discPercentage = discPercentage;
	}
	
	public Double getDiscPercentage() {
		return this.discPercentage;
	}
	public void setDiscAmount(Double discAmount) {
		this.discAmount = discAmount;
	}
	
	public Double getDiscAmount() {
		return this.discAmount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setFirstAmount(Double firstAmount) {
		this.firstAmount = firstAmount;
	}
	
	public Double getFirstAmount() {
		return this.firstAmount;
	}
	public void setFirstCash(Double firstCash) {
		this.firstCash = firstCash;
	}
	
	public Double getFirstCash() {
		return this.firstCash;
	}
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}
	
	public Double getSecondAmount() {
		return this.secondAmount;
	}
	public void setSecondCash(Double secondCash) {
		this.secondCash = secondCash;
	}
	
	public Double getSecondCash() {
		return this.secondCash;
	}
	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}
	
	public Double getRemainAmount() {
		return this.remainAmount;
	}
	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}
	
	public String getSaleMan() {
		return this.saleMan;
	}
	public void setSoftDesign(String softDesign) {
		this.softDesign = softDesign;
	}
	
	public String getSoftDesign() {
		return this.softDesign;
	}
	public void setSaleDirt(String saleDirt) {
		this.saleDirt = saleDirt;
	}
	
	public String getSaleDirt() {
		return this.saleDirt;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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
	public void setIsCal(String isCal) {
		this.isCal = isCal;
	}
	
	public String getIsCal() {
		return this.isCal;
	}
	public void setCalNo(String calNo) {
		this.calNo = calNo;
	}
	
	public String getCalNo() {
		return this.calNo;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setOldNo(String oldNo) {
		this.oldNo = oldNo;
	}
	
	public String getOldNo() {
		return this.oldNo;
	}
	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}
	
	public Double getOtherAmount() {
		return this.otherAmount;
	}
	public void setIsCheckOut(String isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	
	public String getIsCheckOut() {
		return this.isCheckOut;
	}
	public void setWhcheckOutNo(String whcheckOutNo) {
		this.whcheckOutNo = whcheckOutNo;
	}
	
	public String getWhcheckOutNo() {
		return this.whcheckOutNo;
	}
	public void setCheckSeq(Integer checkSeq) {
		this.checkSeq = checkSeq;
	}
	
	public Integer getCheckSeq() {
		return this.checkSeq;
	}
	public void setIsAuthorized(String isAuthorized) {
		this.isAuthorized = isAuthorized;
	}
	
	public String getIsAuthorized() {
		return this.isAuthorized;
	}
	public void setAuthorizer(String authorizer) {
		this.authorizer = authorizer;
	}
	
	public String getAuthorizer() {
		return this.authorizer;
	}

	public String getUnSelected() {
		return unSelected;
	}

	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}

	public String getItCode() {
		return itCode;
	}

	public void setItCode(String itCode) {
		this.itCode = itCode;
	}

	public String getItCodeDescr() {
		return itCodeDescr;
	}

	public void setItCodeDescr(String itCodeDescr) {
		this.itCodeDescr = itCodeDescr;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getIsEditDiscPercentage() {
		return isEditDiscPercentage;
	}

	public void setIsEditDiscPercentage(String isEditDiscPercentage) {
		this.isEditDiscPercentage = isEditDiscPercentage;
	}

	public String getChageStatus() {
		return chageStatus;
	}

	public void setChageStatus(String chageStatus) {
		this.chageStatus = chageStatus;
	}

	public String getItemRight() {
		return itemRight;
	}

	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}

	public String getIsItemProcess() {
		return isItemProcess;
	}

	public void setIsItemProcess(String isItemProcess) {
		this.isItemProcess = isItemProcess;
	}
	
	
	
}
