package com.house.home.entity.design;

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
@Table(name = "tCustTagGroup")
public class CustTagGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    
    @Column(name = "Descr")
    private String descr;
    
    @Column(name = "DispSeq")
    private Integer dispSeq;
    
    @Column(name = "IsMulti")
    private String isMulti;
    
    @Column(name = "Color")
    private String color;
    
    @Column(name = "CrtCZY")
    private String crtCzy;
    
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    @Column(name = "Expired")
    private String expired;
    
    @Column(name = "ActionLog")
    private String actionLog;
    
    // 标签组中的标签
    @Transient
    private String tagsJson;
    
    // 创建人名称
    @Transient
    private String crtCzyName;

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

    public Integer getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

    public String getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(String isMulit) {
        this.isMulti = isMulit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getCrtCzy() {
        return crtCzy;
    }

    public void setCrtCzy(String crtCzy) {
        this.crtCzy = crtCzy;
    }

    public String getTagsJson() {
        return tagsJson;
    }

    public void setTagsJson(String tagsJson) {
        this.tagsJson = tagsJson;
    }

    public String getCrtCzyName() {
        return crtCzyName;
    }

    public void setCrtCzyName(String crtCzyName) {
        this.crtCzyName = crtCzyName;
    }
    
}
