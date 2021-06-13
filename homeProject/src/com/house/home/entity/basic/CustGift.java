package com.house.home.entity.basic;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name = "tCustGift")
public class CustGift extends BaseEntity{

	private static final long serialVersionUID = 1L;
@Id
	@Column(name = "PK")
	private Integer pK;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "GiftPK")
	private Integer giftPK;
	@Column(name = "Type")
	private String type;
	@Column(name = "QuoteModule")
	private String quoteModule;
	@Column(name = "MaxDiscAmtExpr")
	private String maxDiscAmtExpr;
	@Column(name = "DiscAmount")
	private Double discAmount;
	@Column(name = "SaleAmount")
	private Double saleAmount;
	@Column(name = "TotalCost")
	private Double totalCost;
	@Column(name = "DiscAmtType")
	private String discAmtType;
	@Column(name = "PerfDiscType")
	private String perfDiscType;
	@Column(name = "PerfDiscPer")
	private Double perfDiscPer;
	@Column(name = "PerfDiscAmount")
	private Double perfDiscAmount;
	@Column(name = "IsCalcDiscCtrl")
	private String isCalcDiscCtrl;
	@Column(name = "IsSoftToken")
	private String isSoftToken;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "CalcDiscCtrlPer")
	private Double calcDiscCtrlPer;
	@Column(name = "DiscConfirmRemarks")
	private String discConfirmRemarks;
	@Column(name = "ProjectAmount")
	private Double projectAmount;
	@Column(name = "DiscAmtCalcType")
	private String discAmtCalcType;
	@Column(name = "IsAdvance")
	private String isAdvance;
	
	@Transient
	private String detailJson;
	@Transient
	private String giftDescr;
	@Transient
	private double discPer;
	@Transient
	private String hasGiftAppDetail;

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public double getDiscPer() {
		return discPer;
	}
	public void setDiscPer(double discPer) {
		this.discPer = discPer;
	}
	public String getGiftDescr() {
		return giftDescr;
	}
	public void setGiftDescr(String giftDescr) {
		this.giftDescr = giftDescr;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public Integer getpK() {
		return pK;
	}
	public void setpK(Integer pK) {
		this.pK = pK;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Integer getGiftPK() {
		return giftPK;
	}
	public void setGiftPK(Integer giftPK) {
		this.giftPK = giftPK;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQuoteModule() {
		return quoteModule;
	}
	public void setQuoteModule(String quoteModule) {
		this.quoteModule = quoteModule;
	}
	public String getMaxDiscAmtExpr() {
		return maxDiscAmtExpr;
	}
	public void setMaxDiscAmtExpr(String maxDiscAmtExpr) {
		this.maxDiscAmtExpr = maxDiscAmtExpr;
	}
	public Double getDiscAmount() {
		return discAmount;
	}
	public void setDiscAmount(Double discAmount) {
		this.discAmount = discAmount;
	}
	public Double getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(Double saleAmount) {
		this.saleAmount = saleAmount;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	public String getDiscAmtType() {
		return discAmtType;
	}
	public void setDiscAmtType(String discAmtType) {
		this.discAmtType = discAmtType;
	}
	public String getPerfDiscType() {
		return perfDiscType;
	}
	public void setPerfDiscType(String perfDiscType) {
		this.perfDiscType = perfDiscType;
	}
	public Double getPerfDiscPer() {
		return perfDiscPer;
	}
	public void setPerfDiscPer(Double perfDiscPer) {
		this.perfDiscPer = perfDiscPer;
	}
	public Double getPerfDiscAmount() {
		return perfDiscAmount;
	}
	public void setPerfDiscAmount(Double perfDiscAmount) {
		this.perfDiscAmount = perfDiscAmount;
	}
	public String getIsCalcDiscCtrl() {
		return isCalcDiscCtrl;
	}
	public void setIsCalcDiscCtrl(String isCalcDiscCtrl) {
		this.isCalcDiscCtrl = isCalcDiscCtrl;
	}
	public String getIsSoftToken() {
		return isSoftToken;
	}
	public void setIsSoftToken(String isSoftToken) {
		this.isSoftToken = isSoftToken;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
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

	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public Double getCalcDiscCtrlPer() {
		return calcDiscCtrlPer;
	}
	public void setCalcDiscCtrlPer(Double calcDiscCtrlPer) {
		this.calcDiscCtrlPer = calcDiscCtrlPer;
	}
	public String getDiscConfirmRemarks() {
		return discConfirmRemarks;
	}
	public void setDiscConfirmRemarks(String discConfirmRemarks) {
		this.discConfirmRemarks = discConfirmRemarks;
	}
	public String getHasGiftAppDetail() {
		return hasGiftAppDetail;
	}
	public void setHasGiftAppDetail(String hasGiftAppDetail) {
		this.hasGiftAppDetail = hasGiftAppDetail;
	}
	
	public Double getProjectAmount() {
		return projectAmount;
	}
	public void setProjectAmount(Double projectAmount) {
		this.projectAmount = projectAmount;
	}
	public String getDiscAmtCalcType() {
		return discAmtCalcType;
	}
	public void setDiscAmtCalcType(String discAmtCalcType) {
		this.discAmtCalcType = discAmtCalcType;
	}
	public String getIsAdvance() {
		return isAdvance;
	}
	public void setIsAdvance(String isAdvance) {
		this.isAdvance = isAdvance;
	}
	
}
