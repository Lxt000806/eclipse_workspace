package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * 项目名称信息
 */
@Entity
@Table(name = "tBuilder")
public class Builder extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "GroupCode")
	private String groupCode;
	@Column(name = "AddressType")
	private String addressType;
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
	@Column(name = "DectStatus")
	private String dectStatus;
	@Column(name = "Adress")
	private String adress;
	@Column(name = "HouseNum")
	private Integer houseNum;
	@Column(name = "HouseType")
	private String houseType;
	@Column(name = "MajorArea")
	private String majorArea;
	@Column(name = "ReferPrice")
	private String referPrice;
	@Column(name = "Property")
	private String property;
	@Column(name = "Developer")
	private String developer;
	@Column(name = "RegionCode")
	private String regionCode;
	@Column(name = "DelivDate")
	private Date delivDate;
	@Column(name = "BuilderType")
	private String builderType;
	@Column(name = "longitude")
	private Double longitude;
	@Column(name = "Latitude")
	private Double latitude;
	@Column(name = "RegionCode2")
	private String regionCode2;
	@Column(name = "alias")
	private String alias;
	@Column(name = "IsPrjSpc")
	private String isPrjSpc;
	@Column(name = "SpcBuilder")
	private String spcBuilder;
	@Column(name = "SpcSeq")
	private Integer spcSeq;
	@Column(name = "Offset")
	private Integer offset;
	@Column(name = "PrjLeader")
	private String prjLeader;
	@Column(name = "HouseTypeOld")
	private String houseTypeOld;
	@Column(name = "SendRegion")
	private String sendRegion;
	@Column(name = "SplSendRegion")
	private String splSendRegion;
	
	@Transient
	private String groupCodeDescr;
	@Transient
	private String businessMan;
	@Transient
	private String department2;
	@Transient
	private Date delivDateFrom;
	@Transient
	private Date delivDateTo;
	@Transient
	private String AddressTypeDescr;
	@Transient
	private String BuilderTypeDescr;
	@Transient
	private String DectStatusDescr;
	@Transient
	private String RegionDescr;
	@Transient
	private String HouseTypeDescr;
	@Transient
	private String Region2Descr;
	@Transient
	private String IsPrjSpcdescr;
	@Transient
	private String tude;//经纬度
	@Transient
	private String onlyShowSendRegionNull;//只显示配送区域为空
	
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getSpcBuilder() {
		return spcBuilder;
	}

	public void setSpcBuilder(String spcBuilder) {
		this.spcBuilder = spcBuilder;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getBusinessMan() {
		return businessMan;
	}

	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getAddressType() {
		return this.addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getExpired() {
		return this.expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public String getActionLog() {
		return this.actionLog;
	}

	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}

	public String getGroupCodeDescr() {
		return groupCodeDescr;
	}

	public void setGroupCodeDescr(String groupCodeDescr) {
		this.groupCodeDescr = groupCodeDescr;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	public String getAddressTypeDescr() {
		return AddressTypeDescr;
	}

	public void setAddressTypeDescr(String addressTypeDescr) {
		AddressTypeDescr = addressTypeDescr;
	}

	public String getBuilderTypeDescr() {
		return BuilderTypeDescr;
	}

	public void setBuilderTypeDescr(String builderTypeDescr) {
		BuilderTypeDescr = builderTypeDescr;
	}

	public String getDectStatusDescr() {
		return DectStatusDescr;
	}

	public void setDectStatusDescr(String dectStatusDescr) {
		DectStatusDescr = dectStatusDescr;
	}

	public String getRegionDescr() {
		return RegionDescr;
	}

	public void setRegionDescr(String regionDescr) {
		RegionDescr = regionDescr;
	}

	public String getHouseTypeDescr() {
		return HouseTypeDescr;
	}

	public void setHouseTypeDescr(String houseTypeDescr) {
		HouseTypeDescr = houseTypeDescr;
	}

	public String getRegion2Descr() {
		return Region2Descr;
	}

	public void setRegion2Descr(String region2Descr) {
		Region2Descr = region2Descr;
	}

	public String getIsPrjSpcdescr() {
		return IsPrjSpcdescr;
	}

	public void setIsPrjSpcdescr(String isPrjSpcdescr) {
		IsPrjSpcdescr = isPrjSpcdescr;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getTude() {
		return tude;
	}

	public void setTude(String tude) {
		this.tude = tude;
	}

	public String getDectStatus() {
		return dectStatus;
	}

	public void setDectStatus(String dectStatus) {
		this.dectStatus = dectStatus;
	}

	public Integer getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(Integer houseNum) {
		this.houseNum = houseNum;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getMajorArea() {
		return majorArea;
	}

	public void setMajorArea(String majorArea) {
		this.majorArea = majorArea;
	}

	public String getReferPrice() {
		return referPrice;
	}

	public void setReferPrice(String referPrice) {
		this.referPrice = referPrice;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public Date getDelivDate() {
		return delivDate;
	}

	public void setDelivDate(Date delivDate) {
		this.delivDate = delivDate;
	}

	public String getBuilderType() {
		return builderType;
	}

	public void setBuilderType(String builderType) {
		this.builderType = builderType;
	}

	public String getRegionCode2() {
		return regionCode2;
	}

	public void setRegionCode2(String regionCode2) {
		this.regionCode2 = regionCode2;
	}

	public String getIsPrjSpc() {
		return isPrjSpc;
	}

	public void setIsPrjSpc(String isPrjSpc) {
		this.isPrjSpc = isPrjSpc;
	}

	public Integer getSpcSeq() {
		return spcSeq;
	}

	public void setSpcSeq(Integer spcSeq) {
		this.spcSeq = spcSeq;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getPrjLeader() {
		return prjLeader;
	}

	public void setPrjLeader(String prjLeader) {
		this.prjLeader = prjLeader;
	}

	public String getHouseTypeOld() {
		return houseTypeOld;
	}

	public void setHouseTypeOld(String houseTypeOld) {
		this.houseTypeOld = houseTypeOld;
	}

	public String getSendRegion() {
		return sendRegion;
	}

	public void setSendRegion(String sendRegion) {
		this.sendRegion = sendRegion;
	}

	public String getSplSendRegion() {
		return splSendRegion;
	}

	public void setSplSendRegion(String splSendRegion) {
		this.splSendRegion = splSendRegion;
	}

	public String getOnlyShowSendRegionNull() {
		return onlyShowSendRegionNull;
	}

	public void setOnlyShowSendRegionNull(String onlyShowSendRegionNull) {
		this.onlyShowSendRegionNull = onlyShowSendRegionNull;
	}
	
}
