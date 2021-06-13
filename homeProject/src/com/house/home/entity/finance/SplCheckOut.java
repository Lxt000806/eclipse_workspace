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
 * SplCheckOut信息
 */
@Entity
@Table(name = "tSplCheckOut")
public class SplCheckOut extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "SplCode")
	private String splCode;
	@Column(name = "Date")
	private Date date;
	@Column(name = "PayType")
	private String payType;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "PayAmount")
	private Double payAmount;
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
	@Column(name = "Remark")
	private String remark;
	@Column(name = "OtherCost")
	private Double otherCost;
	@Column(name = "Status")
	private String status;
	@Column(name = "ConfirmCzy")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "DocumentNo")
	private String documentNo;
	@Column(name = "PayCzy")
	private String payCzy;
	@Column(name = "PayDate")
	private Date payDate;
	@Column(name = "PaidAmount")
	private Double paidAmount;
	@Column(name = "NowAmount")
	private Double nowAmount;
	@Column(name = "PreAmount")
	private Double preAmount;
	@Column(name = "AppCZY")
	private String appCZY;
	
	@Transient
	private String splCodeDescr; 
	@Transient
	private String detailJson;
	
	@Transient
	private String custCode;
	@Transient
	private String itemRight;
	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	@Transient
	private String onlyUnCheck;
	
	@Transient
	private String nos;
	@Transient
	private String checkOutNo;
	@Transient
	private String isNormal;
	@Transient
	private String address;
	@Transient
	private String purchType;
	
	@Transient
	private String selectAll;
	@Transient
	private String delStatus; //需要删除的状态
	@Transient
	private String disabledEle; //在选择code窗口disabled查询组件
	@Transient
	private String cmpCode; 
	
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private String itemType1;
	@Transient
	private String isSetItem;
	@Transient
	private String isIncludePurchIn;
	
	@Transient
	private Integer isService;
	@Transient
	private String wfProcInstNo;
	
	public String getWfProcInstNo() {
		return wfProcInstNo;
	}

	public void setWfProcInstNo(String wfProcInstNo) {
		this.wfProcInstNo = wfProcInstNo;
	}

	public String getDisabledEle() {
		return disabledEle;
	}

	public void setDisabledEle(String disabledEle) {
		this.disabledEle = disabledEle;
	}

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setSplCode(String splCode) {
		this.splCode = splCode;
	}
	
	public String getSplCode() {
		return this.splCode;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public String getPayType() {
		return this.payType;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getBeginDate() {
		return this.beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	
	public Double getPayAmount() {
		return this.payAmount;
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
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}
	
	public Double getOtherCost() {
		return this.otherCost;
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
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	
	public Double getPaidAmount() {
		return this.paidAmount;
	}
	public void setNowAmount(Double nowAmount) {
		this.nowAmount = nowAmount;
	}
	
	public Double getNowAmount() {
		return this.nowAmount;
	}
	public void setPreAmount(Double preAmount) {
		this.preAmount = preAmount;
	}
	
	public Double getPreAmount() {
		return this.preAmount;
	}

	public String getSplCodeDescr() {
		return splCodeDescr;
	}

	public void setSplCodeDescr(String splCodeDescr) {
		this.splCodeDescr = splCodeDescr;
	}

	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getAppCZY() {
		return appCZY;
	}

	public void setAppCZY(String appCZY) {
		this.appCZY = appCZY;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getItemRight() {
		return itemRight;
	}

	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
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

	public String getOnlyUnCheck() {
		return onlyUnCheck;
	}

	public void setOnlyUnCheck(String onlyUnCheck) {
		this.onlyUnCheck = onlyUnCheck;
	}

	public String getNos() {
		return nos;
	}

	public void setNos(String nos) {
		this.nos = nos;
	}

	public String getCheckOutNo() {
		return checkOutNo;
	}

	public void setCheckOutNo(String checkOutNo) {
		this.checkOutNo = checkOutNo;
	}

	public String getIsNormal() {
		return isNormal;
	}

	public void setIsNormal(String isNormal) {
		this.isNormal = isNormal;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPurchType() {
		return purchType;
	}

	public void setPurchType(String purchType) {
		this.purchType = purchType;
	}

	public String getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(String selectAll) {
		this.selectAll = selectAll;
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

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getCmpCode() {
		return cmpCode;
	}

	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}

	public String getIsSetItem() {
		return isSetItem;
	}

	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}

	public String getIsIncludePurchIn() {
		return isIncludePurchIn;
	}

	public void setIsIncludePurchIn(String isIncludePurchIn) {
		this.isIncludePurchIn = isIncludePurchIn;
	}

    public Integer getIsService() {
        return isService;
    }

    public void setIsService(Integer isService) {
        this.isService = isService;
    }
	
}
