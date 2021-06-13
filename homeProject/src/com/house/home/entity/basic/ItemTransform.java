package com.house.home.entity.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.house.framework.commons.orm.BaseEntity;


@Entity
@Table(name = "tItemTransform")
public class ItemTransform extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "No")
	private String no;

    @Column(name = "ItemCode")
    private String itemCode;

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
	private List<Map<String, Object>> details;
	@Transient
	private String itemDescr;
	@Transient
	private String itemType1;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public List<Map<String, Object>> getDetails() {
        return details;
    }

    public void setDetails(List<Map<String, Object>> details) {
        this.details = details;
    }

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
    
}
