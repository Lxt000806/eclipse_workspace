package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

@Entity
@Table(name = "tSpcBuilder")
public class SpcBuilder extends BaseEntity{

private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Code")
	private String code;
	@Column(name="Descr")
	private String descr;
	@Column(name="Remarks")
	private String remarks;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="LeaderCode")
	private String leaderCode;
	@Column (name="Type")
	private String type;
	@Column(name="BuilderType")
	private String builderType;
	@Column(name="TotalQty")
	private String totalQty;
	@Column (name="DelivQty")
	private String delivQty;
	@Column(name="TotalBeginQty")
	private String totalBeginQty;
	@Column(name="SelfDecorQty")
	private String selfDecorQty;
	@Column(name="DecorCmpBeginQty")
	private String decorCmpBeginQty;
	@Column(name="DecorCmp1")
	private String decorCmp1;
	@Column(name="DecorCmp1Qty")
	private String decorCmp1Qty;
	@Column(name="DecorCmp2")
	private String decorCmp2;
	@Column(name="DecorCmp2Qty")
	private String decorCmp2Qty;
	@Column(name="DecorCmp3")
	private String decorCmp3;
	@Column(name="DecorCmp3Qty")
	private String decorCmp3Qty;
	@Column(name="DecorCmp4")
	private String decorCmp4;
	@Column(name="DecorCmp4Qty")
	private String decorCmp4Qty;
	@Column(name="DecorCmp5")
	private String decorCmp5;
	@Column(name="DecorCmp5Qty")
	private String decorCmp5Qty;
	@Column(name="DelivDate")
	private Date delivDate;
	
	@Transient
	private String detailJson;
	@Transient
	private String temp;
	@Transient
	private String hasAdd;
	@Transient
	public Date delivDateFrom;
	@Transient
	public Date delivDateTo;
	@Transient
	private String  spcBuilderAnalysisType; //专盘分析类型
	
	public String getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(String totalQty) {
		this.totalQty = totalQty;
	}
	public String getDelivQty() {
		return delivQty;
	}
	public void setDelivQty(String delivQty) {
		this.delivQty = delivQty;
	}
	public String getTotalBeginQty() {
		return totalBeginQty;
	}
	public void setTotalBeginQty(String totalBeginQty) {
		this.totalBeginQty = totalBeginQty;
	}
	public String getSelfDecorQty() {
		return selfDecorQty;
	}
	public void setSelfDecorQty(String selfDecorQty) {
		this.selfDecorQty = selfDecorQty;
	}
	public String getDecorCmpBeginQty() {
		return decorCmpBeginQty;
	}
	public void setDecorCmpBeginQty(String decorCmpBeginQty) {
		this.decorCmpBeginQty = decorCmpBeginQty;
	}
	public String getDecorCmp1Qty() {
		return decorCmp1Qty;
	}
	public void setDecorCmp1Qty(String decorCmp1Qty) {
		this.decorCmp1Qty = decorCmp1Qty;
	}
	public String getDecorCmp2Qty() {
		return decorCmp2Qty;
	}
	public void setDecorCmp2Qty(String decorCmp2Qty) {
		this.decorCmp2Qty = decorCmp2Qty;
	}
	public String getDecorCmp3Qty() {
		return decorCmp3Qty;
	}
	public void setDecorCmp3Qty(String decorCmp3Qty) {
		this.decorCmp3Qty = decorCmp3Qty;
	}
	public String getDecorCmp4Qty() {
		return decorCmp4Qty;
	}
	public void setDecorCmp4Qty(String decorCmp4Qty) {
		this.decorCmp4Qty = decorCmp4Qty;
	}
	public String getDecorCmp5Qty() {
		return decorCmp5Qty;
	}
	public void setDecorCmp5Qty(String decorCmp5Qty) {
		this.decorCmp5Qty = decorCmp5Qty;
	}
	public Date getDelivDate() {
		return delivDate;
	}
	public void setDelivDate(Date delivDate) {
		this.delivDate = delivDate;
	}
	public String getLeaderCode() {
		return leaderCode;
	}
	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}
	public String getDecorCmp1() {
		return decorCmp1;
	}
	public void setDecorCmp1(String decorCmp1) {
		this.decorCmp1 = decorCmp1;
	}
	
	public String getDecorCmp2() {
		return decorCmp2;
	}
	public void setDecorCmp2(String decorCmp2) {
		this.decorCmp2 = decorCmp2;
	}
	
	public String getDecorCmp3() {
		return decorCmp3;
	}
	public void setDecorCmp3(String decorCmp3) {
		this.decorCmp3 = decorCmp3;
	}
	
	public String getDecorCmp4() {
		return decorCmp4;
	}
	public void setDecorCmp4(String decorCmp4) {
		this.decorCmp4 = decorCmp4;
	}
	
	public String getDecorCmp5() {
		return decorCmp5;
	}
	public void setDecorCmp5(String decorCmp5) {
		this.decorCmp5 = decorCmp5;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getHasAdd() {
		return hasAdd;
	}
	public void setHasAdd(String hasAdd) {
		this.hasAdd = hasAdd;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBuilderType() {
		return builderType;
	}
	public void setBuilderType(String builderType) {
		this.builderType = builderType;
	}
	public Date getDelivDateFrom() {
		return delivDateFrom;
	}
	public void setDelivDateFrom(Date delivDateFrom) {
		this.delivDateFrom = delivDateFrom;
	}
	public Date getDelivDateTo() {
		return delivDateTo;
	}
	public void setDelivDateTo(Date delivDateTo) {
		this.delivDateTo = delivDateTo;
	}
	public String getSpcBuilderAnalysisType() {
		return spcBuilderAnalysisType;
	}
	public void setSpcBuilderAnalysisType(String spcBuilderAnalysisType) {
		this.spcBuilderAnalysisType = spcBuilderAnalysisType;
	}
	
	
	
}
