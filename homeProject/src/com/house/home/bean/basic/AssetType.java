package com.house.home.bean.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tAssetType")
public class AssetType extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "Code")
    private String code;
    
    @Column(name = "Descr")
    private String descr;
    
    @Column(name = "RemCode")
    private String remCode;
    
    @Column(name = "DeprType")
    private String deprType;
    
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    @Column(name = "Expired")
    private String expired;
    
    @Column(name = "ActionLog")
    private String actionLog;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDeprType() {
        return deprType;
    }

    public void setDeprType(String deprType) {
        this.deprType = deprType;
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

    public String getRemCode() {
        return remCode;
    }

    public void setRemCode(String remCode) {
        this.remCode = remCode;
    }

}
