package com.house.home.entity.basic;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name = "tUpgWithhold")
public class UpgWithhold extends BaseEntity{

	private static final long serialVersionUID = 1L;
@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "ExistCal")
	private String existCal;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "CalType")
	private String calType;
	@Column(name = "CalAmount")
	private Double calAmount;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "Area")
	private Integer area;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "AreaTo")
	private Double areaTo;
	@Column(name = "Layout")
	private String layout;
	
	@Transient
	private String custTypeDescr;
	@Transient
	private String itemType1Descr;
	@Transient
	private String calTypeDescr;
	@Transient
	private String existCalDescr;
	@Transient
	private String itemDescr;
	@Transient
	private String itemCode;
	@Transient
	private String detailJson;
	
	
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getCustTypeDescr() {
		return custTypeDescr;
	}
	public void setCustTypeDescr(String custTypeDescr) {
		this.custTypeDescr = custTypeDescr;
	}
	public String getItemType1Descr() {
		return itemType1Descr;
	}
	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}
	public String getCalTypeDescr() {
		return calTypeDescr;
	}
	public void setCalTypeDescr(String calTypeDescr) {
		this.calTypeDescr = calTypeDescr;
	}
	public String getExistCalDescr() {
		return existCalDescr;
	}
	public void setExistCalDescr(String existCalDescr) {
		this.existCalDescr = existCalDescr;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getExistCal() {
		return existCal;
	}
	public void setExistCal(String existCal) {
		this.existCal = existCal;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getCalType() {
		return calType;
	}
	public void setCalType(String calType) {
		this.calType = calType;
	}
	public Double getCalAmount() {
		return calAmount;
	}
	public void setCalAmount(Double calAmount) {
		this.calAmount = calAmount;
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
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getAreaTo() {
		return areaTo;
	}
	public void setAreaTo(Double areaTo) {
		this.areaTo = areaTo;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	

}
