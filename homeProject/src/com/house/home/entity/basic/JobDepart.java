package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 任务部门
 * @author lenovo-l729
 *
 */
@Table(name = "tJobDepart")
@Entity
public class JobDepart {
    
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK")
    private Integer pk;
    
    /**
     * 任务类型
     */
    @Column(name = "JobType")
    private String jobType;
    
    /**
     * 项目经理部门2
     */
    @Column(name = "ProjectDepartment2")
    private String projectDepartment2;
    
    /**
     * 可选一级部门
     */
    @Transient
    private String department1;
    
    /**
     * 可选二级部门
     */
    @Column(name = "Department2")
    private String department2;
    
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
     * 操作
     */
    @Column(name = "ActionLog")
    private String actionLog;
    
    /**
     * 过期标志
     */
    @Column(name = "Expired")
    private String expired;

    
    //-- Getter、Setter --//
    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getProjectDepartment2() {
        return projectDepartment2;
    }

    public void setProjectDepartment2(String projectDepartment2) {
        this.projectDepartment2 = projectDepartment2;
    }

    public String getDepartment2() {
        return department2;
    }

    public void setDepartment2(String department2) {
        this.department2 = department2;
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

    public String getActionLog() {
        return actionLog;
    }

    public void setActionLog(String actionLog) {
        this.actionLog = actionLog;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getDepartment1() {
        return department1;
    }

    public void setDepartment1(String department1) {
        this.department1 = department1;
    }
}
