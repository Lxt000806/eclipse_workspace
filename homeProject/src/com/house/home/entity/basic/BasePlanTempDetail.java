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
 * BasePlanTempDetail信息
 */
@Entity
@Table(name = "tBasePlanTempDetail")
public class BasePlanTempDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "FixAreaType")
	private String fixAreaType;
	@Column(name = "BaseItemCode")
	private String baseItemCode;
	@Column(name = "BaseAlgorithm")
	private String baseAlgorithm;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "IsOutSet")
	private String isOutSet;
	@Column(name = "IsRequired")
	private String isRequired;
	@Column(name = "CanReplace")
	private String canReplace;
	@Column(name = "CanModiQty")
	private String canModiQty;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "PaveTile")
	private String paveTile;
	@Column(name = "PaveFloor")
	private String paveFloor;
	@Column(name = "PaveDiamondFloor")
	private String paveDiamondFloor;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Transient
	private String baseItemDescr;
	@Transient
	private String isOutSetDescr;
	@Transient
	private String fixAreaTypeDescr;
	@Transient
	private String custType;

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
	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}
	
	public String getBaseItemCode() {
		return this.baseItemCode;
	}
	public void setBaseAlgorithm(String baseAlgorithm) {
		this.baseAlgorithm = baseAlgorithm;
	}
	
	public String getBaseAlgorithm() {
		return this.baseAlgorithm;
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
	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}
	
	public String getIsOutSet() {
		return this.isOutSet;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	
	public String getIsRequired() {
		return this.isRequired;
	}
	public void setCanReplace(String canReplace) {
		this.canReplace = canReplace;
	}
	
	public String getCanReplace() {
		return this.canReplace;
	}
	public void setCanModiQty(String canModiQty) {
		this.canModiQty = canModiQty;
	}
	
	public String getCanModiQty() {
		return this.canModiQty;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setPaveTile(String paveTile) {
		this.paveTile = paveTile;
	}
	
	public String getPaveTile() {
		return this.paveTile;
	}
	public void setPaveFloor(String paveFloor) {
		this.paveFloor = paveFloor;
	}
	
	public String getPaveFloor() {
		return this.paveFloor;
	}
	public void setPaveDiamondFloor(String paveDiamondFloor) {
		this.paveDiamondFloor = paveDiamondFloor;
	}
	
	public String getPaveDiamondFloor() {
		return this.paveDiamondFloor;
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

	public String getBaseItemDescr() {
		return baseItemDescr;
	}

	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
	}

	public String getIsOutSetDescr() {
		return isOutSetDescr;
	}

	public void setIsOutSetDescr(String isOutSetDescr) {
		this.isOutSetDescr = isOutSetDescr;
	}

	public String getFixAreaTypeDescr() {
		return fixAreaTypeDescr;
	}

	public void setFixAreaTypeDescr(String fixAreaTypeDescr) {
		this.fixAreaTypeDescr = fixAreaTypeDescr;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

}
