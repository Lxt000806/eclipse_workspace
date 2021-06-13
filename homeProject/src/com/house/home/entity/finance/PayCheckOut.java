package com.house.home.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * 收入记账
 * 
 * @author yzj
 */
@Entity
@Table(name = "tPayCheckOut")
public class PayCheckOut extends BaseDao{

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
    @Column(name = "LastUpdateBy")
    private String lastUpdateBy;
    @Column(name = "Expired")
    private String expired;
    @Column(name = "ActionLog")
    private String actionLog;
    @Column(name = "ReturnPayNo")
    private String returnPayNo;
    
    @Transient
    private String custCode;
    @Transient
    private Date dateFrom;
    @Transient
    private Date dateTo;
    @Transient
    private Date checkDateFrom;
    @Transient
    private Date checkDateTo;
    @Transient
    private String appCZYDescr;
    @Transient
    private String confirmCZYDescr;
    @Transient
    private String allDetailInfo;
    @Transient
    private String detailJson;
    @Transient
    private String  m_umState;
    @Transient
    private String  custStatus;
    @Transient
    private String cmpCode;//公司编号
    
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
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
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
    public String getReturnPayNo() {
        return returnPayNo;
    }
    public void setReturnPayNo(String returnPayNo) {
        this.returnPayNo = returnPayNo;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    
    public String getCustCode() {
        return custCode;
    }
    public void setCustCode(String custCode) {
        this.custCode = custCode;
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
    public Date getCheckDateFrom() {
        return checkDateFrom;
    }
    public void setCheckDateFrom(Date checkDateFrom) {
        this.checkDateFrom = checkDateFrom;
    }
    public Date getCheckDateTo() {
        return checkDateTo;
    }
    public void setCheckDateTo(Date checkDateTo) {
        this.checkDateTo = checkDateTo;
    }
    public String getAppCZYDescr() {
        return appCZYDescr;
    }
    public void setAppCZYDescr(String appCZYDescr) {
        this.appCZYDescr = appCZYDescr;
    }
    public String getConfirmCZYDescr() {
        return confirmCZYDescr;
    }
    public void setConfirmCZYDescr(String confirmCZYDescr) {
        this.confirmCZYDescr = confirmCZYDescr;
    }
    public String getAllDetailInfo() {
        return allDetailInfo;
    }
    public void setAllDetailInfo(String allDetailInfo) {
        this.allDetailInfo = allDetailInfo;
    }
    public String getDetailJson() {
        return detailJson;
    }
    public void setDetailJson(String detailJson) {
        this.detailJson = detailJson;
    }
    public String getPayAppDetailXml(){
        String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
        return xml;
    }
    public String getM_umState() {
        return m_umState;
    }
    public void setM_umState(String m_umState) {
        this.m_umState = m_umState;
    }
	public String getCustStatus() {
		return custStatus;
	}
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	public String getCmpCode() {
		return cmpCode;
	}
	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}
    
}
