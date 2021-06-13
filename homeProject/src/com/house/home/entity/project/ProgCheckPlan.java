package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

@Entity
@Table(name="tProgCheckPlan")
public class ProgCheckPlan extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="No")
	private String no;
	@Column(name="Type")
	private String type;
	@Column(name="CrtDate")
	private Date crtDate;
	@Column(name="CheckCZY")
	private String checkCZY;
	@Column(name="Remarks")
	private String remarks;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="IsCheckDept")
	private String isCheckDept;
	
	
	@Transient
	private String detailJson;
	@Transient
	private String checkCZYDescr;
	@Transient 
	private String m_czy;
	@Transient 
	private String address;
	@Transient 
	private String appCZY;
	
	
	
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAppCZY() {
		return appCZY;
	}
	public void setAppCZY(String appCZY) {
		this.appCZY = appCZY;
	}
	public String getM_czy() {
		return m_czy;
	}
	public void setM_czy(String m_czy) {
		this.m_czy = m_czy;
	}
	public String getCheckCZYDescr() {
		return checkCZYDescr;
	}
	public void setCheckCZYDescr(String checkCZYDescr) {
		this.checkCZYDescr = checkCZYDescr;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getGiftAppDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getCheckCZY() {
		return checkCZY;
	}
	public void setCheckCZY(String checkCZY) {
		this.checkCZY = checkCZY;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getIsCheckDept() {
		return isCheckDept;
	}
	public void setIsCheckDept(String isCheckDept) {
		this.isCheckDept = isCheckDept;
	}
	
	
	
}
