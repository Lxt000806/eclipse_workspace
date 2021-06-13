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
@Table(name = "tAssetChg")
public class AssetChg extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "AssetCode")
	private String assetCode;
	@Column(name = "Date")
	private Date date;
	@Column(name = "ChgType")
	private String chgType;
	@Column(name = "BefValue")
	private String befValue;
	@Column(name = "AftValue")
	private String aftValue;
	@Column(name = "BefValue2")
	private String befValue2;
	@Column(name = "AftValue2")
	private String aftValue2;
	@Column(name = "AftAddress")
	private String aftAddress;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "ChgCZY")
	private String chgCZY;
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
	@Transient
	private String department;
	@Transient
	private String assetRemarks;
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getAssetChgDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getBefValue2() {
		return befValue2;
	}
	public void setBefValue2(String befValue2) {
		this.befValue2 = befValue2;
	}
	public String getAftValue2() {
		return aftValue2;
	}
	public void setAftValue2(String aftValue2) {
		this.aftValue2 = aftValue2;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getChgType() {
		return chgType;
	}
	public void setChgType(String chgType) {
		this.chgType = chgType;
	}
	public String getBefValue() {
		return befValue;
	}
	public void setBefValue(String befValue) {
		this.befValue = befValue;
	}
	public String getAftValue() {
		return aftValue;
	}
	public void setAftValue(String aftValue) {
		this.aftValue = aftValue;
	}
	public String getAftAddress() {
		return aftAddress;
	}
	public void setAftAddress(String aftAddress) {
		this.aftAddress = aftAddress;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getChgCZY() {
		return chgCZY;
	}
	public void setChgCZY(String chgCZY) {
		this.chgCZY = chgCZY;
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
	public String getAssetRemarks() {
		return assetRemarks;
	}
	public void setAssetRemarks(String assetRemarks) {
		this.assetRemarks = assetRemarks;
	}

	
}
