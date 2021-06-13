package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.house.framework.commons.orm.BaseEntity;

/**
 * tCarryRuleFloor信息
 */
@Entity
@Table(name = "tCarryRuleItem")
public class CarryRuleItem extends BaseEntity {
	
	private static final long serialVersionUID = 1L;	
    	@Id
	@Column(name = "Pk")
	private String pk;
	@Column(name = "No")
	private String no;
	@Column(name = "ItemType3")
	private String itemType3;
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
	private String itemType1;
	@Transient
	private String itemType2;
	@Transient
	private String itemcode;
	@Transient
	private String itemcodedescr;
	@Transient
	private String No1;
	@Transient
	private String itemType3Descr;
	@Transient
	private String fieldJson;
	
	
	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getItemType3() {
		return itemType3;
	}

	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
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

	public String getNo1() {
		return No1;
	}

	public void setNo1(String no1) {
		No1 = no1;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getItemcode() {
		return itemcode;
	}

	public String getItemcodedescr() {
		return itemcodedescr;
	}

	public void setItemcodedescr(String itemcodedescr) {
		this.itemcodedescr = itemcodedescr;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getItemType3Descr() {
		return itemType3Descr;
	}

	public void setItemType3Descr(String itemType3Descr) {
		this.itemType3Descr = itemType3Descr;
	}

	public String getFieldJson() {
		return fieldJson;
	}

	public void setFieldJson(String fieldJson) {
		this.fieldJson = fieldJson;
	}

	
	

}
