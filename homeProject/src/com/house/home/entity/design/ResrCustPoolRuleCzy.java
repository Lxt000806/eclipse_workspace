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
@Table(name = "tResrCustPoolRuleCZY")
public class ResrCustPoolRuleCzy extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK")
    private Integer pk;

    @Column(name = "PoolRulePK")
    private Integer poolRulePk;

    @Column(name = "CZYBH")
    private String czybh;
    
    @Column(name = "DispSeq")
    private Integer dispSeq;
    
    @Column(name = "ResrCustNumber")
    private Integer resrCustNumber;

    @Column(name = "Weight")
    private Integer weight;

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

    public Integer getPoolRulePk() {
        return poolRulePk;
    }

    public void setPoolRulePk(Integer poolRulePk) {
        this.poolRulePk = poolRulePk;
    }

    public String getCzybh() {
        return czybh;
    }

    public void setCzybh(String czybh) {
        this.czybh = czybh;
    }

    public Integer getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

    public Integer getResrCustNumber() {
        return resrCustNumber;
    }

    public void setResrCustNumber(Integer resrCustNumber) {
        this.resrCustNumber = resrCustNumber;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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
