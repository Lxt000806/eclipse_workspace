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
@Table(name = "tCustTypeItem")
public class CustTypeItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="PK")
	private Integer pk;
	@Column(name="CustType")
	private String custType;
	@Column(name="ItemCode")
	private String itemCode;
	@Column (name="Price")
	private Double price;
	@Column (name="ProjectCost")
	private double projectCost;
	@Column (name="Remark")
	private String remark;
	@Column (name="LastUpdate")
	private Date lastUpdate;
	@Column (name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column (name="Expired")
	private String expired;
	@Column (name="ActionLog")
	private String actionLog;
	@Column (name="DiscAmtCalcType")
	private String discAmtCalcType;
	@Column (name="FixAreaType")
	private String fixAreaType;
	
	@Transient
	private String itemDescr;
	@Transient
	private String itemTypeDescr;
	@Transient
	private String lastUpdatedByDescr;
	@Transient
	private String detailJson;
	@Transient
	private String itemType1;
	@Transient
	private double oldPrice;
	@Transient
	private double oldProjectCost;
	@Transient
	private String oldRemars;
	@Transient
	private String itemRight;
	@Transient
	private String supplCode;
	@Transient
	private Integer prePlanAreaPk;
	@Transient
	private String pks;

	public String getPks() {
		return pks;
	}
	public void setPks(String pks) {
		this.pks = pks;
	}
	public Integer getPrePlanAreaPk() {
		return prePlanAreaPk;
	}
	public void setPrePlanAreaPk(Integer prePlanAreaPk) {
		this.prePlanAreaPk = prePlanAreaPk;
	}
	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	public String getItemRight() {
		return itemRight;
	}
	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}
	public double getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(double oldPrice) {
		this.oldPrice = oldPrice;
	}
	public double getOldProjectCost() {
		return oldProjectCost;
	}
	public void setOldProjectCost(double oldProjectCost) {
		this.oldProjectCost = oldProjectCost;
	}
	public String getOldRemars() {
		return oldRemars;
	}
	public void setOldRemars(String oldRemars) {
		this.oldRemars = oldRemars;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getLastUpdatedByDescr() {
		return lastUpdatedByDescr;
	}
	public void setLastUpdatedByDescr(String lastUpdatedByDescr) {
		this.lastUpdatedByDescr = lastUpdatedByDescr;
	}
	public String getItemTypeDescr() {
		return itemTypeDescr;
	}
	public void setItemTypeDescr(String itemTypeDescr) {
		this.itemTypeDescr = itemTypeDescr;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getProjectCost() {
		return projectCost;
	}
	public void setProjectCost(double projectCost) {
		this.projectCost = projectCost;
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
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustTypeItemDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDiscAmtCalcType() {
		return discAmtCalcType;
	}
	public void setDiscAmtCalcType(String discAmtCalcType) {
		this.discAmtCalcType = discAmtCalcType;
	}
	public String getFixAreaType() {
		return fixAreaType;
	}
	public void setFixAreaType(String fixAreaType) {
		this.fixAreaType = fixAreaType;
	}
	
}
