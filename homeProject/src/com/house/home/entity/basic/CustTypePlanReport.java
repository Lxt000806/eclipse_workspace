package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tCustTypePlanReport")
public class CustTypePlanReport extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PK")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String pk;
	
	@Column(name = "CustType")
	private String custType;
	
	@Column(name = "CustPlanReport")
	private String custPlanReport;
	
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    @Column(name = "Expired")
    private String expired;
    
    @Column(name = "ActionLog")
    private String actionLog;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getCustPlanReport() {
        return custPlanReport;
    }

    public void setCustPlanReport(String custPlanReport) {
        this.custPlanReport = custPlanReport;
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
