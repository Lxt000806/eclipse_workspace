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

/**
 * CustPayTran信息
 */
@Entity
@Table(name = "tCustPayTran")
public class CustPayTran extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "AppCzy")
	private String appCzy;
	@Column(name = "Type")
	private String type;
	@Column(name = "Date")
	private Date date;
	@Column(name = "TranAmount")
	private Double tranAmount;
	@Column(name = "PayAmount")
	private Double payAmount;
	@Column(name = "rcvAct")
	private String rcvAct;
	@Column(name = "posCode")
	private String posCode;
	@Column(name = "ProcedureFee")
	private Double procedureFee;
	@Column(name = "AddDate")
	private Date addDate;
	@Column(name = "PayNo")
	private String payNo;
	@Column(name = "CardID")
	private String cardId;
	@Column(name = "BankCode")
	private String bankCode;
	@Column(name = "BankName")
	private String bankName;
	@Column(name = "TraceNo")
	private String traceNo;
	@Column(name = "ReferNo")
	private String referNo;
	@Column(name = "IsSign")
	private String isSign;
	@Column(name = "PrintCZY")
	private String printCZY;
	@Column(name = "PrintDate")
	private Date printDate;
	@Column(name = "PrintTimes")
	private Integer printTimes;
	@Column(name = "CustPayPK")
	private Integer custPayPk;
	@Column(name = "ExceptionRemarks")
	private String exceptionRemarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "CardAttr")
	private String cardAttr;//卡性质
	@Column(name = "ReprintRemarks")
	private String reprintRemarks;//重打说明
	@Column(name = "PayType")
	private String payType;//重打说明
	
	@Transient
	private String PosId;
	@Transient
	private String address;
	@Transient
	private String cmpCode;
	
	// 收款单位编号
	@Transient
	private String payeeCode;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
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
	public String getAppCzy() {
		return appCzy;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(Double tranAmount) {
		this.tranAmount = tranAmount;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public String getRcvAct() {
		return rcvAct;
	}
	public void setRcvAct(String rcvAct) {
		this.rcvAct = rcvAct;
	}
	public String getPosCode() {
		return posCode;
	}
	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}
	public Double getProcedureFee() {
		return procedureFee;
	}
	public void setProcedureFee(Double procedureFee) {
		this.procedureFee = procedureFee;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getTraceNo() {
		return traceNo;
	}
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
	public String getReferNo() {
		return referNo;
	}
	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
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
	public Integer getPrintTimes() {
		return printTimes;
	}
	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}
	public Integer getCustPayPk() {
		return custPayPk;
	}
	public void setCustPayPk(Integer custPayPk) {
		this.custPayPk = custPayPk;
	}
	public String getExceptionRemarks() {
		return exceptionRemarks;
	}
	public void setExceptionRemarks(String exceptionRemarks) {
		this.exceptionRemarks = exceptionRemarks;
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
	public String getPosId() {
		return PosId;
	}
	public void setPosId(String posId) {
		PosId = posId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCardAttr() {
		return cardAttr;
	}
	public void setCardAttr(String cardAttr) {
		this.cardAttr = cardAttr;
	}
	public String getReprintRemarks() {
		return reprintRemarks;
	}
	public void setReprintRemarks(String reprintRemarks) {
		this.reprintRemarks = reprintRemarks;
	}
	public String getCmpCode() {
		return cmpCode;
	}
	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
    public String getPayeeCode() {
        return payeeCode;
    }
    public void setPayeeCode(String payeeCode) {
        this.payeeCode = payeeCode;
    }
	
}
