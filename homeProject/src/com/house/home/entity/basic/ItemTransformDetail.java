package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;


@Entity
@Table(name = "tItemTransformDetail")
public class ItemTransformDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    
	@Column(name = "No")
	private String no;

    @Column(name = "ItemCode")
    private String itemCode;

	@Column(name = "TransformPer")
	private Double transformPer;
	
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	
	@Column(name = "Expired")
	private String expired;
	
	@Column(name = "ActionLog")
	private String actionLog;
	
	
	@Transient
	private String itemDescr;

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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getTransformPer() {
        return transformPer;
    }

    public void setTransformPer(Double transformPer) {
        this.transformPer = transformPer;
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

    public String getItemDescr() {
        return itemDescr;
    }

    public void setItemDescr(String itemDescr) {
        this.itemDescr = itemDescr;
    }

}
