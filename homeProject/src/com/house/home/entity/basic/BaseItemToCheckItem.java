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

@Entity
@Table(name = "tBaseItemToCheckItem")
public class BaseItemToCheckItem extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "BaseItemCode")
	private String baseItemCode;
	@Column(name = "BaseCheckItemCode")
	private String baseCheckItemCode;
	@Column(name = "CalType")
	private String calType;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String baseItemDescr;// 基础项目
	@Transient
	private String baseCheckItemDescr;// 基础结算项目
	@Transient
	private String baseItemType1;// 基装类型1
	@Transient
	private String baseItemType2;// 基装类型2
	

	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getBaseItemCode() {
		return baseItemCode;
	}
	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}
	public String getBaseCheckItemCode() {
		return baseCheckItemCode;
	}
	public void setBaseCheckItemCode(String baseCheckItemCode) {
		this.baseCheckItemCode = baseCheckItemCode;
	}
	public String getCalType() {
		return calType;
	}
	public void setCalType(String calType) {
		this.calType = calType;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
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
	public String getBaseItemDescr() {
		return baseItemDescr;
	}
	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
	}
	public String getBaseCheckItemDescr() {
		return baseCheckItemDescr;
	}
	public void setBaseCheckItemDescr(String baseCheckItemDescr) {
		this.baseCheckItemDescr = baseCheckItemDescr;
	}


}
