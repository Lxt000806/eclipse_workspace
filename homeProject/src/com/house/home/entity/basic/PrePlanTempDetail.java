package com.house.home.entity.basic;

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
 * PrePlanTempDetail信息
 */
@Entity
@Table(name = "tPrePlanTempDetail")
public class PrePlanTempDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "FixAreaType")
	private String fixAreaType;
	@Column(name = "ItemTypeDescr")
	private String itemTypeDescr;
	@Column(name = "Algorithm")
	private String algorithm;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "IsService")
	private Integer isService;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ProcessCost")
	private Integer processCost;
	@Column(name = "Markup")
	private Double markup;
	@Column(name = "IsOutSet")
	private String isOutSet;
	@Column(name = "CustTypeItemPK")
	private Integer custTypeItemPk;
	@Column(name = "CutType")
	private String cutType;
	@Column(name = "PaveType")
	private String paveType;
	@Column(name = "AlgorithmPer")
	private Double algorithmPer;
	@Column(name = "AlgorithmDeduct")
	private Double algorithmDeduct;
	@Column(name = "CanModiQty")
	private String canModiQty;
	
	@Transient
	private String fixAreaTypeDescr;
	@Transient
	private String algorithmDescr;
	@Transient
	private String itemDescr;
	@Transient
	private String isServiceDescr;
	@Transient
	private String isOutSetDescr;
	@Transient
	private String custType;
	@Transient
	private String cutTypeDescr;
	@Transient
	private String itemExpired;
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setFixAreaType(String fixAreaType) {
		this.fixAreaType = fixAreaType;
	}
	
	public String getFixAreaType() {
		return this.fixAreaType;
	}
	public void setItemTypeDescr(String itemTypeDescr) {
		this.itemTypeDescr = itemTypeDescr;
	}
	
	public String getItemTypeDescr() {
		return this.itemTypeDescr;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	
	public String getAlgorithm() {
		return this.algorithm;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemCode() {
		return this.itemCode;
	}
	public void setIsService(Integer isService) {
		this.isService = isService;
	}
	
	public Integer getIsService() {
		return this.isService;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setProcessCost(Integer processCost) {
		this.processCost = processCost;
	}
	
	public Integer getProcessCost() {
		return this.processCost;
	}
	public void setMarkup(Double markup) {
		this.markup = markup;
	}
	
	public Double getMarkup() {
		return this.markup;
	}
	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}
	
	public String getIsOutSet() {
		return this.isOutSet;
	}
	public void setCustTypeItemPk(Integer custTypeItemPk) {
		this.custTypeItemPk = custTypeItemPk;
	}
	
	public Integer getCustTypeItemPk() {
		return this.custTypeItemPk;
	}

	public String getFixAreaTypeDescr() {
		return fixAreaTypeDescr;
	}

	public void setFixAreaTypeDescr(String fixAreaTypeDescr) {
		this.fixAreaTypeDescr = fixAreaTypeDescr;
	}

	public String getAlgorithmDescr() {
		return algorithmDescr;
	}

	public void setAlgorithmDescr(String algorithmDescr) {
		this.algorithmDescr = algorithmDescr;
	}

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public String getIsServiceDescr() {
		return isServiceDescr;
	}

	public void setIsServiceDescr(String isServiceDescr) {
		this.isServiceDescr = isServiceDescr;
	}

	public String getIsOutSetDescr() {
		return isOutSetDescr;
	}

	public void setIsOutSetDescr(String isOutSetDescr) {
		this.isOutSetDescr = isOutSetDescr;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCutType() {
		return cutType;
	}

	public void setCutType(String cutType) {
		this.cutType = cutType;
	}

	public String getCutTypeDescr() {
		return cutTypeDescr;
	}

	public void setCutTypeDescr(String cutTypeDescr) {
		this.cutTypeDescr = cutTypeDescr;
	}

	public String getItemExpired() {
		return itemExpired;
	}

	public void setItemExpired(String itemExpired) {
		this.itemExpired = itemExpired;
	}

	public String getPaveType() {
		return paveType;
	}

	public void setPaveType(String paveType) {
		this.paveType = paveType;
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

	public String getCanModiQty() {
		return canModiQty;
	}

	public void setCanModiQty(String canModiQty) {
		this.canModiQty = canModiQty;
	}
		
}
