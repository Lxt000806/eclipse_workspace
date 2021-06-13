package com.house.home.entity.insales;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

@Entity
@Table(name = "tItemTransferHeader")
public class ItemTransferHeader extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "No")
	private String no;
    @Column(name = "Date")
    private Date date;
    @Column(name = "UseGIT")
    private String useGIT;
    @Column(name = "FromWHCode")
    private String fromWHCode;
    @Column(name = "ToWHCode")
    private String toWHCode;
    @Column(name = "Remarks")
    private String remarks;
    @Column(name = "IsGITDeal")
    private String isGITDeal;
    @Column(name = "DealType")
    private String dealType;
    @Column(name = "DealDate")
    private Date dealDate;
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    @Column(name = "Expired")
    private String expired;
    @Column(name = "ActionLog")
    private String actionLog;
    @Column(name = "Status")
    private String status;
    @Column(name = "AppEmp")
    private String appEmp;
    @Column(name = "AppDate")
    private Date appDate;
    @Column(name = "ConfirmEmp")
    private String confirmEmp;
    @Column(name = "ConfirmDate")
    private Date confirmDate;
    @Column(name = "DocumentNo")
    private String documentNo;
    
    @Transient
    private String itCode;
    @Transient
    private String trfQty;
    @Transient
    private String detailRemarks;
    @Transient 
    private String fromQty;
    @Transient 
    private String toQty;
    @Transient
    private String notOnSale;
    @Transient
	private String detailJson;
    @Transient
    private String confirmCZYDescr;
    
    
    
    
    public String getConfirmCZYDescr() {
		return confirmCZYDescr;
	}
	public void setConfirmCZYDescr(String confirmCZYDescr) {
		this.confirmCZYDescr = confirmCZYDescr;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getNotOnSale() {
		return notOnSale;
	}
	public void setNotOnSale(String notOnSale) {
		this.notOnSale = notOnSale;
	}
	public String getFromQty() {
		return fromQty;
	}
	public void setFromQty(String fromQty) {
		this.fromQty = fromQty;
	}
	public String getToQty() {
		return toQty;
	}
	public void setToQty(String toQty) {
		this.toQty = toQty;
	}
	public String getDetailRemarks() {
		return detailRemarks;
	}
	public void setDetailRemarks(String detailRemarks) {
		this.detailRemarks = detailRemarks;
	}
	public String getTrfQty() {
		return trfQty;
	}
	public void setTrfQty(String trfQty) {
		this.trfQty = trfQty;
	}
	public String getItCode() {
		return itCode;
	}
	public void setItCode(String itCode) {
		this.itCode = itCode;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUseGIT() {
		return useGIT;
	}
	public void setUseGIT(String useGIT) {
		this.useGIT = useGIT;
	}
	public String getFromWHCode() {
		return fromWHCode;
	}
	public void setFromWHCode(String fromWHCode) {
		this.fromWHCode = fromWHCode;
	}
	public String getToWHCode() {
		return toWHCode;
	}
	public void setToWHCode(String toWHCode) {
		this.toWHCode = toWHCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIsGITDeal() {
		return isGITDeal;
	}
	public void setIsGITDeal(String isGITDeal) {
		this.isGITDeal = isGITDeal;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAppEmp() {
		return appEmp;
	}
	public void setAppEmp(String appEmp) {
		this.appEmp = appEmp;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getConfirmEmp() {
		return confirmEmp;
	}
	public void setConfirmEmp(String confirmEmp) {
		this.confirmEmp = confirmEmp;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getItemTransferDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
    
}
