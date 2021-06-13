package com.house.home.entity.driver;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * ItemReturn信息
 */
@Entity
@Table(name = "tItemReturn")
public class ItemReturn extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "Date")
	private Date date;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "DriverCode")
	private String driverCode;
	@Column(name = "SendBatchNo")
	private String sendBatchNo;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "GetDate")
	private Date getDate;
	@Column(name = "CompleteDate")
	private Date completeDate;
	@Column(name = "CarryFee")
	private Double carryFee;
	@Column(name = "TransFee")
	private Double transFee;
	@Column(name = "AutoCarryFee")
	private Double autoCarryFee;
	@Column(name = "Region")
	private String region;
	@Column(name = "CarryType")
	private String carryType;
	@Column(name = "Floor")
	private Integer floor;
	@Column(name = "AutoTransFee")
	private Double autoTransFee;

	@Transient
	private String address;
	@Transient
	private String projectMan;
	@Transient
	private String nos;
	@Transient
	private String ItemRight;
	@Transient
	private String RegionDescr;
	@Transient
	private String CarryTypeDescr;
	@Transient
	private String r_no;
	@Transient
	private String r_sendBatchNo;
	@Transient
	private String r_address;
	@Transient
	private String r_driverCode;
	@Transient
	private String r_itemType1;
	@Transient
	private String r_status;
	@Transient
	private Date r_dateFrom;
	@Transient
	private Date r_dateTo;
	@Transient
	private String itemReturnJson;
	@Transient
	private String times;
	@Transient
	private Double totalPerWeight;
	@Transient
	private Date arriveDateFrom;
	@Transient
	private Date arriveDateTo;
	@Transient
	private Date createDateFrom;
	@Transient
	private Date createDateTo;
	public void setNo(String no) {
		this.no = no;
	}

	public String getNo() {
		return this.no;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getItemType1() {
		return this.itemType1;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getCustCode() {
		return this.custCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}

	public String getAppCzy() {
		return this.appCzy;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return this.date;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverCode() {
		return this.driverCode;
	}

	public void setSendBatchNo(String sendBatchNo) {
		this.sendBatchNo = sendBatchNo;
	}

	public String getSendBatchNo() {
		return this.sendBatchNo;
	}

	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}

	public String getActionLog() {
		return this.actionLog;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

	public Date getGetDate() {
		return getDate;
	}

	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getNos() {
		return nos;
	}

	public void setNos(String nos) {
		this.nos = nos;
	}

	public Double getCarryFee() {
		return carryFee;
	}

	public void setCarryFee(Double carryFee) {
		this.carryFee = carryFee;
	}

	public Double getTransFee() {
		return transFee;
	}

	public void setTransFee(Double transFee) {
		this.transFee = transFee;
	}

	public Double getAutoCarryFee() {
		return autoCarryFee;
	}

	public void setAutoCarryFee(Double autoCarryFee) {
		this.autoCarryFee = autoCarryFee;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCarryType() {
		return carryType;
	}

	public void setCarryType(String carryType) {
		this.carryType = carryType;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Double getAutoTransFee() {
		return autoTransFee;
	}

	public void setAutoTransFee(Double autoTransFee) {
		this.autoTransFee = autoTransFee;
	}

	public String getItemRight() {
		return ItemRight;
	}

	public void setItemRight(String itemRight) {
		ItemRight = itemRight;
	}

	public String getRegionDescr() {
		return RegionDescr;
	}

	public void setRegionDescr(String regionDescr) {
		RegionDescr = regionDescr;
	}

	public String getCarryTypeDescr() {
		return CarryTypeDescr;
	}

	public void setCarryTypeDescr(String carryTypeDescr) {
		CarryTypeDescr = carryTypeDescr;
	}

	public String getR_no() {
		return r_no;
	}

	public void setR_no(String r_no) {
		this.r_no = r_no;
	}

	public String getR_sendBatchNo() {
		return r_sendBatchNo;
	}

	public void setR_sendBatchNo(String r_sendBatchNo) {
		this.r_sendBatchNo = r_sendBatchNo;
	}

	public String getR_address() {
		return r_address;
	}

	public void setR_address(String r_address) {
		this.r_address = r_address;
	}

	public String getR_driverCode() {
		return r_driverCode;
	}

	public void setR_driverCode(String r_driverCode) {
		this.r_driverCode = r_driverCode;
	}

	public String getR_itemType1() {
		return r_itemType1;
	}

	public void setR_itemType1(String r_itemType1) {
		this.r_itemType1 = r_itemType1;
	}

	public Date getR_dateFrom() {
		return r_dateFrom;
	}

	public void setR_dateFrom(Date r_dateFrom) {
		this.r_dateFrom = r_dateFrom;
	}

	public Date getR_dateTo() {
		return r_dateTo;
	}

	public void setR_dateTo(Date r_dateTo) {
		this.r_dateTo = r_dateTo;
	}

	public String getR_status() {
		return r_status;
	}

	public void setR_status(String r_status) {
		this.r_status = r_status;
	}

	public String getItemReturnJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(itemReturnJson);
    	return xml;
	}

	public void setItemReturnJson(String itemReturnJson) {
		this.itemReturnJson = itemReturnJson;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public Double getTotalPerWeight() {
		return totalPerWeight;
	}

	public void setTotalPerWeight(Double totalPerWeight) {
		this.totalPerWeight = totalPerWeight;
	}

	public Date getArriveDateFrom() {
		return arriveDateFrom;
	}

	public void setArriveDateFrom(Date arriveDateFrom) {
		this.arriveDateFrom = arriveDateFrom;
	}

	public Date getArriveDateTo() {
		return arriveDateTo;
	}

	public void setArriveDateTo(Date arriveDateTo) {
		this.arriveDateTo = arriveDateTo;
	}

	public Date getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Date getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}

}
