package com.house.home.entity.insales;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

public class BaseItemTempDetail extends BaseEntity {
	@Id
@Column(name = "Pk")
private Integer pk;
@Column(name = "No")
private String no;
@Column(name = "BaseItemCode")
private String baseItemCode;
@Column(name = "Qty")
private Double qty;
@Column(name = "DispSeq")
private Integer dispSeq;
@Column(name = "LastUpdate")
private Date lastUpdate;
@Column(name = "LastUpdatedBy")
private String lastUpdatedBy;
@Column(name = "Expired")
private String expired;
@Column(name = "ActionLog")
private String actionLog;
@Transient
private String custType;
@Transient 
private  String canUseComBaseItem;
@Transient
private String baseItemDescr;//基装项目描述

public Integer getPk() {
	return pk;
}
public void setPk(Integer pk) {
	this.pk = pk;
}
public String getNo() {
	return no;
}
public void setNo(String no) {
	this.no = no;
}
public String getBaseItemCode() {
	return baseItemCode;
}
public void setBaseItemCode(String baseItemCode) {
	this.baseItemCode = baseItemCode;
}
public Double getQty() {
	return qty;
}
public void setQty(Double qty) {
	this.qty = qty;
}
public Integer getDispSeq() {
	return dispSeq;
}
public void setDispSeq(Integer dispSeq) {
	this.dispSeq = dispSeq;
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
public String getCustType() {
	return custType;
}
public void setCustType(String custType) {
	this.custType = custType;
}
public String getCanUseComBaseItem() {
	return canUseComBaseItem;
}
public void setCanUseComBaseItem(String canUseComBaseItem) {
	this.canUseComBaseItem = canUseComBaseItem;
}
public String getBaseItemDescr() {
	return baseItemDescr;
}
public void setBaseItemDescr(String baseItemDescr) {
	this.baseItemDescr = baseItemDescr;
}

}
