package com.house.home.entity.commi;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name = "tMainCommiRuleNew")
public class MainCommiRuleNew extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "CommiType")
	private String commiType;
	@Column(name = "ToProfit")
	private Double toProfit;
	@Column(name = "FromProfit")
	private Double fromProfit;
	@Column(name = "IsUpgItem")
	private String isUpgItem;
	@Column(name = "CommiPerc")
	private Double commiPerc;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String detailJson;
	
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCommiType() {
		return commiType;
	}
	public void setCommiType(String commiType) {
		this.commiType = commiType;
	}
	public Double getToProfit() {
		return toProfit;
	}
	public void setToProfit(Double toProfit) {
		this.toProfit = toProfit;
	}
	public Double getFromProfit() {
		return fromProfit;
	}
	public void setFromProfit(Double fromProfit) {
		this.fromProfit = fromProfit;
	}
	public String getIsUpgItem() {
		return isUpgItem;
	}
	public void setIsUpgItem(String isUpgItem) {
		this.isUpgItem = isUpgItem;
	}
	public Double getCommiPerc() {
		return commiPerc;
	}
	public void setCommiPerc(Double commiPerc) {
		this.commiPerc = commiPerc;
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
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
}
