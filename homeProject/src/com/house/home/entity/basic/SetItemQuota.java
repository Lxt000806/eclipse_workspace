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
@Table(name = "tSetItemQuota")
public class SetItemQuota extends BaseEntity{

	private static final long serialVersionUID = 1L;
@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "FromArea")
	private Double fromArea;
	@Column(name = "ToArea")
	private Double toArea;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	
	@Transient
	private String itemCode;
	@Transient
	private String descr;
	@Transient
	private int qty;
	@Transient
	private String judgeSend;
	@Transient
	private String judgeSendDescr;
	@Transient
	private String fixArea;
	@Transient
	private String detailJson;
	
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getJudgeSend() {
		return judgeSend;
	}
	public void setJudgeSend(String judgeSend) {
		this.judgeSend = judgeSend;
	}
	public String getJudgeSendDescr() {
		return judgeSendDescr;
	}
	public void setJudgeSendDescr(String judgeSendDescr) {
		this.judgeSendDescr = judgeSendDescr;
	}
	public String getFixArea() {
		return fixArea;
	}
	public void setFixArea(String fixArea) {
		this.fixArea = fixArea;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	
	

}
