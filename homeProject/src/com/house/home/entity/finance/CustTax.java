package com.house.home.entity.finance;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name = "tCustTax")
public class CustTax extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "DocumentNo")
	private String documentNo;
	@Column(name = "PayeeCode")
	private String payeeCode;
	@Column(name = "LaborCompny")
	private String laborCompny;
	@Column(name = "LaborAmount")
	private Double laborAmount;
	@Column(name = "IsPubReturn")
	private String isPubReturn;
	@Column(name = "ReturnAmount")
	private Double returnAmount;
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
	@Column(name = "LaborDate")
	private Date laborDate;
	
	@Transient
	private String oldCustCode;//最初的客户编号
	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	
	// tCustInvoice表信息
	@Transient
	private Date date;// 开票日期
	@Transient
	private String invoiceNo;// 发票编号
	@Transient
	private BigDecimal amount;// 开票金额
	@Transient
	private BigDecimal taxPer;// 税率
	@Transient
	private BigDecimal noTaxAmount;// 不含税金额
	@Transient
	private BigDecimal taxAmount;// 税额
	@Transient
	private String invoiceCode;//开票代码
	@Transient
	private String taxService;//应税服务名称
	@Transient
	private String buyer;//购买方
	@Transient
	private String detailJson;// 批量json转xml
	@Transient
	private Date laborDateFrom;//劳务开票日期从 
	@Transient
	private Date laborDateTo;//劳务开票日期到
	@Transient
	private String isTaxComplete; //是否完成开票
	@Transient
	private String laborJson;// 批量json转xml
	@Transient
	private String unShow;
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getPayeeCode() {
		return payeeCode;
	}
	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}
	public String getLaborCompny() {
		return laborCompny;
	}
	public void setLaborCompny(String laborCompny) {
		this.laborCompny = laborCompny;
	}
	public Double getLaborAmount() {
		return laborAmount;
	}
	public void setLaborAmount(Double laborAmount) {
		this.laborAmount = laborAmount;
	}
	public String getIsPubReturn() {
		return isPubReturn;
	}
	public void setIsPubReturn(String isPubReturn) {
		this.isPubReturn = isPubReturn;
	}
	public Double getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getOldCustCode() {
		return oldCustCode;
	}
	public void setOldCustCode(String oldCustCode) {
		this.oldCustCode = oldCustCode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * @Description:  json转xml,用xml格式传入到存储过程中
	 * @author	created by zb
	 * @date	2018-8-13--下午5:35:19
	 * @return
	 */
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public BigDecimal getTaxPer() {
		return taxPer;
	}
	public void setTaxPer(BigDecimal taxPer) {
		this.taxPer = taxPer;
	}
	public BigDecimal getNoTaxAmount() {
		return noTaxAmount;
	}
	public void setNoTaxAmount(BigDecimal noTaxAmount) {
		this.noTaxAmount = noTaxAmount;
	}
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getTaxService() {
		return taxService;
	}
	public void setTaxService(String taxService) {
		this.taxService = taxService;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public Date getLaborDate() {
		return laborDate;
	}
	public void setLaborDate(Date laborDate) {
		this.laborDate = laborDate;
	}
	public Date getLaborDateFrom() {
		return laborDateFrom;
	}
	public void setLaborDateFrom(Date laborDateFrom) {
		this.laborDateFrom = laborDateFrom;
	}
	public Date getLaborDateTo() {
		return laborDateTo;
	}
	public void setLaborDateTo(Date laborDateTo) {
		this.laborDateTo = laborDateTo;
	}
	public String getIsTaxComplete() {
		return isTaxComplete;
	}
	public void setIsTaxComplete(String isTaxComplete) {
		this.isTaxComplete = isTaxComplete;
	}
	public String getLaborJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(laborJson);
    	return xml;
	}
	public void setLaborJson(String laborJson) {
		this.laborJson = laborJson;
	}
	public String getUnShow() {
		return unShow;
	}
	public void setUnShow(String unShow) {
		this.unShow = unShow;
	}
	
}
