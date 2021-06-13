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
import com.house.framework.commons.utils.XmlConverUtil;

@Entity
@Table(name = "tSetMainItemPrice")
public class SetMainItemPrice extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustType")
	private String custType = "";
	@Column(name = "FromArea")
	private Double fromArea = 0.0;
	@Column(name = "ToArea")
	private Double toArea = 0.0;
	@Column(name = "BasePrice")
	private Double basePrice = 0.0;
	@Column(name = "BaseArea")
	private Double baseArea = 0.0;
	@Column(name = "UnitPrice")
	private Double unitPrice = 0.0;
	@Column(name = "BaseToiletCount")
	private Double baseToiletCount = 0.0;
	@Column(name = "ToiletPrice")
	private Double toiletPrice = 0.0;
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

	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustType() {
		return custType.trim();
	}
	public void setCustType(String custType) {
		this.custType = custType.trim();
	}
	public Double getFromArea() {
		return fromArea;
	}
	public void setFromArea(Double fromArea) {
		this.fromArea = fromArea;
	}
	public Double getToArea() {
		return toArea;
	}
	public void setToArea(Double toArea) {
		this.toArea = toArea;
	}
	public Double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
	public Double getBaseArea() {
		return baseArea;
	}
	public void setBaseArea(Double baseArea) {
		this.baseArea = baseArea;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getBaseToiletCount() {
		return baseToiletCount;
	}
	public void setBaseToiletCount(Double baseToiletCount) {
		this.baseToiletCount = baseToiletCount;
	}
	public Double getToiletPrice() {
		return toiletPrice;
	}
	public void setToiletPrice(Double toiletPrice) {
		this.toiletPrice = toiletPrice;
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
	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
}
