package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tWorkType12Item")
public class WorkType12Item {

    @Id
    @GeneratedValue
    private Integer pk;
    
    @Column(name = "WorkType12")
    private String workType12;
    
    @Column(name = "ItemType1")
    private String itemType1;
    
    @Column(name = "ItemType2")
    private String itemType2;
    
    @Column(name = "ItemType3")
    private String itemType3;
    
    @Column(name = "AppType")
    private String appType;
    
    @Column(name = "LastAppDay")
    private String lastAppDay;
    
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    @Column(name = "Expired")
    private String expired;
    
    @Column(name = "ActionLog")
    private String actionLog;
    
    //toString
    @Override
    public String toString() {
        return "WorkType12Item [pk=" + pk + ", workType12=" + workType12
                + ", itemType1=" + itemType1 + ", itemType2=" + itemType2
                + ", itemType3=" + itemType3 + ", appType=" + appType
                + ", lastAppDay=" + lastAppDay + ", lastUpdate=" + lastUpdate
                + ", lastUpdatedBy=" + lastUpdatedBy + ", expired=" + expired
                + ", actionLog=" + actionLog + "]";
    }
    
    //-- Getter、Setter --//
    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getWorkType12() {
        return workType12;
    }

    public void setWorkType12(String workType12) {
        this.workType12 = workType12;
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

    public String getItemType3() {
        return itemType3;
    }

    public void setItemType3(String itemType3) {
        this.itemType3 = itemType3;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getLastAppDay() {
        return lastAppDay;
    }

    public void setLastAppDay(String lastAppDay) {
        this.lastAppDay = lastAppDay;
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
