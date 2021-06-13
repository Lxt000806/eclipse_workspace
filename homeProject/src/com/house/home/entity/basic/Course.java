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
 * 课程
 * @author lenovo-l729
 *
 */
@Entity(name = "course")
@Table(name = "tCourse")
public class Course {
    
    /** 课程编号 */
    @Column(name = "Code")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String code;
    
    /** 课程类型 */
    @Column(name = "CourseType")
    private String courseType;
    
    /** 课程类型名称 */
    @Transient
    private String courseTypeName;
    
    /** 期数 */
    @Column(name = "Nums")
    private Integer nums;
    
    /** 课程名称 */
    @Column(name = "Descr")
    private String descr;
    
    /** 开始时间 */
    @Column(name = "BeginDate")
    private Date beginDate;
    
    /** 查询课程开始的最早时间 */
    @Transient
    private Date earliestBeginDate;
    
    /** 查询课程的开始的最晚时间 **/
    @Transient
    private Date lastestBeginDate;
    
    /** 结束时间 */
    @Column(name = "EndDate")
    private Date endDate;
    
    /** 描述 */
    @Column(name = "Remark")
    private String remark;
    
    /** 最后更新时间 */
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    /** 最后更新人 */
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    /** 是否过期 */
    @Column(name = "Expired")
    private String expired;
    
    /** 最后更新操作 */
    @Column(name = "ActionLog")
    private String actionLog;
    
    //-- 无参构造 --//
    public Course(){}

    //-- Getter、Setter --//
    public String getCode() {
        return code;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public Date getEarliestBeginDate() {
        return earliestBeginDate;
    }

    public void setEarliestBeginDate(Date earliestBeginDate) {
        this.earliestBeginDate = earliestBeginDate;
    }

    public Date getLastestBeginDate() {
        return lastestBeginDate;
    }

    public void setLastestBeginDate(Date lastestBeginDate) {
        this.lastestBeginDate = lastestBeginDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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




















