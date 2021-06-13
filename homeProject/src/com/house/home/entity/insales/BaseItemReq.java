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

/**
 * BaseItemReq信息
 */
@Entity
@Table(name = "tBaseItemReq")
public class BaseItemReq extends BaseEntity {

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
	@Column(name = "Material")
	private Double material;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "IsOutSet")
	private String isOutSet;
	@Column(name = "PrjCtrlType")
	private String prjCtrlType;
	@Column(name = "OfferCtrl")
	private Double offerCtrl;
	@Column(name = "MaterialCtrl")
	private Double materialCtrl;
	
	@Transient
	private String unSelected;
	@Transient
	private String showOutSet;//1：显示套餐内项目
	@Transient
	private String baseItemDescr;//PrjAPP用 项目名称 add by zb on 20190311
	@Transient
	private String fixAreaDescr;

	
	public String getFixAreaDescr() {
		return fixAreaDescr;
	}

	public void setFixAreaDescr(String fixAreaDescr) {
		this.fixAreaDescr = fixAreaDescr;
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
	public void setMaterial(Double material) {
		this.material = material;
	}
	
	public Double getMaterial() {
		return this.material;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}
	
	public String getIsOutSet() {
		return this.isOutSet;
	}
	public void setPrjCtrlType(String prjCtrlType) {
		this.prjCtrlType = prjCtrlType;
	}
	
	public String getPrjCtrlType() {
		return this.prjCtrlType;
	}
	public void setOfferCtrl(Double offerCtrl) {
		this.offerCtrl = offerCtrl;
	}
	
	public Double getOfferCtrl() {
		return this.offerCtrl;
	}
	public void setMaterialCtrl(Double materialCtrl) {
		this.materialCtrl = materialCtrl;
	}
	
	public Double getMaterialCtrl() {
		return this.materialCtrl;
	}

	public String getUnSelected() {
		return unSelected;
	}

	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}

	public String getShowOutSet() {
		return showOutSet;
	}

	public void setShowOutSet(String showOutSet) {
		this.showOutSet = showOutSet;
	}

	public String getBaseItemDescr() {
		return baseItemDescr;
	}

	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
	}


}
