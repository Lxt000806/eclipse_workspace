package com.house.home.entity.design;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.excel.ExcelAnnotation;
import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tResrCustPoolEmp")
public class ResrCustPoolEmp extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PK")
    private Integer pk;

    @Column(name = "ResrCustPoolNo")
    private String resrCustPoolNo;

    @Column(name = "Type")
    private String type;

    @ExcelAnnotation(exportName = "操作员编号", order = 1)
    @Column(name = "CZYBH")
    private String czybh;

    @ExcelAnnotation(exportName = "权重", order = 2)
    @Column(name = "Weight")
    private Integer weight;
    
    @Column(name = "ResrCustNumber")
    private Integer resrCustNumber;

    @Column(name = "DispSeq")
    private Integer dispSeq;

    @ExcelAnnotation(exportName = "组标签", order = 3)
    @Column(name = "GroupSign")
    private String groupSign;

    @Column(name = "LastUpdate")
    private Date lastUpdate;

    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;

    @Column(name = "Expired")
    private String expired;

    @Column(name = "ActionLog")
    private String actionLog;
    
    @Transient
    private Integer onLeave;
    
    @Transient
    private String oldGroupSign;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCzybh() {
        return czybh;
    }

    public void setCzybh(String czybh) {
        this.czybh = czybh;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

    public String getGroupSign() {
        return groupSign;
    }

    public void setGroupSign(String groupSign) {
        this.groupSign = groupSign;
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

    public Integer getOnLeave() {
        return onLeave;
    }

    public void setOnLeave(Integer onLeave) {
        this.onLeave = onLeave;
    }

    public String getOldGroupSign() {
        return oldGroupSign;
    }

    public void setOldGroupSign(String oldGroupSign) {
        this.oldGroupSign = oldGroupSign;
    }

    public Integer getResrCustNumber() {
        return resrCustNumber;
    }

    public void setResrCustNumber(Integer resrCustNumber) {
        this.resrCustNumber = resrCustNumber;
    }

}
