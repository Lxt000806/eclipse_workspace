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

/**
 * BaseItemPlan信息
 */
@Entity
@Table(name = "tBaseItemPlan")
public class BaseItemPlan extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "FixAreaPK")
	private Integer fixAreaPk;
	@Column(name = "BaseItemCode")
	private String baseItemCode;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "Cost")
	private Double cost;
	@Column(name = "UnitPrice")
	private Double unitPrice;
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
	@Column(name = "Material")
	private Double material;
	@Column(name = "IsCheck")
	private Integer isCheck;
	@Column(name = "BaseAlgorithm")
	private String baseAlgorithm;
	@Column(name = "GiftPK")
	private Integer giftPk;
	@Column(name = "IsGift")
	private String isGift;
	@Column(name = "TempDtPk")
	private Integer tempDtPk;

	@Transient
	private String isOutSet;
	@Transient
	private String itemType1;
	@Transient
	private String rowId;
	@Transient
	private String baseItemType1;
	@Transient
	private String custTypeType;//1、清单客户 2、套餐客户
	@Transient
	private String custType;//客户类型
	@Transient 
	private Integer prePlanAreaPK;
	@Transient 
	private String mustImportTemp;
	@Transient 
	private  String canUseComBaseItem;
	
	
	public Integer getGiftPk() {
		return giftPk;
	}

	public void setGiftPk(Integer giftPk) {
		this.giftPk = giftPk;
	}

	public String getIsGift() {
		return isGift;
	}

	public void setIsGift(String isGift) {
		this.isGift = isGift;
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
	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}
	
	public String getBaseItemCode() {
		return this.baseItemCode;
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
	public void setMaterial(Double material) {
		this.material = material;
	}
	
	public Double getMaterial() {
		return this.material;
	}
	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}
	
	public Integer getIsCheck() {
		return this.isCheck;
	}

	public String getIsOutSet() {
		return isOutSet;
	}

	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getBaseItemType1() {
		return baseItemType1;
	}

	public void setBaseItemType1(String baseItemType1) {
		this.baseItemType1 = baseItemType1;
	}

	public String getCustTypeType() {
		return custTypeType;
	}

	public void setCustTypeType(String custTypeType) {
		this.custTypeType = custTypeType;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getBaseAlgorithm() {
		return baseAlgorithm;
	}

	public void setBaseAlgorithm(String baseAlgorithm) {
		this.baseAlgorithm = baseAlgorithm;
	}

	public Integer getPrePlanAreaPK() {
		return prePlanAreaPK;
	}

	public void setPrePlanAreaPK(Integer prePlanAreaPK) {
		this.prePlanAreaPK = prePlanAreaPK;
	}

	public String getMustImportTemp() {
		return mustImportTemp;
	}

	public void setMustImportTemp(String mustImportTemp) {
		this.mustImportTemp = mustImportTemp;
	}

	public String getCanUseComBaseItem() {
		return canUseComBaseItem;
	}

	public void setCanUseComBaseItem(String canUseComBaseItem) {
		this.canUseComBaseItem = canUseComBaseItem;
	}

	public Integer getTempDtPk() {
		return tempDtPk;
	}

	public void setTempDtPk(Integer tempDtPk) {
		this.tempDtPk = tempDtPk;
	}	

}
