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
 * ItemSet信息
 */
@Entity
@Table(name = "tItemType12")
public class ItemType12 extends BaseEntity {
	
	private static final long serialVersionUID = 1L;	
    	@Id
	@Column(name = "Code")
	private String Code;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "Descr")
	private String descr;	
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "DispSeq")
	private int dispseq;
	@Column(name = "ProPer")
	private float proper;
	@Column(name = "TransFeeType")
	private String transFeeType;
	@Column(name = "InstallFeeType")
	private String installFeeType;
	@Transient
	private String detailJson;
	@Transient
	private String descr1;
	@Transient
	private String transFeeTypeDescr;
	@Transient
	private String installFeeTypeDescr;
	public String getDescr1() {
		return descr1;
	}
	public void setDescr1(String descr1) {
		this.descr1 = descr1;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public int getDispseq() {
		return dispseq;
	}
	public void setDispseq(int dispseq) {
		this.dispseq = dispseq;
	}
	public float getProper() {
		return proper;
	}
	public void setProper(float proper) {
		this.proper = proper;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTransFeeType() {
		return transFeeType;
	}
	public void setTransFeeType(String transFeeType) {
		this.transFeeType = transFeeType;
	}
	public String getInstallFeeType() {
		return installFeeType;
	}
	public void setInstallFeeType(String installFeeType) {
		this.installFeeType = installFeeType;
	}
	public String getTransFeeTypeDescr() {
		return transFeeTypeDescr;
	}
	public void setTransFeeTypeDescr(String transFeeTypeDescr) {
		this.transFeeTypeDescr = transFeeTypeDescr;
	}
	public String getInstallFeeTypeDescr() {
		return installFeeTypeDescr;
	}
	public void setInstallFeeTypeDescr(String installFeeTypeDescr) {
		this.installFeeTypeDescr = installFeeTypeDescr;
	}


}
