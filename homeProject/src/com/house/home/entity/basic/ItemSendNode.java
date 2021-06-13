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
 * ItemSendNode信息
 */
@Entity
@Table(name = "tItemSendNode")
public class ItemSendNode extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name="Type")
	private String type;
	@Column(name = "WorkerClassify")
	private String workerClassify;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	
	@Transient
	private String confItemType;
	@Transient
	private String beginNode;
	@Transient
	private String beginDateType;
	@Transient
	private int beginAddDays;
	@Transient
	private String endNode;
	@Transient
	private String endDateType;
	@Transient
	private int endAddDays;
	@Transient
	private String detailJson;
	@Transient
	private Integer payNum;
	
	

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}
	
	public String getWorkerClassify() {
		return this.workerClassify;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
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
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}


	public String getConfItemType() {
		return confItemType;
	}

	public void setConfItemType(String confItemType) {
		this.confItemType = confItemType;
	}

	public String getBeginNode() {
		return beginNode;
	}

	public void setBeginNode(String beginNode) {
		this.beginNode = beginNode;
	}

	public String getBeginDateType() {
		return beginDateType;
	}

	public void setBeginDateType(String beginDateType) {
		this.beginDateType = beginDateType;
	}

	public int getBeginAddDays() {
		return beginAddDays;
	}

	public void setBeginAddDays(int beginAddDays) {
		this.beginAddDays = beginAddDays;
	}

	public String getEndNode() {
		return endNode;
	}

	public void setEndNode(String endNode) {
		this.endNode = endNode;
	}

	public String getEndDateType() {
		return endDateType;
	}

	public void setEndDateType(String endDateType) {
		this.endDateType = endDateType;
	}

	public int getEndAddDays() {
		return endAddDays;
	}

	public void setEndAddDays(int endAddDays) {
		this.endAddDays = endAddDays;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public Integer getPayNum() {
		return payNum;
	}

	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
	}
}
