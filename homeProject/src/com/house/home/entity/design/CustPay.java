package com.house.home.entity.design;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * CustPay信息
 */
@Entity
@Table(name = "tCustPay")
public class CustPay extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Date")
	private Date date;
	@Column(name = "Amount")
	private Double amount;
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
	@Column(name = "IsCheckOut")
	private String isCheckOut;
	@Column(name = "PayCheckOutNo")
	private String payCheckOutNo;
	@Column(name = "CheckSeq")
	private Integer checkSeq;
	@Column(name = "RcvAct")
	private String rcvAct;
	@Column(name = "PosCode")
	private String posCode;
	@Column(name = "ProcedureFee")
	private Double procedureFee;
	@Column(name = "AddDate")
	private Date addDate;
	@Column(name = "PayNo")
	private String payNo;
	@Column(name = "Type")
	private String type;
	@Column(name = "WfProcInstNo")
	private String wfProcInstNo;
	@Column(name = "DiscTokenNo")
	private String discTokenNo;
	@Column(name = "PrintCZY")
	private String printCZY;
	@Column(name = "PrintDate")
	private Date printDate;
	
    @Transient
	private Date lastUpdateFrom;
	@Transient
	private Date lastUpdateTo;
	@Transient
	private Date addDateFrom;
	@Transient
	private Date addDateTo;
	@Transient
	private String address;
	@Transient
	private String detailJson;
	@Transient
	private String CustType;
	@Transient
	private String payDate;
	@Transient
	private String receiveActName;
	@Transient
	private Double payAmount;
	@Transient
	private String payActName;
	@Transient
	private String flag;
	
	/**
	 * 收入记账状态
	 */
	@Transient
	private String payCheckoutStatus;
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getReceiveActName() {
		return receiveActName;
	}

	public void setReceiveActName(String receiveActName) {
		this.receiveActName = receiveActName;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayActName() {
		return payActName;
	}

	public void setPayActName(String payActName) {
		this.payActName = payActName;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
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
	public void setIsCheckOut(String isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	
	public String getIsCheckOut() {
		return this.isCheckOut;
	}
	public void setPayCheckOutNo(String payCheckOutNo) {
		this.payCheckOutNo = payCheckOutNo;
	}
	
	public String getPayCheckOutNo() {
		return this.payCheckOutNo;
	}
	public void setCheckSeq(Integer checkSeq) {
		this.checkSeq = checkSeq;
	}
	
	public Integer getCheckSeq() {
		return this.checkSeq;
	}
	public void setRcvAct(String rcvAct) {
		this.rcvAct = rcvAct;
	}
	
	public String getRcvAct() {
		return this.rcvAct;
	}
	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}
	
	public String getPosCode() {
		return this.posCode;
	}
	public void setProcedureFee(Double procedureFee) {
		this.procedureFee = procedureFee;
	}
	
	public Double getProcedureFee() {
		return this.procedureFee;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	public Date getAddDate() {
		return this.addDate;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	
	public String getPayNo() {
		return this.payNo;
	}

	public Date getLastUpdateFrom() {
		return lastUpdateFrom;
	}

	public void setLastUpdateFrom(Date lastUpdateFrom) {
		this.lastUpdateFrom = lastUpdateFrom;
	}

	public Date getLastUpdateTo() {
		return lastUpdateTo;
	}

	public void setLastUpdateTo(Date lastUpdateTo) {
		this.lastUpdateTo = lastUpdateTo;
	}

	public Date getAddDateFrom() {
		return addDateFrom;
	}

	public void setAddDateFrom(Date addDateFrom) {
		this.addDateFrom = addDateFrom;
	}

	public Date getAddDateTo() {
		return addDateTo;
	}

	public void setAddDateTo(Date addDateTo) {
		this.addDateTo = addDateTo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getCustType() {
		return CustType;
	}

	public void setCustType(String custType) {
		CustType = custType;
	}

	public String getDiscTokenNo() {
		return discTokenNo;
	}

	public void setDiscTokenNo(String discTokenNo) {
		this.discTokenNo = discTokenNo;
	}

	public String getPrintCZY() {
		return printCZY;
	}

	public void setPrintCZY(String printCZY) {
		this.printCZY = printCZY;
	}

    public Date getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }

    public String getWfProcInstNo() {
        return wfProcInstNo;
    }

    public void setWfProcInstNo(String wfProcInstNo) {
        this.wfProcInstNo = wfProcInstNo;
    }

    public String getPayCheckoutStatus() {
        return payCheckoutStatus;
    }

    public void setPayCheckoutStatus(String payCheckoutStatus) {
        this.payCheckoutStatus = payCheckoutStatus;
    }
	
}
