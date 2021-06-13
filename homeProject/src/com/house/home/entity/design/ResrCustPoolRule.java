package com.house.home.entity.design;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tResrCustPoolRule")
public class ResrCustPoolRule extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK")
    private Integer pk;

    @Column(name = "ResrCustPoolNo")
    private String resrCustPoolNo;
    
    @Column(name = "Descr")
    private String descr;
    
    @Column(name = "RuleClass")
    private String ruleClass;
    
    @Column(name = "Scope")
    private String scope;

    @Column(name = "Type")
    private String type;
    
    @Column(name = "DispatchCZYScope")
    private String dispatchCZYScope;

    @Column(name = "GroupSign")
    private String groupSign;

    @Column(name = "ToResrCustPoolNo")
    private String toResrCustPoolNo;
    
    @Column(name = "Sql")
    private String sql;
    
    @Column(name = "DispSeq")
    private Integer dispSeq;
    
    @Column(name = "LastUpdate")
    private Date lastUpdate;

    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;

    @Column(name = "Expired")
    private String expired;

    @Column(name = "ActionLog")
    private String actionLog;
    
    @Transient
    private String savedCzys;
    
    @Transient
    private List<Map<String, String>> dispatchMembers;
    
    @Transient
    private String deletedMemberPks;

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getResrCustPoolNo() {
        return resrCustPoolNo;
    }

    public void setResrCustPoolNo(String resrCustPoolNo) {
        this.resrCustPoolNo = resrCustPoolNo;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getRuleClass() {
        return ruleClass;
    }

    public void setRuleClass(String ruleClass) {
        this.ruleClass = ruleClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDispatchCZYScope() {
        return dispatchCZYScope;
    }

    public void setDispatchCZYScope(String dispatchCZYScope) {
        this.dispatchCZYScope = dispatchCZYScope;
    }

    public String getGroupSign() {
        return groupSign;
    }

    public void setGroupSign(String groupSign) {
        this.groupSign = groupSign;
    }

    public String getToResrCustPoolNo() {
        return toResrCustPoolNo;
    }

    public void setToResrCustPoolNo(String toResrCustPoolNo) {
        this.toResrCustPoolNo = toResrCustPoolNo;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
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

    public Integer getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

    public String getSavedCzys() {
        return savedCzys;
    }

    public void setSavedCzys(String savedCzys) {
        this.savedCzys = savedCzys;
    }

    public List<Map<String, String>> getDispatchMembers() {
        return dispatchMembers;
    }

    public void setDispatchMembers(List<Map<String, String>> dispatchMembers) {
        this.dispatchMembers = dispatchMembers;
    }

    public String getDeletedMemberPks() {
        return deletedMemberPks;
    }

    public void setDeletedMemberPks(String deletedMemberPks) {
        this.deletedMemberPks = deletedMemberPks;
    }

}
