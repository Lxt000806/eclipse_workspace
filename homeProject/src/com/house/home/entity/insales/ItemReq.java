package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.home.entity.design.PrePlanArea;

/**
 * ItemReq信息
 */
@Entity
@Table(name = "tItemReq")
public class ItemReq extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "FixAreaPk")
	private Integer fixAreaPk;
	@Column(name = "IntProdPk")
	private Integer intProdPk;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "SendQty")
	private Double sendQty;
	@Column(name = "Cost")
	private Double cost;
	@Column(name = "UnitPrice")
	private Double unitPrice;
	@Column(name = "BefLineAmount")
	private Double befLineAmount;
	@Column(name = "Markup")
	private Double markup;
	@Column(name = "LineAmount")
	private Double lineAmount;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "ProcessCost")
	private Double processCost;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "IsService")
	private Integer isService;
	@Column(name = "IsCommi")
	private Integer isCommi;
	@Column(name = "ItemSetNo")
	private String itemSetNo;
	
	@Transient
	private String itemType2;
	@Transient
	private String puno;
	@Transient
	private String itemCodeDescr;
	@Transient
	private String no;//领料单号
	@Transient
	private String unSelected;//要排除的pk串，多个,隔开
	@Transient
	private String isCupboard;
	@Transient
	private String fixareadescr;
	@Transient
	private String itemdescr;
	@Transient
	private String itemtype2descr;
	@Transient
	private String isSetItem;
	@Transient
	private String intproddescr;
	@Transient
	private String itemChgNo;
	@Transient
	private String supplierCode;
	@Transient
	private String onlyShowNotEqual;//只显示发货不等需求;
	@Transient
	private String sqlCode;//品牌编号
	@Transient
	private String customerStatus;//客户状态
	@Transient
	private String sendStatus;//发货状态
	@Transient
	private String isProm;//是否促销
	@Transient
	private String itemRight;//材料权限
	@Transient
	private String groupBy;//统计方式
	@Transient
	private String costRight;//成本权限
	@Transient
	private String phoneRight;//查看电话权限
	@Transient
	private String workType1; //工种分类1
	@Transient
	private String baseItemDescr; //基础项目名称
	@Transient
	private Integer prePlanAreaPk;
	
	public Integer getPrePlanAreaPk() {
		return prePlanAreaPk;
	}

	public void setPrePlanAreaPk(Integer prePlanAreaPk) {
		this.prePlanAreaPk = prePlanAreaPk;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getItemChgNo() {
		return itemChgNo;
	}

	public void setItemChgNo(String itemChgNo) {
		this.itemChgNo = itemChgNo;
	}

	public String getPuno() {
		return puno;
	}

	public void setPuno(String puno) {
		this.puno = puno;
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
	public void setFixAreaPk(Integer fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}
	
	public Integer getFixAreaPk() {
		return this.fixAreaPk;
	}
	public void setIntProdPk(Integer intProdPk) {
		this.intProdPk = intProdPk;
	}
	
	public Integer getIntProdPk() {
		return this.intProdPk;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemCode() {
		return this.itemCode;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
	}
	public void setSendQty(Double sendQty) {
		this.sendQty = sendQty;
	}
	
	public Double getSendQty() {
		return this.sendQty;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Double getCost() {
		return this.cost;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Double getUnitPrice() {
		return this.unitPrice;
	}
	public void setBefLineAmount(Double befLineAmount) {
		this.befLineAmount = befLineAmount;
	}
	
	public Double getBefLineAmount() {
		return this.befLineAmount;
	}
	public void setMarkup(Double markup) {
		this.markup = markup;
	}
	
	public Double getMarkup() {
		return this.markup;
	}
	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}
	
	public Double getLineAmount() {
		return this.lineAmount;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
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
	public void setProcessCost(Double processCost) {
		this.processCost = processCost;
	}
	
	public Double getProcessCost() {
		return this.processCost;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setIsService(Integer isService) {
		this.isService = isService;
	}
	
	public Integer getIsService() {
		return this.isService;
	}
	public void setIsCommi(Integer isCommi) {
		this.isCommi = isCommi;
	}
	
	public Integer getIsCommi() {
		return this.isCommi;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getItemCodeDescr() {
		return itemCodeDescr;
	}

	public void setItemCodeDescr(String itemCodeDescr) {
		this.itemCodeDescr = itemCodeDescr;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getUnSelected() {
		return unSelected;
	}

	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}

	public String getIsCupboard() {
		return isCupboard;
	}

	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}

	public String getFixareadescr() {
		return fixareadescr;
	}

	public void setFixareadescr(String fixareadescr) {
		this.fixareadescr = fixareadescr;
	}

	public String getItemtype2descr() {
		return itemtype2descr;
	}

	public void setItemtype2descr(String itemtype2descr) {
		this.itemtype2descr = itemtype2descr;
	}

	public String getItemdescr() {
		return itemdescr;
	}

	public void setItemdescr(String itemdescr) {
		this.itemdescr = itemdescr;
	}

	public String getItemSetNo() {
		return itemSetNo;
	}

	public void setItemSetNo(String itemSetNo) {
		this.itemSetNo = itemSetNo;
	}

	public String getIsSetItem() {
		return isSetItem;
	}

	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}

	public String getIntproddescr() {
		return intproddescr;
	}

	public void setIntproddescr(String intproddescr) {
		this.intproddescr = intproddescr;
	}

	public String getOnlyShowNotEqual() {
		return onlyShowNotEqual;
	}

	public void setOnlyShowNotEqual(String onlyShowNotEqual) {
		this.onlyShowNotEqual = onlyShowNotEqual;
	}

	public String getSqlCode() {
		return sqlCode;
	}

	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getIsProm() {
		return isProm;
	}

	public void setIsProm(String isProm) {
		this.isProm = isProm;
	}

	public String getItemRight() {
		return itemRight;
	}

	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getCostRight() {
		return costRight;
	}

	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}

	public String getPhoneRight() {
		return phoneRight;
	}

	public void setPhoneRight(String phoneRight) {
		this.phoneRight = phoneRight;
	}

	public String getWorkType1() {
		return workType1;
	}

	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}

	public String getBaseItemDescr() {
		return baseItemDescr;
	}

	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
	}


}
