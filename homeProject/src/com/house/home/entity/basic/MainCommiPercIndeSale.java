package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * MainCommiPercIndeSale信息
 */
@Entity
@Table(name = "tMainCommiPerc_IndeSale")
public class MainCommiPercIndeSale extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SaleType")
	private String saleType;
	@Column(name = "MainCommiPerc")
	private Double mainCommiPerc;
	@Column(name = "ServCommiPerc")
	private Double servCommiPerc;
	@Column(name = "ElecCommiPerc")
	private Double elecCommiPerc;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	
	public String getSaleType() {
		return this.saleType;
	}
	public void setMainCommiPerc(Double mainCommiPerc) {
		this.mainCommiPerc = mainCommiPerc;
	}
	
	public Double getMainCommiPerc() {
		return this.mainCommiPerc;
	}
	public void setServCommiPerc(Double servCommiPerc) {
		this.servCommiPerc = servCommiPerc;
	}
	
	public Double getServCommiPerc() {
		return this.servCommiPerc;
	}
	public void setElecCommiPerc(Double elecCommiPerc) {
		this.elecCommiPerc = elecCommiPerc;
	}
	
	public Double getElecCommiPerc() {
		return this.elecCommiPerc;
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
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}

}
