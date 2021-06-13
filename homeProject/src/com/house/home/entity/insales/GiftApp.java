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
 * GiftApp信息
 */
@Entity
@Table(name = "tGiftApp")
public class GiftApp extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "No")
	private String no;
	@Column(name = "Type")
	private String type;
	@Column(name = "Status")
	private String status;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "UseMan")
	private String useMan;
	@Column(name = "Phone")
	private String phone;
	@Column(name = "ActNo")
	private String actNo;
	@Column(name = "SupplCode")
	private String supplCode;
	@Column(name = "Puno")
	private String puNo;
	@Column(name = "WhCode")
	private String whCode;
	@Column(name = "Date")
	private Date date;
	@Column(name = "AppCzy")
	private String appCzy;
	@Column(name = "SendCzy")
	private String sendCzy;
	@Column(name = "SendDate")
	private Date sendDate;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "SendType")
	private String sendType;
	@Column(name = "IsCheckOut")
	private String isCheckOut;
	@Column(name = "checkOutNo")
	private String checkOutNo;
	@Column(name = "CheckSeq")
	private Integer checkSeq;
	@Column(name = "OldNo")
	private String oldNo;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "OutType ")
	private String outType ;
	@Column(name = "Amount")
	private Double amount;
	
	// 供应商接收人
	@Column(name = "SplRcvCZY")
	private String splRcvCzy;
	
	// 供应商接收日期
	@Column(name = "SplRcvDate")
	private Date splRcvDate;
	
	// 供应商备注
	@Column(name = "SplRemark")
	private String splRemark;
	
	// 供应商状态
	@Column(name = "SplStatus")
	private String splStatus;
	
	@Transient
	private String detailJson;
    @Transient 
	private String address;
	@Transient
	private Date setDate;
	@Transient
	private Date signDate;
	@Transient
	private String custDescr;
	@Transient
	private String appCzyDescr;
	@Transient
	private String giftAppDetailJson;
	@Transient
	private String giftStakeholderJson;
	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	@Transient
	private Date sendDateFrom;
	@Transient
	private Date sendDateTo;
	@Transient
	private String m_umState;
	@Transient
	private String  supplDescr;
	@Transient
	private String  whDescr;
	@Transient
	private String  actDescr;
	@Transient
	private String useManDescr;
	@Transient
	private String itemCode;
	@Transient
	private String viewStatus;
	@Transient
	private String splCheckOutNo;	//供应商结算单号
	
	// 客户手机号
	@Transient
	private String custMobile;
	
	
	public String getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	public String getGiftAppDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(giftAppDetailJson);
    	return xml;
	}

	public void setGiftAppDetailJson(String giftAppDetailJson) {
		this.giftAppDetailJson = giftAppDetailJson;
	}

	public String getGiftStakeholderJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(giftStakeholderJson);
		return xml;
	}

	public void setGiftStakeholderJson(String giftStakeholderJson) {
		this.giftStakeholderJson = giftStakeholderJson;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getUseMan() {
		return useMan;
	}

	public void setUseMan(String useMan) {
		this.useMan = useMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getSupplCode() {
		return supplCode;
	}

	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}

    
	public String getPuNo() {
		return puNo;
	}

	public void setPuNo(String puNo) {
		this.puNo = puNo;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAppCzy() {
		return appCzy;
	}

	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}

	public String getSendCzy() {
		return sendCzy;
	}

	public void setSendCzy(String sendCzy) {
		this.sendCzy = sendCzy;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getIsCheckOut() {
		return isCheckOut;
	}

	public void setIsCheckOut(String isCheckOut) {
		this.isCheckOut = isCheckOut;
	}

	public String getCheckOutNo() {
		return checkOutNo;
	}

	public void setCheckOutNo(String checkOutNo) {
		this.checkOutNo = checkOutNo;
	}

	public Integer getCheckSeq() {
		return checkSeq;
	}

	public void setCheckSeq(Integer checkSeq) {
		this.checkSeq = checkSeq;
	}

	public String getOldNo() {
		return oldNo;
	}

	public void setOldNo(String oldNo) {
		this.oldNo = oldNo;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getSetDate() {
		return setDate;
	}

	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCustDescr() {
		return custDescr;
	}

	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}

	public String getAppCzyDescr() {
		return appCzyDescr;
	}

	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}
    
	@Override
	public Date getDateTo() {
		return dateTo;
	}

	@Override
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getSendDateTo() {
		return sendDateTo;
	}

	public void setSendDateTo(Date sendDateTo) {
		this.sendDateTo = sendDateTo;
	}

	@Override
	public Date getDateFrom() {
		return dateFrom;
	}

	@Override
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	
	public Date getSendDateFrom() {
		return sendDateFrom;
	}

	public void setSendDateFrom(Date sendDateFrom) {
		this.sendDateFrom = sendDateFrom;
	}

	@Override
	public String getM_umState() {
		return m_umState;
	}

	@Override
	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}

	public String getSupplDescr() {
		return supplDescr;
	}

	public void setSupplDescr(String supplDescr) {
		this.supplDescr = supplDescr;
	}

	public String getWhDescr() {
		return whDescr;
	}

	public void setWhDescr(String whDescr) {
		this.whDescr = whDescr;
	}

	public String getActDescr() {
		return actDescr;
	}

	public void setActDescr(String actDescr) {
		this.actDescr = actDescr;
	}

	public String getUseManDescr() {
		return useManDescr;
	}

	public void setUseManDescr(String useManDescr) {
		this.useManDescr = useManDescr;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSplCheckOutNo() {
		return splCheckOutNo;
	}

	public void setSplCheckOutNo(String splCheckOutNo) {
		this.splCheckOutNo = splCheckOutNo;
	}
	
   public String getSplRcvCzy() {
        return splRcvCzy;
    }

    public void setSplRcvCzy(String splRcvCzy) {
        this.splRcvCzy = splRcvCzy;
    }

    public Date getSplRcvDate() {
        return splRcvDate;
    }

    public void setSplRcvDate(Date splRcvDate) {
        this.splRcvDate = splRcvDate;
    }

    public String getSplRemark() {
        return splRemark;
    }

    public void setSplRemark(String splRemark) {
        this.splRemark = splRemark;
    }

    public String getSplStatus() {
        return splStatus;
    }

    public void setSplStatus(String splStatus) {
        this.splStatus = splStatus;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }
	
}
