package com.house.home.entity.design;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tDesignPicPrg")
public class DesignPicPrg extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CustCode")
    private String custCode;
    
    @Column(name = "Status")
    private String status;
    
    @Column(name = "SubmitDate")
    private Date submitDate;
    
    @Column(name = "SubmitCZY")
    private String submitCZY;
    
    @Column(name = "ConfirmDate")
    private Date confirmDate;
    
    @Column(name = "ConfirmCZY")
    private String confirmCZY;
    
    @Column(name = "ConfirmRemark")
    private String confirmRemark;
    
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    @Column(name = "Expired")
    private String expired;
    
    @Column(name = "ActionLog")
    private String actionLog;
    
    @Column(name = "IsFullColorDraw")
    private String isFullColorDraw;
    
    @Column(name = "DrawNoChg")
    private String drawNoChg;
    
    @Column(name = "DrawNoChgDate")
    private Date drawNoChgDate;
    
    @Column(name = "DrawQty")
    private Integer drawQty;

    @Column(name = "Draw3DQty")
    private Integer draw3DQty;

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getSubmitCZY() {
        return submitCZY;
    }

    public void setSubmitCZY(String submitCZY) {
        this.submitCZY = submitCZY;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmCZY() {
        return confirmCZY;
    }

    public void setConfirmCZY(String confirmCZY) {
        this.confirmCZY = confirmCZY;
    }

    public String getConfirmRemark() {
        return confirmRemark;
    }

    public void setConfirmRemark(String confirmRemark) {
        this.confirmRemark = confirmRemark;
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

    public String getIsFullColorDraw() {
        return isFullColorDraw;
    }

    public void setIsFullColorDraw(String isFullColorDraw) {
        this.isFullColorDraw = isFullColorDraw;
    }

    public String getDrawNoChg() {
        return drawNoChg;
    }

    public void setDrawNoChg(String drawNoChg) {
        this.drawNoChg = drawNoChg;
    }

    public Date getDrawNoChgDate() {
        return drawNoChgDate;
    }

    public void setDrawNoChgDate(Date drawNoChgDate) {
        this.drawNoChgDate = drawNoChgDate;
    }

    public Integer getDrawQty() {
        return drawQty;
    }

    public void setDrawQty(Integer drawQty) {
        this.drawQty = drawQty;
    }

    public Integer getDraw3DQty() {
        return draw3DQty;
    }

    public void setDraw3DQty(Integer draw3dQty) {
        draw3DQty = draw3dQty;
    }

}
