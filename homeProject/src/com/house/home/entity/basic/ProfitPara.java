package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * 毛利参数
 * @author lenovo-l729
 *
 */
@Table(name = "tProfitPara")
@Entity
public class ProfitPara {

    /**
     * 主键
     */
    @Id
    @Column(name = "PK")
    private Integer pk;
    
    /**
     * 设计费提成比例
     */
    @Column(name = "DesignCalPer")
    @NotNull(message="设计费提成比例不能为空")
    private Double designCalPer;
    
    /**
     * 预提费用比例
     */
    @Column(name = "CostPer")
    @NotNull(message="预提费用比例不能为空")
    private Double costPer;
    
    /**
     * 基础提成比例
     */
    @Column(name = "BaseCalPer")
    @NotNull(message="基础提成比例不能为空")
    private Double baseCalPer;
    
    /**
     * 主材提成比例
     */
    @Column(name = "MainCalPer")
    @NotNull(message="主材提成比例不能为空")
    private Double mainCalPer;
    
    /**
     * 服务性产品预估毛利率
     */
    @Column(name = "ServProPer")
    @NotNull(message="服务性产品预估毛利率不能为空")
    private Double servProPer;
    
    /**
     * 服务性产品提成比例
     */
    @Column(name = "ServCalPer")
    @NotNull(message="服务性产品提成比例不能为空")
    private Double servCalPer;
    
    /**
     * 基础发包比例控制
     */
    @Column(name = "JobCtrl")
    @NotNull(message="基础发包比例控制不能为空")
    private Double jobCtrl;
    
    /**
     * 发包低比例
     */
    @Column(name = "JobLowPer")
    @NotNull(message="发包低比例不能为空")
    private Double jobLowPer;
    
    /**
     * 发包高比例
     */
    @Column(name = "JobHighPer")
    @NotNull(message="发包高比例不能为空")
    private Double jobHighPer;
    
    /**
     * 集成预估毛利率
     */
    @Column(name = "IntProPer")
    @NotNull(message="集成预估毛利率不能为空")
    private Double intProPer;
    
    /**
     * 集成提成比例
     */
    @Column(name = "IntCalPer")
    @NotNull(message="集成提成比例不能为空")
    private Double intCalPer;
    
    /**
     * 橱柜预估毛利率
     */
    @Column(name = "CupProPer")
    @NotNull(message="橱柜预估毛利率不能为空")
    private Double cupProPer;
    
    /**
     * 橱柜提成比例
     */
    @Column(name = "CupCalPer")
    @NotNull(message="橱柜提成比例不能为空")
    private Double cupCalPer;
    
    /**
     * 软装预估毛利率
     */
    @Column(name = "SoftProPer")
    @NotNull(message="软装预估毛利率不能为空")
    private Double softProPer;
    
    /**
     * 软装提成比例
     */
    @Column(name = "SoftCalPer")
    @NotNull(message="软装提成比例不能为空")
    private Double softCalPer;
    
    /**
     * 最后修改时间
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
    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public Double getDesignCalPer() {
        return designCalPer;
    }

    public void setDesignCalPer(Double designCalPer) {
        this.designCalPer = designCalPer;
    }

    public Double getCostPer() {
        return costPer;
    }

    public void setCostPer(Double costPer) {
        this.costPer = costPer;
    }

    public Double getBaseCalPer() {
        return baseCalPer;
    }

    public void setBaseCalPer(Double baseCalPer) {
        this.baseCalPer = baseCalPer;
    }

    public Double getMainCalPer() {
        return mainCalPer;
    }

    public void setMainCalPer(Double mainCalPer) {
        this.mainCalPer = mainCalPer;
    }

    public Double getServProPer() {
        return servProPer;
    }

    public void setServProPer(Double servProPer) {
        this.servProPer = servProPer;
    }

    public Double getServCalPer() {
        return servCalPer;
    }

    public void setServCalPer(Double servCalPer) {
        this.servCalPer = servCalPer;
    }

    public Double getJobCtrl() {
        return jobCtrl;
    }

    public void setJobCtrl(Double jobCtrl) {
        this.jobCtrl = jobCtrl;
    }

    public Double getJobLowPer() {
        return jobLowPer;
    }

    public void setJobLowPer(Double jobLowPer) {
        this.jobLowPer = jobLowPer;
    }

    public Double getJobHighPer() {
        return jobHighPer;
    }

    public void setJobHighPer(Double jobHighPer) {
        this.jobHighPer = jobHighPer;
    }

    public Double getIntProPer() {
        return intProPer;
    }

    public void setIntProPer(Double intProPer) {
        this.intProPer = intProPer;
    }

    public Double getIntCalPer() {
        return intCalPer;
    }

    public void setIntCalPer(Double intCalPer) {
        this.intCalPer = intCalPer;
    }

    public Double getCupProPer() {
        return cupProPer;
    }

    public void setCupProPer(Double cupProPer) {
        this.cupProPer = cupProPer;
    }

    public Double getCupCalPer() {
        return cupCalPer;
    }

    public void setCupCalPer(Double cupCalPer) {
        this.cupCalPer = cupCalPer;
    }

    public Double getSoftProPer() {
        return softProPer;
    }

    public void setSoftProPer(Double softProPer) {
        this.softProPer = softProPer;
    }

    public Double getSoftCalPer() {
        return softCalPer;
    }

    public void setSoftCalPer(Double softCalPer) {
        this.softCalPer = softCalPer;
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
