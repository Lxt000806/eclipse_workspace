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
 * ItemPlan信息
 */
@Entity
@Table(name = "tItemPlan")
public class ItemPlan extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "FixAreaPK")
	private Integer fixAreaPk;
	@Column(name = "IntProdPK")
	private Integer intProdPk;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "ProjectQty")
	private Double projectQty;
	@Column(name = "Qty")
	private Double qty;
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
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "ProcessCost")
	private Double processCost;
	@Column(name = "IsService")
	private Integer isService;
	@Column(name = "IsCommi")
	private Integer isCommi;
	@Column(name = "IsOutSet")
	private String isOutSet;
	@Column(name = "ItemSetNo")
	private String itemSetNo;
	@Column(name = "CutType")
	private String cutType;
	@Column(name="GiftPK")
	private Integer giftPk;
	@Column(name = "AlgorithmPer")
	private Double algorithmPer;
	@Column(name = "AlgorithmDeduct")
	private Double algorithmDeduct;
	@Column(name = "TempDtPk")
	private Integer tempDtPk;
		
	@Transient
	private Date planSendDate;
	@Transient
	private String mainTempNo;
	@Transient
	private String descr;
	@Transient
	private String designMan;
	@Transient
	private String businessMan;
	@Transient
	private String designManDescr;
	@Transient
	private String businessManDescr;
	@Transient
	private String status;
	@Transient
	private String address;
	@Transient
	private String isCupboard;
	@Transient
	private String rowId;
	@Transient
	private Double fee;
	@Transient
	private Double discAmount;
	@Transient
	private Double cupboardFee;
	@Transient
	private Double cupboardDisc;
	@Transient
	private Double softFee_WallPaper;
	@Transient
	private Double softFee_Curtain;
	@Transient
	private Double softFee_Light;
	@Transient
	private Double softFee_Furniture;
	@Transient
	private Double softFee_Adornment;
	@Transient
	private Double manageFee_Main;
	@Transient
	private Double manageFee_Serv;
	@Transient
	private Double manageFee_Soft;
	@Transient
	private Double manageFee_Int;
	@Transient
	private Double manageFee_Cup;
	@Transient
	private String custType;
	@Transient
	private String detailJson;
	@Transient
	private String fixareadescr;
	@Transient
	private String itemdescr;
	@Transient
	private String itemtype2descr;
	@Transient
	private boolean isContainMainServ;//是否增加包含主材服务判断
	@Transient
	private boolean isExceptMain;//总价不包含主材
	@Transient
	private String itemType2;
	@Transient
	private String empCode;
	@Transient
	private Date mainManDateFrom;
	@Transient
	private Date mainManDateTo;
	@Transient
	private String tempNo;
	@Transient
	private String sCustCode;//源预算(被复制)的客户编号
	@Transient
	private String isAuto;//是否自动生成付款信息预录 1：是
	@Transient
	private Integer isClearFixArea;//是否清除装修区域，0：不清除装修区域，1：清除装修区域
	@Transient
	private String baseItemType1;
	@Transient
	private String mainBusinessMan;//主材管家
	@Transient
	private String softPlanRemark;
	@Transient
	private String payType;
	@Transient
	private String isIntPerfDetail;//集成业绩查看明细时要判断是否橱柜
	@Transient
	private Integer containInt;
	@Transient
	private Integer containCup;
	@Transient
	private Integer containMain;
	@Transient
	private Integer containSoft;
	@Transient
	private Integer containMainServ;
	@Transient
	private String algorithm;
	@Transient 
	private Integer  DoorPK;
	@Transient 
	private Integer prePlanAreaPK;
	@Transient
	private String planBakNo; //备份编号
	@Transient 
	private String mustImportTemp;
	@Transient 
	private String isRegenCanreplace ;//可替换项是否替换
	@Transient 
	private String isRegenCanmodiQty ;//可替换项是否替换
	@Transient
	private String costRight; //成本查看权限
	@Transient 
	private  String canUseComBaseItem;
	@Transient 
	private Integer giftPK;
	@Transient 
	private String giftDescr;
	@Transient
	private String isCustGift;
	@Transient
	private String isHasCustProfit;
	@Transient
	private String fixAreaType;
	@Transient
	private String baseItemCode;
	@Transient
	private String type;
	@Transient
	private String no;
	@Transient 
	private String canUseComItem;
	@Transient
	private String discTokenNo;
	@Transient
	private String contractStatus;
	@Transient
	private String wfProcInstNo;
	
	public String getWfProcInstNo() {
		return wfProcInstNo;
	}
	public void setWfProcInstNo(String wfProcInstNo) {
		this.wfProcInstNo = wfProcInstNo;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public Integer getGiftPk() {
		return giftPk;
	}
	public void setGiftPk(Integer giftPk) {
		this.giftPk = giftPk;
	}
	public String getIsCustGift() {
		return isCustGift;
	}
	public void setIsCustGift(String isCustGift) {
		this.isCustGift = isCustGift;
	}

	public Date getPlanSendDate() {
		return planSendDate;
	}
	public void setPlanSendDate(Date planSendDate) {
		this.planSendDate = planSendDate;
	}
	public String getItemPlanDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
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
	public void setProjectQty(Double projectQty) {
		this.projectQty = projectQty;
	}
	
	public Double getProjectQty() {
		return this.projectQty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
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
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setProcessCost(Double processCost) {
		this.processCost = processCost;
	}
	
	public Double getProcessCost() {
		return this.processCost;
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
	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}
	
	public String getIsOutSet() {
		return this.isOutSet;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDesignMan() {
		return designMan;
	}

	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}

	public String getBusinessMan() {
		return businessMan;
	}

	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDesignManDescr() {
		return designManDescr;
	}

	public void setDesignManDescr(String designManDescr) {
		this.designManDescr = designManDescr;
	}

	public String getBusinessManDescr() {
		return businessManDescr;
	}

	public void setBusinessManDescr(String businessManDescr) {
		this.businessManDescr = businessManDescr;
	}

	public String getItemSetNo() {
		return itemSetNo;
	}

	public void setItemSetNo(String itemSetNo) {
		this.itemSetNo = itemSetNo;
	}

	public String getIsCupboard() {
		return isCupboard;
	}

	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getDiscAmount() {
		return discAmount;
	}

	public void setDiscAmount(Double discAmount) {
		this.discAmount = discAmount;
	}

	public Double getCupboardFee() {
		return cupboardFee;
	}

	public void setCupboardFee(Double cupboardFee) {
		this.cupboardFee = cupboardFee;
	}

	public Double getCupboardDisc() {
		return cupboardDisc;
	}

	public void setCupboardDisc(Double cupboardDisc) {
		this.cupboardDisc = cupboardDisc;
	}

	public Double getSoftFee_WallPaper() {
		return softFee_WallPaper;
	}

	public void setSoftFee_WallPaper(Double softFee_WallPaper) {
		this.softFee_WallPaper = softFee_WallPaper;
	}

	public Double getSoftFee_Curtain() {
		return softFee_Curtain;
	}

	public void setSoftFee_Curtain(Double softFee_Curtain) {
		this.softFee_Curtain = softFee_Curtain;
	}

	public Double getSoftFee_Light() {
		return softFee_Light;
	}

	public void setSoftFee_Light(Double softFee_Light) {
		this.softFee_Light = softFee_Light;
	}

	public Double getSoftFee_Furniture() {
		return softFee_Furniture;
	}

	public void setSoftFee_Furniture(Double softFee_Furniture) {
		this.softFee_Furniture = softFee_Furniture;
	}

	public Double getSoftFee_Adornment() {
		return softFee_Adornment;
	}

	public void setSoftFee_Adornment(Double softFee_Adornment) {
		this.softFee_Adornment = softFee_Adornment;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getFixareadescr() {
		return fixareadescr;
	}

	public void setFixareadescr(String fixareadescr) {
		this.fixareadescr = fixareadescr;
	}

	public String getItemdescr() {
		return itemdescr;
	}

	public void setItemdescr(String itemdescr) {
		this.itemdescr = itemdescr;
	}

	public String getItemtype2descr() {
		return itemtype2descr;
	}

	public void setItemtype2descr(String itemtype2descr) {
		this.itemtype2descr = itemtype2descr;
	}
	public boolean isContainMainServ() {
		return isContainMainServ;
	}
	public void setContainMainServ(boolean isContainMainServ) {
		this.isContainMainServ = isContainMainServ;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Date getMainManDateFrom() {
		return mainManDateFrom;
	}
	public void setMainManDateFrom(Date mainManDateFrom) {
		this.mainManDateFrom = mainManDateFrom;
	}
	public Date getMainManDateTo() {
		return mainManDateTo;
	}
	public void setMainManDateTo(Date mainManDateTo) {
		this.mainManDateTo = mainManDateTo;
	}
	public String getTempNo() {
		return tempNo;
	}
	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}
	public String getsCustCode() {
		return sCustCode;
	}
	public void setsCustCode(String sCustCode) {
		this.sCustCode = sCustCode;
	}
	public boolean isExceptMain() {
		return isExceptMain;
	}
	public void setExceptMain(boolean isExceptMain) {
		this.isExceptMain = isExceptMain;
	}
	public Double getManageFee_Main() {
		return manageFee_Main;
	}
	public void setManageFee_Main(Double manageFee_Main) {
		this.manageFee_Main = manageFee_Main;
	}
	public Double getManageFee_Serv() {
		return manageFee_Serv;
	}
	public void setManageFee_Serv(Double manageFee_Serv) {
		this.manageFee_Serv = manageFee_Serv;
	}
	public Double getManageFee_Soft() {
		return manageFee_Soft;
	}
	public void setManageFee_Soft(Double manageFee_Soft) {
		this.manageFee_Soft = manageFee_Soft;
	}
	public Double getManageFee_Int() {
		return manageFee_Int;
	}
	public void setManageFee_Int(Double manageFee_Int) {
		this.manageFee_Int = manageFee_Int;
	}
	public Double getManageFee_Cup() {
		return manageFee_Cup;
	}
	public void setManageFee_Cup(Double manageFee_Cup) {
		this.manageFee_Cup = manageFee_Cup;
	}
	public String getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}
	public Integer getIsClearFixArea() {
		return isClearFixArea;
	}
	public void setIsClearFixArea(Integer isClearFixArea) {
		this.isClearFixArea = isClearFixArea;
	}
	public String getBaseItemType1() {
		return baseItemType1;
	}
	public void setBaseItemType1(String baseItemType1) {
		this.baseItemType1 = baseItemType1;
	}
	public String getMainBusinessMan() {
		return mainBusinessMan;
	}
	public void setMainBusinessMan(String mainBusinessMan) {
		this.mainBusinessMan = mainBusinessMan;
	}
	public Integer getContainInt() {
		return containInt;
	}
	public void setContainInt(Integer containInt) {
		this.containInt = containInt;
	}
	public Integer getContainCup() {
		return containCup;
	}
	public void setContainCup(Integer containCup) {
		this.containCup = containCup;
	}
	public Integer getContainMain() {
		return containMain;
	}
	public void setContainMain(Integer containMain) {
		this.containMain = containMain;
	}
	public Integer getContainSoft() {
		return containSoft;
	}
	public void setContainSoft(Integer containSoft) {
		this.containSoft = containSoft;
	}
	public Integer getContainMainServ() {
		return containMainServ;
	}
	public void setContainMainServ(Integer containMainServ) {
		this.containMainServ = containMainServ;
	}
	public String getSoftPlanRemark() {
		return softPlanRemark;
	}
	public void setSoftPlanRemark(String softPlanRemark) {
		this.softPlanRemark = softPlanRemark;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public Integer getDoorPK() {
		return DoorPK;
	}
	public void setDoorPK(Integer doorPK) {
		DoorPK = doorPK;
	}
	public Integer getPrePlanAreaPK() {
		return prePlanAreaPK;
	}
	public void setPrePlanAreaPK(Integer prePlanAreaPK) {
		this.prePlanAreaPK = prePlanAreaPK;
	}

	public String getIsIntPerfDetail() {
		return isIntPerfDetail;
	}
	public void setIsIntPerfDetail(String isIntPerfDetail) {
		this.isIntPerfDetail = isIntPerfDetail;
	}
	public String getPlanBakNo() {
		return planBakNo;
	}
	public void setPlanBakNo(String planBakNo) {
		this.planBakNo = planBakNo;
	}
	public String getMustImportTemp() {
		return mustImportTemp;
	}
	public void setMustImportTemp(String mustImportTemp) {
		this.mustImportTemp = mustImportTemp;
	}
	public String getCutType() {
		return cutType;
	}
	public void setCutType(String cutType) {
		this.cutType = cutType;
	}
	public String getMainTempNo() {
		return mainTempNo;
	}
	public void setMainTempNo(String mainTempNo) {
		this.mainTempNo = mainTempNo;
	}
	public String getIsRegenCanreplace() {
		return isRegenCanreplace;
	}
	public void setIsRegenCanreplace(String isRegenCanreplace) {
		this.isRegenCanreplace = isRegenCanreplace;
	}

	public String getIsRegenCanmodiQty() {
		return isRegenCanmodiQty;
	}
	public void setIsRegenCanmodiQty(String isRegenCanmodiQty) {
		this.isRegenCanmodiQty = isRegenCanmodiQty;
	}

	public String getCostRight() {
		return costRight;
	}
	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}
	public String getCanUseComBaseItem() {
		return canUseComBaseItem;
	}
	public void setCanUseComBaseItem(String canUseComBaseItem) {
		this.canUseComBaseItem = canUseComBaseItem;
	}
	public Integer getGiftPK() {
		return giftPK;
	}
	public void setGiftPK(Integer giftPK) {
		this.giftPK = giftPK;
	}
	public String getGiftDescr() {
		return giftDescr;
	}
	public void setGiftDescr(String giftDescr) {
		this.giftDescr = giftDescr;
	}
	public String getIsHasCustProfit() {
		return isHasCustProfit;
	}
	public void setIsHasCustProfit(String isHasCustProfit) {
		this.isHasCustProfit = isHasCustProfit;
	}
	public String getFixAreaType() {
		return fixAreaType;
	}
	public void setFixAreaType(String fixAreaType) {
		this.fixAreaType = fixAreaType;
	}
	public String getBaseItemCode() {
		return baseItemCode;
	}
	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCanUseComItem() {
		return canUseComItem;
	}
	public void setCanUseComItem(String canUseComItem) {
		this.canUseComItem = canUseComItem;
	}
	public String getDiscTokenNo() {
		return discTokenNo;
	}
	public void setDiscTokenNo(String discTokenNo) {
		this.discTokenNo = discTokenNo;
	}
	public Double getAlgorithmPer() {
		return algorithmPer;
	}
	public void setAlgorithmPer(Double algorithmPer) {
		this.algorithmPer = algorithmPer;
	}
	public Double getAlgorithmDeduct() {
		return algorithmDeduct;
	}
	public void setAlgorithmDeduct(Double algorithmDeduct) {
		this.algorithmDeduct = algorithmDeduct;
	}
	public Integer getTempDtPk() {
		return tempDtPk;
	}
	public void setTempDtPk(Integer tempDtPk) {
		this.tempDtPk = tempDtPk;
	}
	
		
}
