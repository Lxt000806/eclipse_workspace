package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 装修区域类型
 * @author lenovo-l729
 *
 */
@Table(name = "tFixAreaType")
@Entity
public class FixAreaType {

    /**
     * 主键
     */
    @Id
    @Column(name = "Code")
    private String code;
    
    /**
     * 名称
     */
    @Column(name = "Descr")
    private String descr;
    
    /**
     * 是否默认空间
     */
    @Column(name = "IsDefaultArea")
    private String isDefaultArea;
    
    /**
     * 显示顺序
     */
    @Column(name = "DispSeq")
    private String dispSeq;

    /**
     * 最后更新时间
     */
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    /**
     * 修改人
     */
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    /**
     * 过期标志
     */
    @Column(name = "Expired")
    private String expired;
    
    /**
     * 操作
     */
    @Column(name = "ActionLog")
    private String actionLog;

    //-- Getter、Setter --//
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

    public String getIsDefaultArea() {
        return isDefaultArea;
    }

    public void setIsDefaultArea(String isDefaultArea) {
        this.isDefaultArea = isDefaultArea;
    }

    public String getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(String dispSeq) {
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
