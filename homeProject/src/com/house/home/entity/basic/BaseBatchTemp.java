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
 * BaseBatchTemp信息
 */
@Entity
@Table(name = "tBaseBatchTemp")
public class BaseBatchTemp extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Transient
	private String custCode;
	@Transient
	private String areaNo;
	@Transient
	private String areaDescr;
	@Transient
	private String areaType;
	@Transient
	private String isOutSet;
	@Transient
	private String isOutSetDescr;
	@Transient
	private String baseItemCode;
	@Transient
	private String baseItemDescr;
	@Transient
	private Double qty;
	@Transient
	private Integer pk;
	@Transient
	private String baseBatchTempItemJson;
	@Transient
	private String areaTypeDescr;
	@Transient
	private Integer dispSeq;
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
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

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getAreaDescr() {
		return areaDescr;
	}

	public void setAreaDescr(String areaDescr) {
		this.areaDescr = areaDescr;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getIsOutSet() {
		return isOutSet;
	}

	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}

	public String getIsOutSetDescr() {
		return isOutSetDescr;
	}

	public void setIsOutSetDescr(String isOutSetDescr) {
		this.isOutSetDescr = isOutSetDescr;
	}

	public String getBaseItemCode() {
		return baseItemCode;
	}

	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}

	public String getBaseItemDescr() {
		return baseItemDescr;
	}

	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getBaseBatchTempItemJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(baseBatchTempItemJson);
    	return xml;
	}

	public void setBaseBatchTempItemJson(String baseBatchTempItemJson) {
		this.baseBatchTempItemJson = baseBatchTempItemJson;
	}

	public String getAreaTypeDescr() {
		return areaTypeDescr;
	}

	public void setAreaTypeDescr(String areaTypeDescr) {
		this.areaTypeDescr = areaTypeDescr;
	}

	public Integer getDispSeq() {
		return dispSeq;
	}

	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
}
