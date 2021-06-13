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
@Table(name = "tReturnPay")
public class ReturnPay extends BaseEntity{

	private static final long serialVersionUID = 1L;
@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Status")
	private String status;
	@Column(name = "DocumentNo")
	private String documentNo;
	@Column(name = "CheckDate")
	private Date checkDate;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "AppCZY")
	private String appCZY;
	@Column(name = "Date")
	private Date date;
	@Column(name = "ConfirmCZY")
	private String confirmCZY;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Column(name = "Type")
	private String type;
	
	@Transient
	private Date appDateFrom;
	@Transient
	private Date appDateTo;
	@Transient
	private String custCode;
	@Transient
	private String appEmp;
	@Transient
	private String address;
	@Transient
	private String amount;
	@Transient
	private String descr;
	@Transient
	private String detailJson;
	@Transient
	private String statusDescr;
	@Transient
	private String endDescr;
	@Transient
	private String isPubReturnDescr;
	@Transient
	private double returnAmount;
	@Transient 
	private String appCzyDescr;
	@Transient 
	private String confirmCzyDescr;
	@Transient 
	private String custCheckStatus;

	@Transient
	private String hasCustCode;
	@Transient
	private Integer refSupplPrepayPK;
	@Transient
	private String hasRefSupplPrepayPK;
	@Transient
	private String refSupplPrepayNo;
	@Transient
	private String splDescr;
	@Transient
	private double thisAmount;
	
	@Transient
	private String excludedCusts;

    @Transient
	private Date checkoutDateFrom;

    @Transient
	private Date checkoutDateTo;
	
    public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
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
	
	
	public Date getAppDateFrom() {
		return appDateFrom;
	}
	public void setAppDateFrom(Date appDateFrom) {
		this.appDateFrom = appDateFrom;
	}
	public Date getAppDateTo() {
		return appDateTo;
	}
	public void setAppDateTo(Date appDateTo) {
		this.appDateTo = appDateTo;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getAppEmp() {
		return appEmp;
	}
	public void setAppEmp(String appEmp) {
		this.appEmp = appEmp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getEndDescr() {
		return endDescr;
	}
	public void setEndDescr(String endDescr) {
		this.endDescr = endDescr;
	}
	public String getIsPubReturnDescr() {
		return isPubReturnDescr;
	}
	public void setIsPubReturnDescr(String isPubReturnDescr) {
		this.isPubReturnDescr = isPubReturnDescr;
	}
	public double getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
	}
	public String getAppCzyDescr() {
		return appCzyDescr;
	}
	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}
	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}
	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}
	public String getCustCheckStatus() {
		return custCheckStatus;
	}
	public void setCustCheckStatus(String custCheckStatus) {
		this.custCheckStatus = custCheckStatus;
	}
	public String getHasCustCode() {
		return hasCustCode;
	}
	public void setHasCustCode(String hasCustCode) {
		this.hasCustCode = hasCustCode;
	}
	public Integer getRefSupplPrepayPK() {
		return refSupplPrepayPK;
	}
	public void setRefSupplPrepayPK(Integer refSupplPrepayPK) {
		this.refSupplPrepayPK = refSupplPrepayPK;
	}
	public String getHasRefSupplPrepayPK() {
		return hasRefSupplPrepayPK;
	}
	public void setHasRefSupplPrepayPK(String hasRefSupplPrepayPK) {
		this.hasRefSupplPrepayPK = hasRefSupplPrepayPK;
	}
	public String getRefSupplPrepayNo() {
		return refSupplPrepayNo;
	}
	public void setRefSupplPrepayNo(String refSupplPrepayNo) {
		this.refSupplPrepayNo = refSupplPrepayNo;
	}
	public String getSplDescr() {
		return splDescr;
	}
	public void setSplDescr(String splDescr) {
		this.splDescr = splDescr;
	}
	public double getThisAmount() {
		return thisAmount;
	}
	public void setThisAmount(double thisAmount) {
		this.thisAmount = thisAmount;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
	
    public String getExcludedCusts() {
        return excludedCusts;
    }

    public void setExcludedCusts(String excludedCusts) {
        this.excludedCusts = excludedCusts;
    }
    
    public Date getCheckoutDateFrom() {
        return checkoutDateFrom;
    }

    public void setCheckoutDateFrom(Date checkoutDateFrom) {
        this.checkoutDateFrom = checkoutDateFrom;
    }

    public Date getCheckoutDateTo() {
        return checkoutDateTo;
    }

    public void setCheckoutDateTo(Date checkoutDateTo) {
        this.checkoutDateTo = checkoutDateTo;
    }
    
}
