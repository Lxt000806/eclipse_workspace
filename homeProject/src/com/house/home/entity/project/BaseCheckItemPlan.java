package com.house.home.entity.project;

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
 * BaseCheckItemPlan信息
 */
@Entity
@Table(name = "tBaseCheckItemPlan")
public class BaseCheckItemPlan extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "FixAreaPK")
	private Integer fixAreaPk;
	@Column(name = "BaseCheckItemCode")
	private String baseCheckItemCode;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "OfferPri")
	private Double offerPri;
	@Column(name = "Material")
	private Double material;
	@Column(name = "PrjOfferPri")
	private Double prjOfferPri;
	@Column(name = "PrjMaterial")
	private Double prjMaterial;
	@Column(name = "Remark")
	private String remark;
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

	@Transient
	private String address;
	@Transient
	private String custType;
	@Transient
	private String custStatus;
	@Transient
	private String itemType1;
	@Transient
	private String baseItemType1;
	@Transient
	private String rowId;
	@Transient
	private String baseCheckItemPlanJson;
	@Transient
	private Date signDateFrom;
	@Transient
	private Date signDateTo;
	@Transient
	private Date confirmBeginFrom;
	@Transient
	private Date confirmBeginTo;
	@Transient
	private String dppStatus;
	@Transient
	private Date checkPlanDateFrom;
	@Transient
	private Date checkPlanDateTo;
	
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
	public void setBaseCheckItemCode(String baseCheckItemCode) {
		this.baseCheckItemCode = baseCheckItemCode;
	}
	
	public String getBaseCheckItemCode() {
		return this.baseCheckItemCode;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
	}
	public void setOfferPri(Double offerPri) {
		this.offerPri = offerPri;
	}
	
	public Double getOfferPri() {
		return this.offerPri;
	}
	public void setMaterial(Double material) {
		this.material = material;
	}
	
	public Double getMaterial() {
		return this.material;
	}
	public void setPrjOfferPri(Double prjOfferPri) {
		this.prjOfferPri = prjOfferPri;
	}
	
	public Double getPrjOfferPri() {
		return this.prjOfferPri;
	}
	public void setPrjMaterial(Double prjMaterial) {
		this.prjMaterial = prjMaterial;
	}
	
	public Double getPrjMaterial() {
		return this.prjMaterial;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getBaseItemType1() {
		return baseItemType1;
	}

	public void setBaseItemType1(String baseItemType1) {
		this.baseItemType1 = baseItemType1;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getBaseCheckItemPlanJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(baseCheckItemPlanJson);
    	return xml;
	}

	public void setBaseCheckItemPlanJson(String baseCheckItemPlanJson) {
		this.baseCheckItemPlanJson = baseCheckItemPlanJson;
	}

	public Date getSignDateFrom() {
		return signDateFrom;
	}

	public void setSignDateFrom(Date signDateFrom) {
		this.signDateFrom = signDateFrom;
	}

	public Date getSignDateTo() {
		return signDateTo;
	}

	public void setSignDateTo(Date signDateTo) {
		this.signDateTo = signDateTo;
	}

	public Date getConfirmBeginFrom() {
		return confirmBeginFrom;
	}

	public void setConfirmBeginFrom(Date confirmBeginFrom) {
		this.confirmBeginFrom = confirmBeginFrom;
	}

	public Date getConfirmBeginTo() {
		return confirmBeginTo;
	}

	public void setConfirmBeginTo(Date confirmBeginTo) {
		this.confirmBeginTo = confirmBeginTo;
	}

	public String getDppStatus() {
		return dppStatus;
	}

	public void setDppStatus(String dppStatus) {
		this.dppStatus = dppStatus;
	}

	public Date getCheckPlanDateFrom() {
		return checkPlanDateFrom;
	}

	public void setCheckPlanDateFrom(Date checkPlanDateFrom) {
		this.checkPlanDateFrom = checkPlanDateFrom;
	}

	public Date getCheckPlanDateTo() {
		return checkPlanDateTo;
	}

	public void setCheckPlanDateTo(Date checkPlanDateTo) {
		this.checkPlanDateTo = checkPlanDateTo;
	}

}
