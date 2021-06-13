package com.house.home.entity.insales;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

@Entity
@Table(name = "tBaseItemTemp")
public class BaseItemTemp extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
@Column(name = "No")
private String no;
@Column(name = "Descr")
private String descr;
@Column(name = "Remark")
private String remark;
@Column(name = "LastUpdate")
private Date lastUpdate;
@Column(name = "LastUpdatedBy")
private String lastUpdatedBy;
@Column(name = "Expired")
private String expired;
@Column(name = "ActionLog")
private String actionLog;
@Column(name = "Type")
private String type;

@Transient
private String detailJson; // 批量json转xml

public String getNo() {
	return no;
}
public void setNo(String no) {
	this.no = no;
}
public String getDescr() {
	return descr;
}
public void setDescr(String descr) {
	this.descr = descr;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
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
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getDetailJson() {
	String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
	return xml;
}
public void setDetailJson(String detailJson) {
	this.detailJson = detailJson;
}


}
