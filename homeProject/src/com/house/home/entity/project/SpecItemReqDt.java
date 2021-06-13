package com.house.home.entity.project;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tSpecItemReqDt")
public class SpecItemReqDt extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "PK")
	private Integer pK;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "IntProdPK")
	private Integer intProdPK;
	@Column(name = "IsCupboard")
	private String isCupboard;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "Cost")
	private Double cost;//移动平均成本
	@Column(name = "Remark")
	private String remark;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	
	@Transient
	private String itemType2;//材料类型2
	@Transient
	private String intProdPKDescr;//集成成品名称
	@Transient
	private String itemDescr;//材料名称
	@Transient
	private Double itCost;//成本
	@Transient
	private Double price;//单价
	@Transient
	private Double totalCost;//成本总价
	@Transient
	private Double appQty;//已下单数量
	@Transient
	private Double isExist;//是否存在下单
	
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
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public Integer getIntProdPK() {
		return intProdPK;
	}
	public void setIntProdPK(Integer intProdPK) {
		this.intProdPK = intProdPK;
	}
	public String getIsCupboard() {
		return isCupboard;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getIntProdPKDescr() {
		return intProdPKDescr;
	}
	public void setIntProdPKDescr(String intProdPKDescr) {
		this.intProdPKDescr = intProdPKDescr;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public Double getItCost() {
		return itCost;
	}
	public void setItCost(Double itCost) {
		this.itCost = itCost;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	public Double getAppQty() {
		return appQty;
	}
	public void setAppQty(Double appQty) {
		this.appQty = appQty;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public Double getIsExist() {
		return isExist;
	}
	public void setIsExist(Double isExist) {
		this.isExist = isExist;
	}


}
