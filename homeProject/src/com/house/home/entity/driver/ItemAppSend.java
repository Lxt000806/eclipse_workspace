package com.house.home.entity.driver;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name="tItemAppSend")
@SuppressWarnings("serial")
public class ItemAppSend extends BaseEntity {
	@Id
	@Column(name="No",nullable=false)
	private String no;
	@Column(name="IANo")
	private String iaNo;
	@Column(name="WHCode")
	private String whCode;
	@Column(name="WHCheckOutNo")
	private String whCheckOutNo;
	
	@Column(name = "Date")
	private Date date;
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
	@Column(name = "IsCheckOut")
	private String isCheckOut;
	@Column(name = "CheckSeq")
	private Integer checkSeq;
	@Column(name = "SupplCode")
	private String supplCode;
	@Column(name = "SendBatchNo")
	private String sendBatchNo;
	@Column(name = "SendStatus")
	private String sendStatus;
	@Column(name = "DriverCode")
	private String driverCode;
	@Column(name = "DriverRemark")
	private String driverRemark;
	@Column(name = "ArriveDate")
	private Date arriveDate;
	@Column(name = "SendFee")
	private Double sendFee;
	@Column(name = "CarryFee")
	private Double carryFee;
	@Column(name = "IsConfirm")
	private String isConfirm;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "ProjectManRemark")
	private String projectManRemark;
	@Column(name = "ArriveAddress")
	private String arriveAddress;
	@Column(name = "ConfirmMan")
	private String confirmMan;
	@Column(name = "ConfirmStatus")
	private String confirmStatus;
	@Column(name = "ConfirmReason")
	private String confirmReason;
	@Column(name = "WHFee")
	private Double whfee;
	@Column(name = "ProjectOtherCost")
	private Double projectOtherCost;
	@Column(name = "Region")
	private String region;
	@Column(name = "CarryType")
	private String carryType;
	@Column(name = "Floor")
	private Integer floor;
	@Column(name = "TransFee")
	private Double transFee;
	@Column(name = "AutoCarryFee")
	private Double autoCarryFee;
	@Column(name = "AutoTransFee")
	private Double autoTransFee;
	@Column(name = "TransFeeAdj")
	private Double transFeeAdj;
	@Column(name = "ManySendRemarks")
	private String manySendRemarks;
	
	@Transient
	private String unSelected;//by zjf
	@Transient
	private String custCode;
	@Transient
	private String itemType1;
	@Transient
	private String itemType2;
	@Transient
	private String areaDescr;
	@Transient 
	private String itemDescr;
	@Transient 
	private String address;
	@Transient 
	private String nos;
	@Transient 
	private String sendType;
	@Transient 
	private String ItemRight;
	@Transient 
	private String RegionDescr;
	@Transient 
	private String CarryTypeDescr;
	@Transient
	private String itemAppSendJson;
	@Transient
	private String photoName;
	@Transient
	private String photoPath;
	@Transient
	private Date arriveDateFrom;
	@Transient
	private Date arriveDateTo;
	@Transient
	private Date createDateFrom;
	@Transient
	private Date createDateTo;
	public String getAreaDescr() {
		return areaDescr;
	}
	public void setAreaDescr(String areaDescr) {
		this.areaDescr = areaDescr;
	}
	public String getItemDescr() {
		return itemDescr;
	}
	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getIaNo() {
		return iaNo;
	}
	public void setIaNo(String iaNo) {
		this.iaNo = iaNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
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
	public String getIsCheckOut() {
		return isCheckOut;
	}
	public void setIsCheckOut(String isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	public String getWhCheckOutNo() {
		return whCheckOutNo;
	}
	public void setWhCheckOutNo(String whCheckOutNo) {
		this.whCheckOutNo = whCheckOutNo;
	}
	
	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	public String getSendBatchNo() {
		return sendBatchNo;
	}
	public void setSendBatchNo(String sendBatchNo) {
		this.sendBatchNo = sendBatchNo;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getDriverRemark() {
		return driverRemark;
	}
	public void setDriverRemark(String driverRemark) {
		this.driverRemark = driverRemark;
	}
	public Date getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getArriveAddress() {
		return arriveAddress;
	}
	public void setArriveAddress(String arriveAddress) {
		this.arriveAddress = arriveAddress;
	}
	public String getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getProjectManRemark() {
		return projectManRemark;
	}
	public void setProjectManRemark(String projectManRemark) {
		this.projectManRemark = projectManRemark;
	}
	public Double getSendFee() {
		return sendFee;
	}
	public void setSendFee(Double sendFee) {
		this.sendFee = sendFee;
	}
	public Double getCarryFee() {
		return carryFee;
	}
	public void setCarryFee(Double carryFee) {
		this.carryFee = carryFee;
	}
	public Integer getCheckSeq() {
		return checkSeq;
	}
	public void setCheckSeq(Integer checkSeq) {
		this.checkSeq = checkSeq;
	}
	public String getUnSelected() {
		return unSelected;
	}
	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
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
	public String getConfirmMan() {
		return confirmMan;
	}
	public void setConfirmMan(String confirmMan) {
		this.confirmMan = confirmMan;
	}
	public String getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public String getConfirmReason() {
		return confirmReason;
	}
	public void setConfirmReason(String confirmReason) {
		this.confirmReason = confirmReason;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNos() {
		return nos;
	}
	public void setNos(String nos) {
		this.nos = nos;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getItemRight() {
		return ItemRight;
	}
	public void setItemRight(String itemRight) {
		ItemRight = itemRight;
	}
	public Double getWhfee() {
		return whfee;
	}
	public void setWhfee(Double whfee) {
		this.whfee = whfee;
	}
	public Double getProjectOtherCost() {
		return projectOtherCost;
	}
	public void setProjectOtherCost(Double projectOtherCost) {
		this.projectOtherCost = projectOtherCost;
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
	public Double getAutoTransFee() {
		return autoTransFee;
	}
	public void setAutoTransFee(Double autoTransFee) {
		this.autoTransFee = autoTransFee;
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
	public String getItemAppSendJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(itemAppSendJson);
    	return xml;
	}
	public void setItemAppSendJson(String itemAppSendJson) {
		this.itemAppSendJson = itemAppSendJson;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
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
	public Double getTransFeeAdj() {
		return transFeeAdj;
	}
	public void setTransFeeAdj(Double transFeeAdj) {
		this.transFeeAdj = transFeeAdj;
	}
	public String getManySendRemarks() {
		return manySendRemarks;
	}
	public void setManySendRemarks(String manySendRemarks) {
		this.manySendRemarks = manySendRemarks;
	}
	
}
