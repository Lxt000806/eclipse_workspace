package com.house.home.entity.salary;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * 薪酬月份
 */
@Entity
@Table(name = "tSalaryMon")
public class SalaryMon extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "SalaryMon")
	private Integer salaryMon;

    @Column(name = "Descr")
    private String descr;
    
    @Column(name = "BeginDate")
    private Date beginDate;
    
    @Column(name = "EndDate")
    private Date endDate;
    
    @Column(name = "Status")
    private String status;
    
    @Column(name = "FirstCalcTime")
    private Date firstCalcTime;
    
    @Column(name = "LastCalcTime")
    private Date lastCalcTime;
    
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	
	@Column(name = "Expired")
	private String expired;
	
	@Column(name = "ActionLog")
	private String actionLog;
	
	// 年份查询条件，为了与数据库主键月份作比较
	// 会在前端生成一个6位数字，例：202000
	@Transient
	private Integer year;

    public Integer getSalaryMon() {
        return salaryMon;
    }

    public void setSalaryMon(Integer salaryMon) {
        this.salaryMon = salaryMon;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFirstCalcTime() {
        return firstCalcTime;
    }

    public void setFirstCalcTime(Date firstCalcTime) {
        this.firstCalcTime = firstCalcTime;
    }

    public Date getLastCalcTime() {
        return lastCalcTime;
    }

    public void setLastCalcTime(Date lastCalcTime) {
        this.lastCalcTime = lastCalcTime;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
