package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * tcmpActivity信息
 */
@Entity
@Table(name = "tcmpActivity")
public class cmpActivity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;	
    	@Id
	@Column(name = "No")
	private String No;
	@Column(name = "Descr")
	private String descr;	
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
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
	@Transient
	private String detailJson;
	@Transient
	private String unSelected;
	@Transient
	private String descr1;
	@Transient
	private String no1;
	@Transient
	private String typedescr;
	@Transient
	private String check;
	@Transient
	private String giftCode;//礼品编号
	
	public String getNo() {
		return No;
	}
	public void setNo(String no) {
		No = no;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
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
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getUnSelected() {
		return unSelected;
	}
	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}
	public String getDescr1() {
		return descr1;
	}
	public void setDescr1(String descr1) {
		this.descr1 = descr1;
	}
	public String getNo1() {
		return no1;
	}
	public void setNo1(String no1) {
		this.no1 = no1;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getcmpActivityGiftXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getTypedescr() {
		return typedescr;
	}
	public void setTypedescr(String typedescr) {
		this.typedescr = typedescr;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public String getGiftCode() {
		return giftCode;
	}
	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}
}
