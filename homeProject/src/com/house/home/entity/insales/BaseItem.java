package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * BaseItem信息
 */
@Entity
@Table(name = "tBaseItem")
public class BaseItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "RemCode")
	private String remCode;
	@Column(name = "BaseItemType1")
	private String baseItemType1;
	@Column(name = "BaseItemType2")
	private String baseItemType2;
	@Column(name = "Cost")
	private Double cost;
	@Column(name = "MarketPrice")
	private Double marketPrice;
	@Column(name = "OfferPri")
	private Double offerPri;
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
	@Column(name = "Uom")
	private String uom;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "Material")
	private Double material;
	@Column(name = "Category")
	private String category;
	@Column(name = "CtrlType")
	private String ctrlType;
	@Column(name = "CtrlOfferCost")
	private Double ctrlOfferCost;
	@Column(name = "IsCalMangeFee")
	private String isCalMangeFee;
	@Column(name = "PrjCtrlType")
	private String prjCtrlType;
	@Column(name = "OfferCtrl")
	private String offerCtrl;
	@Column(name = "MaterialCtrl")
	private String materialCtrl;
	@Column(name = "PrjType")
	private String prjType;
	@Column(name = "IsFixPrice")
	private String IsFixPrice;
	@Column(name="CustType")
	private String custType;
	@Column(name="PerfPer")
	private Double perfPer;
	@Column(name="ProjectPrice")
	private Double projectPrice;
	@Column(name="AllowPriceRise")
	private String allowPriceRise;
	@Column(name="IsOutSet")
	private String isOutSet;
	@Column(name="IsBaseItemSet")
	private String isBaseItemSet;
	@Column(name="BaseTempNo")
	private String baseTempNo;
	@Column(name="FixAreaType")
	private String fixAreaType;
	
	@Column(name = "CustTypeGroupNo")
	private String custTypeGroupNo;
	
	@Column(name = "ItemType2")
	private String itemType2;
	
	@Transient
	private String itemType1;

    @Transient
	private String fixAreaDescr;
	@Transient
	private String custTypeType;
	@Transient
	private String noSetBaseCheckItem;//未设置基础结算项目 --add by zb
	@Transient
	private String customerType;
	@Transient
	private String isPrefpre;
	@Transient
	private String canUseComBaseItem;
	@Transient
	private String custCode;
	@Transient
	private String baseTempDescr;
	
	public String getCanUseComBaseItem() {
		return canUseComBaseItem;
	}

	public void setCanUseComBaseItem(String canUseComBaseItem) {
		this.canUseComBaseItem = canUseComBaseItem;
	}

	public String getIsPrefpre() {
		return isPrefpre;
	}

	public void setIsPrefpre(String isPrefpre) {
		this.isPrefpre = isPrefpre;
	}

	public String getAllowPriceRise() {
		return allowPriceRise;
	}

	public void setAllowPriceRise(String allowPriceRise) {
		this.allowPriceRise = allowPriceRise;
	}

	public Double getPerfPer() {
		return perfPer;
	}

	public void setPerfPer(Double perfPer) {
		this.perfPer = perfPer;
	}

	public Double getProjectPrice() {
		return projectPrice;
	}

	public void setProjectPrice(Double projectPrice) {
		this.projectPrice = projectPrice;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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
	public void setBaseItemType1(String baseItemType1) {
		this.baseItemType1 = baseItemType1;
	}
	
	public String getBaseItemType1() {
		return this.baseItemType1;
	}
	public void setBaseItemType2(String baseItemType2) {
		this.baseItemType2 = baseItemType2;
	}
	
	public String getBaseItemType2() {
		return this.baseItemType2;
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
	public void setOfferPri(Double offerPri) {
		this.offerPri = offerPri;
	}
	
	public Double getOfferPri() {
		return this.offerPri;
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
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public String getUom() {
		return this.uom;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setMaterial(Double material) {
		this.material = material;
	}
	
	public Double getMaterial() {
		return this.material;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return this.category;
	}
	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}
	
	public String getCtrlType() {
		return this.ctrlType;
	}
	public void setCtrlOfferCost(Double ctrlOfferCost) {
		this.ctrlOfferCost = ctrlOfferCost;
	}
	
	public Double getCtrlOfferCost() {
		return this.ctrlOfferCost;
	}

	public String getIsOutSet() {
		return isOutSet;
	}

	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}

	public String getFixAreaDescr() {
		return fixAreaDescr;
	}

	public void setFixAreaDescr(String fixAreaDescr) {
		this.fixAreaDescr = fixAreaDescr;
	}

	public String getCustTypeType() {
		return custTypeType;
	}

	public void setCustTypeType(String custTypeType) {
		this.custTypeType = custTypeType;
	}

	public String getIsCalMangeFee() {
		return isCalMangeFee;
	}

	public void setIsCalMangeFee(String isCalMangeFee) {
		this.isCalMangeFee = isCalMangeFee;
	}

	public String getPrjCtrlType() {
		return prjCtrlType;
	}

	public void setPrjCtrlType(String prjCtrlType) {
		this.prjCtrlType = prjCtrlType;
	}

	public String getOfferCtrl() {
		return offerCtrl;
	}

	public void setOfferCtrl(String offerCtrl) {
		this.offerCtrl = offerCtrl;
	}

	public String getMaterialCtrl() {
		return materialCtrl;
	}

	public void setMaterialCtrl(String materialCtrl) {
		this.materialCtrl = materialCtrl;
	}

	public String getPrjType() {
		return prjType;
	}

	public void setPrjType(String prjType) {
		this.prjType = prjType;
	}

	public String getIsFixPrice() {
		return IsFixPrice;
	}

	public void setIsFixPrice(String isFixPrice) {
		IsFixPrice = isFixPrice;
	}
	
	public String getNoSetBaseCheckItem() {
		return noSetBaseCheckItem;
	}

	public void setNoSetBaseCheckItem(String noSetBaseCheckItem) {
		this.noSetBaseCheckItem = noSetBaseCheckItem;
	}

	public String getIsBaseItemSet() {
		return isBaseItemSet;
	}

	public void setIsBaseItemSet(String isBaseItemSet) {
		this.isBaseItemSet = isBaseItemSet;
	}

	public String getBaseTempNo() {
		return baseTempNo;
	}

	public void setBaseTempNo(String baseTempNo) {
		this.baseTempNo = baseTempNo;
	}

	public String getFixAreaType() {
		return fixAreaType;
	}

	public void setFixAreaType(String fixAreaType) {
		this.fixAreaType = fixAreaType;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getBaseTempDescr() {
		return baseTempDescr;
	}

	public void setBaseTempDescr(String baseTempDescr) {
		this.baseTempDescr = baseTempDescr;
	}

    public String getCustTypeGroupNo() {
        return custTypeGroupNo;
    }

    public void setCustTypeGroupNo(String custTypeGroupNo) {
        this.custTypeGroupNo = custTypeGroupNo;
    }
    
    public String getItemType2() {
        return itemType2;
    }

    public void setItemType2(String itemType2) {
        this.itemType2 = itemType2;
    }

    public String getItemType1() {
        return itemType1;
    }

    public void setItemType1(String itemType1) {
        this.itemType1 = itemType1;
    }
	
}
