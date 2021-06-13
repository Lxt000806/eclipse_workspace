package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * Item信息
 */
@Entity
@Table(name = "tItem")
public class Item extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "RemCode")
	private String remCode;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "ItemType2")
	private String itemType2;
	@Column(name = "ItemType3")
	private String itemType3;
	@Column(name = "BarCode")
	private String barCode;
	@Column(name = "SqlCode")
	private String sqlCode;
	@Column(name = "SupplCode")
	private String supplCode;
	@Column(name = "Model")
	private String model;
	@Column(name = "ItemSize")
	private Integer itemSize;
	@Column(name = "SizeDesc")
	private String sizeDesc;
	@Column(name = "Color")
	private String color;
	@Column(name = "Uom")
	private String uom;
	@Column(name = "Cost")
	private Double cost;
	@Column(name = "MarketPrice")
	private Double marketPrice;
	@Column(name = "Price")
	private Double price;
	@Column(name = "IsFixPrice")
	private String isFixPrice;
	@Column(name = "CommiType")
	private String commiType;
	@Column(name = "CommiPerc")
	private Double commiPerc;
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
	@Column(name = "AvgCost")
	private Double avgCost;
	@Column(name = "AllQty")
	private Double allQty;
	@Column(name = "ProjectCost")
	private Double projectCost;
	@Column(name = "IsSetItem")
	private String isSetItem;
	@Column(name = "WhCode")
	private String whCode;
	@Column(name = "SendType")
	private String sendType;
	@Column(name = "HasSample")
	private String hasSample;
	@Column(name = "PerfPer")
	private Double perfPer;  
	@Column(name = "buyer1")
	private String buyer1;
	@Column(name = "buyer2")
	private String buyer2;
	@Column(name = "crtDate")
	private Date crtDate;
	@Column(name = "isClearInv")
	private String isClearInv;
	@Column(name = "PerWeight")
	private Double perWeight;
	@Column(name = "MinQty")
	private Double minQty;
	@Column(name = "isProm")
	private String IsProm;
	@Column(name = "OldCost")
	private Double oldCost;
	@Column(name = "IsFee")
	private String isFee;
	@Column(name = "IsInv")
	private String isInv;
	@Column(name = "PerNum")
	private Double perNum;
	@Column(name = "PackageNum")
	private Integer packageNum;
	@Column(name = "Volume")
	private Double volume;
	@Column(name = "Size")
	private Double size;
	@Column(name = "SaleStragy")
	private String saleStragy;
	@Column(name = "OldPrice")
	private Double oldPrice;
	@Column(name = "WHFee")
	private Double wHFee;
	@Column(name = "LampNum")
	private Double lampNum;
	@Column(name = "InstallFeeType")
	private String installFeeType;
	@Column(name = "InstallFee")
	private Double installFee;
	@Column(name = "WHFeeType")
	private String whFeeType;
	@Column(name = "CustType")
	private String custType;	
	@Column(name = "CustTypeGroupNo")
	private String custTypeGroupNo;
	@Column(name = "IsActualItem")
	private String isActualItem;
	@Column(name = "WareHouseItemCode")
	private String wareHouseItemCode;
	
	@Column(name = "AdditionalCost")
	private Double additionalCost;

	@Transient
	private String ibdno;
	@Transient
	private String puno;
	
	@Transient
	private Double saleqty;//销售申请数
	@Transient
	private Double appqty;//领料审核数
	@Transient
	private Double applyqty;//领料申请数
	@Transient
	private String readonly;//"1":材料类型1只读，
	@Transient
	private String workType12;
	@Transient
	private String isItemProcess; //加工材料

	
	/**
	 * 控制材料选择组件一个或多个搜索条件是否隐藏
	 * 传入需要隐藏的条件组成的字符串，逗号分隔
	 * 例如：“code,type,expired”
	 */
	@Transient
	private String hiddenFields;
	
	@Transient
	private Double  purqty;//在途采购数
	@Transient
	private Double useqty;
	@Transient
	private String actNo; //礼品活动编号
	@Transient
	private String giftAppType; //礼品类型
	@Transient
	private String disabledEle;//禁用元素
	@Transient
	private String costRight;
	@Transient
	private String containCode;//1表示包含code查询 0，不包含
	@Transient 
	private String isCmpActGift;
	@Transient
	private Integer custTypeItemPk;//套餐材料信息pk
	@Transient
	private String isCustTypeItem;
	@Transient
	private String isItemTran;
	@Transient 
	private String isPrefPre;
	@Transient 
	private String itemType2Descr;
	@Transient 
	private String itemType3Descr;
	@Transient 
	private String sqlDescr; //品牌
	@Transient 
	private String supplDescr;
	@Transient 
	private String buyer1Descr;
	@Transient 
	private String buyer2Descr;
	@Transient
	private String uomDescr;
	@Transient
	private String detailJson;
	@Transient
	private String photoName;
	@Transient
	private String photoPath;
	@Transient 
	private String allNo;
	@Transient
	private String nowNo;
	@Transient
	private String isAdded;
	@Transient
	private String itcodes;
	@Transient
	private String  moduleCall;//调用模块
	@Transient
	private String whDescr;
	@Transient
	private String hasBuyerRight;
	@Transient
	private String hasBuyerDeptRight;
	@Transient
	private String isContainTax;
	@Transient
	private String custCode;
	@Transient
	private String pk;//套餐材料信息pk
	@Transient
	private String id;
	@Transient
	private String isMultiselect;
	@Transient
	private Integer prePlanAreaPk;
	@Transient
	private String isDescrWithPrice;
	@Transient
	private String canUseComItem;
	@Transient
	private String softLevel ;// 软装星级
	
	// 供应商供货价列表
	@Transient
	private String supplCostJson;
	@Transient
	private String wareHouseItemDescr;
	@Transient
	private String isOutSet;

	public String getWorkType12() {
		return workType12;
	}

	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}

	public String getIsOutSet() {
		return isOutSet;
	}

	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public String getIsItemTran() {
		return isItemTran;
	}

	public void setIsItemTran(String isItemTran) {
		this.isItemTran = isItemTran;
	}

	public String getIsCustTypeItem() {
		return isCustTypeItem;
	}

	public void setIsCustTypeItem(String isCustTypeItem) {
		this.isCustTypeItem = isCustTypeItem;
	}

	public String getIsCmpActGift() {
		return isCmpActGift;
	}

	public void setIsCmpActGift(String isCmpActGift) {
		this.isCmpActGift = isCmpActGift;
	}

	public String getCostRight() {
		return costRight;
	}

	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}

	public Double getPurqty() {
		return purqty;
	}

	public void setPurqty(Double purqty) {
		this.purqty = purqty;
	}

	public Double getUseqty() {
		return useqty;
	}

	public void setUseqty(Double useqty) {
		this.useqty = useqty;
	}

	public Double getSaleqty() {
		return saleqty;
	}

	public void setSaleqty(Double saleqty) {
		this.saleqty = saleqty;
	}

	public Double getAppqty() {
		return appqty;
	}

	public void setAppqty(Double appqty) {
		this.appqty = appqty;
	}

	public Double getApplyqty() {
		return applyqty;
	}

	public void setApplyqty(Double applyqty) {
		this.applyqty = applyqty;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getPuno() {
		return puno;
	}

	public void setPuno(String puno) {
		this.puno = puno;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setRemCode(String remCode) {
		this.remCode = remCode;
	}
	
	public String getRemCode() {
		return this.remCode;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	
	public String getItemType2() {
		return this.itemType2;
	}
	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}
	
	public String getItemType3() {
		return this.itemType3;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	public String getBarCode() {
		return this.barCode;
	}
	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}
	
	public String getSqlCode() {
		return this.sqlCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	
	public String getSupplCode() {
		return this.supplCode;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getModel() {
		return this.model;
	}
	public void setItemSize(Integer itemSize) {
		this.itemSize = itemSize;
	}
	
	public Integer getItemSize() {
		return this.itemSize;
	}
	public void setSizeDesc(String sizeDesc) {
		this.sizeDesc = sizeDesc;
	}
	
	public String getSizeDesc() {
		return this.sizeDesc;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public String getUom() {
		return this.uom;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Double getCost() {
		return this.cost;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public Double getMarketPrice() {
		return this.marketPrice;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPrice() {
		return this.price;
	}
	public void setIsFixPrice(String isFixPrice) {
		this.isFixPrice = isFixPrice;
	}
	
	public String getIsFixPrice() {
		return this.isFixPrice;
	}
	public void setCommiType(String commiType) {
		this.commiType = commiType;
	}
	
	public String getCommiType() {
		return this.commiType;
	}
	public void setCommiPerc(Double commiPerc) {
		this.commiPerc = commiPerc;
	}
	
	public Double getCommiPerc() {
		return this.commiPerc;
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
	public void setAvgCost(Double avgCost) {
		this.avgCost = avgCost;
	}
	
	public Double getAvgCost() {
		return this.avgCost;
	}
	public void setAllQty(Double allQty) {
		this.allQty = allQty;
	}
	
	public Double getAllQty() {
		return this.allQty;
	}
	public void setProjectCost(Double projectCost) {
		this.projectCost = projectCost;
	}
	
	public Double getProjectCost() {
		return this.projectCost;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	
	public String getIsSetItem() {
		return this.isSetItem;
	}

	public String getIbdno() {
		return ibdno;
	}

	public void setIbdno(String ibdno) {
		this.ibdno = ibdno;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getGiftAppType() {
		return giftAppType;
	}

	public void setGiftAppType(String giftAppType) {
		this.giftAppType = giftAppType;
	}

	public String getDisabledEle() {
		return disabledEle;
	}

	public void setDisabledEle(String disabledEle) {
		this.disabledEle = disabledEle;
	}

	public String getContainCode() {
		return containCode;
	}

	public void setContainCode(String containCode) {
		this.containCode = containCode;
	}

	public Integer getCustTypeItemPk() {
		return custTypeItemPk;
	}

	public void setCustTypeItemPk(Integer custTypeItemPk) {
		this.custTypeItemPk = custTypeItemPk;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getHasSample() {
		return hasSample;
	}

	public void setHasSample(String hasSample) {
		this.hasSample = hasSample;
	}

	public Double getPerfPer() {
		return perfPer;
	}

	public void setPerfPer(Double perfPer) {
		this.perfPer = perfPer;
	}

	public String getBuyer1() {
		return buyer1;
	}

	public void setBuyer1(String buyer1) {
		this.buyer1 = buyer1;
	}

	public String getBuyer2() {
		return buyer2;
	}

	public void setBuyer2(String buyer2) {
		this.buyer2 = buyer2;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public String getIsClearInv() {
		return isClearInv;
	}

	public void setIsClearInv(String isClearInv) {
		this.isClearInv = isClearInv;
	}

	public Double getPerWeight() {
		return perWeight;
	}

	public void setPerWeight(Double perWeight) {
		this.perWeight = perWeight;
	}

	public Double getMinQty() {
		return minQty;
	}

	public void setMinQty(Double minQty) {
		this.minQty = minQty;
	}

	public String getIsProm() {
		return IsProm;
	}

	public void setIsProm(String isProm) {
		IsProm = isProm;
	}

	public Double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Double getOldCost() {
		return oldCost;
	}

	public void setOldCost(Double oldCost) {
		this.oldCost = oldCost;
	}

	public String getIsFee() {
		return isFee;
	}

	public void setIsFee(String isFee) {
		this.isFee = isFee;
	}

	public String getIsInv() {
		return isInv;
	}

	public void setIsInv(String isInv) {
		this.isInv = isInv;
	}

	public Double getPerNum() {
		return perNum;
	}

	public void setPerNum(Double perNum) {
		this.perNum = perNum;
	}

	public Integer getPackageNum() {
		return packageNum;
	}

	public void setPackageNum(Integer packageNum) {
		this.packageNum = packageNum;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public String getIsPrefPre() {
		return isPrefPre;
	}

	public void setIsPrefPre(String isPrefPre) {
		this.isPrefPre = isPrefPre;
	}

	public String getItemType2Descr() {
		return itemType2Descr;
	}

	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}

	public String getItemType3Descr() {
		return itemType3Descr;
	}

	public void setItemType3Descr(String itemType3Descr) {
		this.itemType3Descr = itemType3Descr;
	}

	public String getSqlDescr() {
		return sqlDescr;
	}

	public void setSqlDescr(String sqlDescr) {
		this.sqlDescr = sqlDescr;
	}

	public String getSupplDescr() {
		return supplDescr;
	}

	public void setSupplDescr(String supplDescr) {
		this.supplDescr = supplDescr;
	}

	public String getBuyer1Descr() {
		return buyer1Descr;
	}

	public void setBuyer1Descr(String buyer1Descr) {
		this.buyer1Descr = buyer1Descr;
	}

	public String getBuyer2Descr() {
		return buyer2Descr;
	}

	public void setBuyer2Descr(String buyer2Descr) {
		this.buyer2Descr = buyer2Descr;
	}

	public String getUomDescr() {
		return uomDescr;
	}

	public void setUomDescr(String uomDescr) {
		this.uomDescr = uomDescr;
	}

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getAllNo() {
		return allNo;
	}

	public void setAllNo(String allNo) {
		this.allNo = allNo;
	}

	public String getNowNo() {
		return nowNo;
	}

	public void setNowNo(String nowNo) {
		this.nowNo = nowNo;
	}
	public String getSaleStragy() {
		return saleStragy;
	}

	public void setSaleStragy(String saleStragy) {
		this.saleStragy = saleStragy;
	}


	public String getIsAdded() {
		return isAdded;
	}

	public void setIsAdded(String isAdded) {
		this.isAdded = isAdded;
	}

	public String getItcodes() {
		return itcodes;
	}

	public void setItcodes(String itcodes) {
		this.itcodes = itcodes;
	}

	public String getModuleCall() {
		return moduleCall;
	}

	public void setModuleCall(String moduleCall) {
		this.moduleCall = moduleCall;
	}

	public String getWhDescr() {
		return whDescr;
	}

	public void setWhDescr(String whDescr) {
		this.whDescr = whDescr;
	}

	public Double getwHFee() {
		return wHFee;
	}

	public void setwHFee(Double wHFee) {
		this.wHFee = wHFee;
	}

	public String getHasBuyerRight() {
		return hasBuyerRight;
	}

	public void setHasBuyerRight(String hasBuyerRight) {
		this.hasBuyerRight = hasBuyerRight;
	}

	public String getHasBuyerDeptRight() {
		return hasBuyerDeptRight;
	}

	public void setHasBuyerDeptRight(String hasBuyerDeptRight) {
		this.hasBuyerDeptRight = hasBuyerDeptRight;
	}

	public Double getLampNum() {
		return lampNum;
	}

	public void setLampNum(Double lampNum) {
		this.lampNum = lampNum;
	}

	public String getIsContainTax() {
		return isContainTax;
	}

	public void setIsContainTax(String isContainTax) {
		this.isContainTax = isContainTax;
	}

	public String getInstallFeeType() {
		return installFeeType;
	}

	public void setInstallFeeType(String installFeeType) {
		this.installFeeType = installFeeType;
	}

	public Double getInstallFee() {
		return installFee;
	}

	public void setInstallFee(Double installFee) {
		this.installFee = installFee;
	}

	public String getWhFeeType() {
		return whFeeType;
	}

	public void setWhFeeType(String whFeeType) {
		this.whFeeType = whFeeType;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsMultiselect() {
		return isMultiselect;
	}

	public void setIsMultiselect(String isMultiselect) {
		this.isMultiselect = isMultiselect;
	}

	public Integer getPrePlanAreaPk() {
		return prePlanAreaPk;
	}

	public void setPrePlanAreaPk(Integer prePlanAreaPk) {
		this.prePlanAreaPk = prePlanAreaPk;
	}

	public String getIsDescrWithPrice() {
		return isDescrWithPrice;
	}

	public void setIsDescrWithPrice(String isDescrWithPrice) {
		this.isDescrWithPrice = isDescrWithPrice;
	}

	public String getCanUseComItem() {
		return canUseComItem;
	}

	public void setCanUseComItem(String canUseComItem) {
		this.canUseComItem = canUseComItem;
	}

	public String getSoftLevel() {
		return softLevel;
	}

	public void setSoftLevel(String softLevel) {
		this.softLevel = softLevel;
	}

    public String getSupplCostJson() {
        return supplCostJson;
    }
    
    public String getSupplCostXml() {
        return XmlConverUtil.jsonToXmlNoHead(supplCostJson);
    }

    public void setSupplCostJson(String supplCostJson) {
        this.supplCostJson = supplCostJson;
    }

    public String getCustTypeGroupNo() {
        return custTypeGroupNo;
    }

    public void setCustTypeGroupNo(String custTypeGroupNo) {
        this.custTypeGroupNo = custTypeGroupNo;
    }

    public String getHiddenFields() {
        return hiddenFields;
    }

    public void setHiddenFields(String hiddenFields) {
        this.hiddenFields = hiddenFields;
    }

	public String getIsActualItem() {
		return isActualItem;
	}

	public void setIsActualItem(String isActualItem) {
		this.isActualItem = isActualItem;
	}

	public String getWareHouseItemCode() {
		return wareHouseItemCode;
	}

	public void setWareHouseItemCode(String wareHouseItemCode) {
		this.wareHouseItemCode = wareHouseItemCode;
	}

	public String getWareHouseItemDescr() {
		return wareHouseItemDescr;
	}

	public void setWareHouseItemDescr(String wareHouseItemDescr) {
		this.wareHouseItemDescr = wareHouseItemDescr;
	}


	public String getIsItemProcess() {
		return isItemProcess;
	}

	public void setIsItemProcess(String isItemProcess) {
		this.isItemProcess = isItemProcess;
	}
	  
    public Double getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(Double additionalCost) {
        this.additionalCost = additionalCost;
    }
	   
}
