package com.house.home.entity.basic;

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
 * Gift信息
 */
@Entity
@Table(name = "tGift")
public class Gift extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "ActName")
	private String actName;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "MinArea")
	private Double minArea;
	@Column(name = "MaxArea")
	private Double maxArea;
	@Column(name = "Type")
	private String type;
	@Column(name = "QuoteModule")
	private String quoteModule;
	@Column(name = "DiscType")
	private String discType;
	@Column(name = "DiscPer")
	private Double discPer;
	@Column(name = "DiscAmtType")
	private String discAmtType;
	@Column(name = "MaxDiscAmtExpr")
	private String maxDiscAmtExpr;
	@Column(name = "PerfDiscType")
	private String perfDiscType;
	@Column(name = "PerfDiscPer")
	private Double perfDiscPer;
	@Column(name = "IsCalcDiscCtrl")
	private String isCalcDiscCtrl;
	@Column(name = "IsService")
	private String isService;
	@Column(name = "IsOutSet")
	private String isOutSet;
	@Column(name = "IsSoftToken")
	private String isSoftToken;
	@Column(name = "IsLimitItem")
	private String isLimitItem;
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
	@Column(name = "IsCupboard")
	private String isCupboard;
	@Column(name = "CalcDiscCtrlPer")
	private Double calcDiscCtrlPer;
	@Column(name = "DiscAmtCalcType")
	private String discAmtCalcType;
	@Column(name = "IsAdvance")
	private String isAdvance;
	@Column(name = "IsProvideDiscToken")
	private String isProvideDiscToken;
	@Column(name = "ConfirmLevel")
	private String confirmLevel;
	
	@Transient
	private String custType;
	@Transient
	private String baseItemCode;
	@Transient
	private String itemCode;
	@Transient
	private String baseItemDescr;
	@Transient
	private String itemDescr;
	@Transient
	private String custTypeDescr;
	@Transient
	private String custTypes;
	@Transient
	private String custTypeJson;
	@Transient
	private String itemJson;
	@Transient
	private String itemCodes;
	@Transient
	private String baseItemCodes;
	@Transient
	private String custCode;
	
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	
	public String getActName() {
		return this.actName;
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
	public void setMinArea(Double minArea) {
		this.minArea = minArea;
	}
	
	public Double getMinArea() {
		return this.minArea;
	}
	public void setMaxArea(Double maxArea) {
		this.maxArea = maxArea;
	}
	
	public Double getMaxArea() {
		return this.maxArea;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setQuoteModule(String quoteModule) {
		this.quoteModule = quoteModule;
	}
	
	public String getQuoteModule() {
		return this.quoteModule;
	}
	public void setDiscType(String discType) {
		this.discType = discType;
	}
	
	public String getDiscType() {
		return this.discType;
	}
	public void setDiscPer(Double discPer) {
		this.discPer = discPer;
	}
	
	public Double getDiscPer() {
		return this.discPer;
	}
	public void setDiscAmtType(String discAmtType) {
		this.discAmtType = discAmtType;
	}
	
	public String getDiscAmtType() {
		return this.discAmtType;
	}
	public void setMaxDiscAmtExpr(String maxDiscAmtExpr) {
		this.maxDiscAmtExpr = maxDiscAmtExpr;
	}
	
	public String getMaxDiscAmtExpr() {
		return this.maxDiscAmtExpr;
	}
	public void setPerfDiscType(String perfDiscType) {
		this.perfDiscType = perfDiscType;
	}
	
	public String getPerfDiscType() {
		return this.perfDiscType;
	}
	public void setPerfDiscPer(Double perfDiscPer) {
		this.perfDiscPer = perfDiscPer;
	}
	
	public Double getPerfDiscPer() {
		return this.perfDiscPer;
	}
	public void setIsCalcDiscCtrl(String isCalcDiscCtrl) {
		this.isCalcDiscCtrl = isCalcDiscCtrl;
	}
	
	public String getIsCalcDiscCtrl() {
		return this.isCalcDiscCtrl;
	}
	public void setIsService(String isService) {
		this.isService = isService;
	}
	
	public String getIsService() {
		return this.isService;
	}
	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}
	
	public String getIsOutSet() {
		return this.isOutSet;
	}
	public void setIsSoftToken(String isSoftToken) {
		this.isSoftToken = isSoftToken;
	}
	
	public String getIsSoftToken() {
		return this.isSoftToken;
	}
	public void setIsLimitItem(String isLimitItem) {
		this.isLimitItem = isLimitItem;
	}
	
	public String getIsLimitItem() {
		return this.isLimitItem;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
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

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getBaseItemCode() {
		return baseItemCode;
	}

	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getBaseItemDescr() {
		return baseItemDescr;
	}

	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
	}

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public String getCustTypeDescr() {
		return custTypeDescr;
	}

	public void setCustTypeDescr(String custTypeDescr) {
		this.custTypeDescr = custTypeDescr;
	}

	public String getCustTypes() {
		return custTypes;
	}

	public void setCustTypes(String custTypes) {
		this.custTypes = custTypes;
	}

	public String getCustTypeJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(custTypeJson);
    	return xml;
	}

	public void setCustTypeJson(String custTypeJson) {
		this.custTypeJson = custTypeJson;
	}

	public String getItemJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(itemJson);
    	return xml;
	}

	public void setItemJson(String itemJson) {
		this.itemJson = itemJson;
	}

	public String getItemCodes() {
		return itemCodes;
	}

	public void setItemCodes(String itemCodes) {
		this.itemCodes = itemCodes;
	}

	public String getBaseItemCodes() {
		return baseItemCodes;
	}

	public void setBaseItemCodes(String baseItemCodes) {
		this.baseItemCodes = baseItemCodes;
	}

	public String getIsCupboard() {
		return isCupboard;
	}

	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}

	public Double getCalcDiscCtrlPer() {
		return calcDiscCtrlPer;
	}

	public void setCalcDiscCtrlPer(Double calcDiscCtrlPer) {
		this.calcDiscCtrlPer = calcDiscCtrlPer;
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

	public String getIsProvideDiscToken() {
		return isProvideDiscToken;
	}

	public void setIsProvideDiscToken(String isProvideDiscToken) {
		this.isProvideDiscToken = isProvideDiscToken;
	}

	public String getConfirmLevel() {
		return confirmLevel;
	}

	public void setConfirmLevel(String confirmLevel) {
		this.confirmLevel = confirmLevel;
	}

	
}
