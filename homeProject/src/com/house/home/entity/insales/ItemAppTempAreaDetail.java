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
 * ItemAppTempAreaDetail信息
 */
@Entity
@Table(name = "tItemAppTempAreaDetail")
public class ItemAppTempAreaDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ATANo")
	private String atano;
	@Column(name = "ITCode")
	private String itcode;
	@Column(name = "Qty")
	private Double qty;
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
	@Column(name = "CalType")
	private String calType;// 计算类型

	@Transient
	private String itDescr;// 材料名称
	@Transient
	private String itExpired;//材料过期标志 领料申请模块用 add by zb
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setAtano(String atano) {
		this.atano = atano;
	}
	
	public String getAtano() {
		return this.atano;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	
	public String getItcode() {
		return this.itcode;
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

	public String getItDescr() {
		return itDescr;
	}

	public void setItDescr(String itDescr) {
		this.itDescr = itDescr;
	}

	public String getCalType() {
		return calType;
	}

	public void setCalType(String calType) {
		this.calType = calType;
	}

	public String getItExpired() {
		return itExpired;
	}

	public void setItExpired(String itExpired) {
		this.itExpired = itExpired;
	}

}
