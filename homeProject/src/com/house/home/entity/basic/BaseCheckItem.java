package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name="tBaseCheckItem")
public class BaseCheckItem extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Code")
	private String code;
	@Column(name="Descr")
	private String descr;
	@Column(name="BaseItemType1")
	private String baseItemType1;
	@Column(name="BaseItemType2")
	private String baseItemType2;
	@Column(name="WorkType12")
	private String workType12;
	@Column(name="OfferPri")
	private String offerPri;
	@Column(name="Material")
	private String material;
	@Column(name="PrjOfferPri")
	private String prjOfferPri;
	@Column(name="PrjMaterial")
	private String prjMaterial;
	@Column(name="Remark")
	private String remark;
	@Column(name="Type")
	private String type;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="Uom")
	private String uom;
	@Column(name = "IsFixItem")
	private String isFixItem;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "IsSubsidyItem")
	private String isSubsidyItem;
	
	@Transient
	private String workType12Descr;
	@Transient
	private String workerClassify;
	@Transient
	private String custCode;
	
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getWorkerClassify() {
		return workerClassify;
	}
	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getBaseItemType1() {
		return baseItemType1;
	}
	public void setBaseItemType1(String baseItemType1) {
		this.baseItemType1 = baseItemType1;
	}
	public String getBaseItemType2() {
		return baseItemType2;
	}
	public void setBaseItemType2(String baseItemType2) {
		this.baseItemType2 = baseItemType2;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getOfferPri() {
		return offerPri;
	}
	public void setOfferPri(String offerPri) {
		this.offerPri = offerPri;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getPrjOfferPri() {
		return prjOfferPri;
	}
	public void setPrjOfferPri(String prjOfferPri) {
		this.prjOfferPri = prjOfferPri;
	}
	public String getPrjMaterial() {
		return prjMaterial;
	}
	public void setPrjMaterial(String prjMaterial) {
		this.prjMaterial = prjMaterial;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getIsFixItem() {
		return isFixItem;
	}
	public void setIsFixItem(String isFixItem) {
		this.isFixItem = isFixItem;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getIsSubsidyItem() {
		return isSubsidyItem;
	}
	public void setIsSubsidyItem(String isSubsidyItem) {
		this.isSubsidyItem = isSubsidyItem;
	}
	
}
