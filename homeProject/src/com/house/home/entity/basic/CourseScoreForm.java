package com.house.home.entity.basic;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseScoreForm {

    /** 主键 */
    private Integer pk;
    
    /** 课程编号 */
    private String courseCode;
    
    /** 员工编号 */
    private String number;

    /** 成绩 */
    private Double score;
    
    /** 是否毕业 */
    private String ispass;
    
    /** 是否补考 */
    private String ismakeup;
    
    /** 补考成绩 */
    private Double makeupscore;
    
    /** 描述 */
    private String remark;    
    
    /** 过期标志 */
    private String expired;
    
    /** 操作 */
    private String actionLog;
    
    /** 课程类型 */
    private String coursetype; 
    
    /** 最早开课时间 */
    private Date earliestBeginDate;
    
    /** 最晚开课时间 */
    private Date lastestBeginDate;
    
    /** 课程期数 */
    private Integer nums;
    
    /** 课程名称 */
    private String courseDescr;

    //-- Getter、Setter --//
    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public String getCourseDescr() {
        return courseDescr;
    }

    public void setCourseDescr(String courseDescr) {
        this.courseDescr = courseDescr;
    }

    public String getIspass() {
        return ispass;
    }

    public void setIspass(String ispass) {
        this.ispass = ispass;
    }

    public String getIsmakeup() {
        return ismakeup;
    }

    public void setIsmakeup(String ismakeup) {
        this.ismakeup = ismakeup;
    }

    public Double getMakeupscore() {
        return makeupscore;
    }

    public void setMakeupscore(Double makeupscore) {
        this.makeupscore = makeupscore;
    }

    public String getCoursetype() {
        return coursetype;
    }

    public void setCoursetype(String coursetype) {
        this.coursetype = coursetype;
    }
}
