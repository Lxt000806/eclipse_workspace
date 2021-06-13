package com.house.home.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import com.house.framework.commons.excel.ExcelAnnotation;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * 材料毛利率
 */
@Entity(name = "endProfPer")
@Table(name = "tEndProfPer")
public class EndProfPer {
    
    /** 客户编号 */
    @Id
    @Column(name = "CustCode")
    @NotBlank
    @ExcelAnnotation(exportName="客户编号", order=1)
    private String custCode;

    /** 主材毛利率 */
    @Column(name = "MainProPer")
    @NotNull
    @ExcelAnnotation(exportName="主材毛利率", order=2)
    private Double mainProPer;
    
    /** 服务型产品毛利率 */
    @Column(name = "ServProPer")
    @NotNull
    @ExcelAnnotation(exportName="服务性产品毛利率", order=3)
    private Double servProper;
    
    /** 集成橱柜毛利率 */
    @Column(name = "IntProPer")
    @NotNull
    @ExcelAnnotation(exportName="集成橱柜毛利率", order=4)
    private Double intProPer;
    
    /** 软装毛利率 */
    @Column(name = "SoftProPer")
    @NotNull
    @ExcelAnnotation(exportName="软装毛利率", order=5)
    private Double softProPer;
    
    /** 最后修改时间 */
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    /** 最后修改人 */
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    /** 是否过期 */
    @Column(name = "Expired")
    private String expired;
    
    /** 操作日志 */
    @Column(name = "ActionLog")
    private String actionLog;
    
    /** 窗帘毛利率 */
    @Column(name = "CurtainProPer")
    @NotNull
    @ExcelAnnotation(exportName="窗帘毛利率", order=6)
    private Double curtainProPer;
    
    /** 家具毛利率 */
    @Column(name = "FurnitureProPer")
    @NotNull
    @ExcelAnnotation(exportName="家具毛利率", order=7)
    private Double furnitureProPer;
  
    /** 客户姓名 */
    @Transient
    private String custDescr;
    
    /** 客户楼盘 */
    @Transient
    private String custAddress;
    
    @Transient
    private String detailJson;
    
    @Transient
    @ExcelAnnotation(exportName="错误信息", order=13)
    private String error;
    
    @Transient
    private String isinvalid;
    
    @Transient
    private String isinvaliddescr;
    
    public String getIsinvalid() {
        return isinvalid;
    }

    public void setIsinvalid(String isinvalid) {
        this.isinvalid = isinvalid;
    }

    public String getIsinvaliddescr() {
        return isinvaliddescr;
    }

    public void setIsinvaliddescr(String isinvaliddescr) {
        this.isinvaliddescr = isinvaliddescr;
    }

    public String getDetailJson() {
        return detailJson;
    }
    
    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public Double getMainProPer() {
        return mainProPer;
    }

    public void setMainProPer(Double mainProPer) {
        this.mainProPer = mainProPer;
    }

    public Double getServProper() {
        return servProper;
    }

    public void setServProper(Double servProper) {
        this.servProper = servProper;
    }

    public Double getIntProPer() {
        return intProPer;
    }

    public void setIntProPer(Double intProPer) {
        this.intProPer = intProPer;
    }

    public Double getSoftProPer() {
        return softProPer;
    }

    public void setSoftProPer(Double softProPer) {
        this.softProPer = softProPer;
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

    public Double getCurtainProPer() {
        return curtainProPer;
    }

    public void setCurtainProPer(Double curtainProPer) {
        this.curtainProPer = curtainProPer;
    }

    public Double getFurnitureProPer() {
        return furnitureProPer;
    }

    public void setFurnitureProPer(Double furnitureProPer) {
        this.furnitureProPer = furnitureProPer;
    }

    public String getCustDescr() {
        return custDescr;
    }

    public void setCustDescr(String custDescr) {
        this.custDescr = custDescr;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public void setDetailJson(String detailJson) {
        this.detailJson = detailJson;
    }
    public String getDetailXml(){
        String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
        return xml;
    }
}
