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
 * MainCommiRule信息
 */
@Entity
@Table(name = "tMainCommiRule")
public class MainCommiRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CommiType")
	private String commiType;
	@Column(name = "FromProfit")
	private Double fromProfit;
	@Column(name = "ToProfit")
	private Double toProfit;
	@Column(name = "CommiPerc")
	private Double commiPerc;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	@Transient
	private String commiTypeDescr;
	@Transient
	private String itemType1;
	@Transient
	private String itemType2;
	@Transient
	private String itemType3;
	@Transient
	private String itemType2Descr;
	@Transient
	private String itemType3Descr;
	@Transient
	private String existCalDescr;
	@Transient
	private String detailJson;
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setCommiType(String commiType) {
		this.commiType = commiType;
	}
	
	public String getCommiType() {
		return this.commiType;
	}
	public void setFromProfit(Double fromProfit) {
		this.fromProfit = fromProfit;
	}
	
	public Double getFromProfit() {
		return this.fromProfit;
	}
	public void setToProfit(Double toProfit) {
		this.toProfit = toProfit;
	}
	
	public Double getToProfit() {
		return this.toProfit;
	}
	public void setCommiPerc(Double commiPerc) {
		this.commiPerc = commiPerc;
	}
	
	public Double getCommiPerc() {
		return this.commiPerc;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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

	public String getCommiTypeDescr() {
		return commiTypeDescr;
	}

	public void setCommiTypeDescr(String commiTypeDescr) {
		this.commiTypeDescr = commiTypeDescr;
	}

	public String getItemType2Descr() {
		return itemType2Descr;
	}

	public void setItemType2Descr(String itemType2Descr) {
		this.itemType2Descr = itemType2Descr;
	}

	public String getItemType3Descr() {
		return itemType3Descr;
	}

	public void setItemType3Descr(String itemType3Descr) {
		this.itemType3Descr = itemType3Descr;
	}

	public String getExistCalDescr() {
		return existCalDescr;
	}

	public void setExistCalDescr(String existCalDescr) {
		this.existCalDescr = existCalDescr;
	}

	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getItemType3() {
		return itemType3;
	}

	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}

}
