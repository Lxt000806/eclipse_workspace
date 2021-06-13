package com.house.home.entity.design;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tCustTag")
public class CustTag extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    
    @Column(name = "Descr")
    private String descr;
    
    @Column(name = "TagGroupPK")
    private Integer tagGroupPk;
    
    @Column(name = "CrtCZY")
    private String crtCzy;
    
    @Column(name = "DispSeq")
    private Double dispSeq;
    
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    @Column(name = "Expired")
    private String expired;
    
    @Column(name = "ActionLog")
    private String actionLog;

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getTagGroupPk() {
        return tagGroupPk;
    }

    public void setTagGroupPk(Integer tagGroupPk) {
        this.tagGroupPk = tagGroupPk;
    }

    public String getCrtCzy() {
        return crtCzy;
    }

    public void setCrtCzy(String crtCzy) {
        this.crtCzy = crtCzy;
    }

    public Double getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Double dispSeq) {
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

    
}
