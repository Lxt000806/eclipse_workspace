package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * 课程成绩
 * @author lenovo-l729
 *
 */
@Entity
@Table(name = "tCourseScore")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseScore {

    /** 主键 */
    @Column(name = "PK")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    
    /** 课程编号 */
    @Column(name = "CourseCode")
    private String courseCode;
    
    /** 员工编号 */
    @Column(name = "EmpCode")
    @JsonProperty("number")
    @ExcelAnnotation(exportName="员工编号", order=1)
    private String empCode;
    
    /** 员工姓名 */
    @ExcelAnnotation(exportName="姓名", order=2)
    @Transient
    private String nameChi;
    
    /** 员工性别 */
    @ExcelAnnotation(exportName="性别", order=3)
    @Transient
    private String gender;
    
    /** 一级部门 */
    @ExcelAnnotation(exportName="一级部门", order=4)
    @Transient
    private String department1Descr;
    
    /** 二级部门 */
    @ExcelAnnotation(exportName="二级部门", order=5)
    @Transient
    private String department2Descr;
    
    /** 三级部门 */
    @ExcelAnnotation(exportName="三级部门", order=6)
    @Transient
    private String department3Descr;
    
    /** 职位 */
    @ExcelAnnotation(exportName="职位", order=7)
    @Transient
    private String positionName;
    
    /** 加入日期 */
    @ExcelAnnotation(exportName="加入日期", order=8)
    @Transient
    @JsonIgnore
    private Date joinDate;
    
    /** 电话 */
    @ExcelAnnotation(exportName="电话", order=9)
    @Transient
    private String phone;
    
    /** 成绩 */
    @Column(name = "Score")
    @ExcelAnnotation(exportName="成绩", order=10)
    private Double score;
    
    /** 是否通过 */
    @Column(name = "IsPass")
    @ExcelAnnotation(exportName="是否毕业", order=11)
    private String isPass;
    
    /** 是否补考 */
    @Column(name = "IsMakeUp")
    @ExcelAnnotation(exportName="是否补考", order=12)
    private String isMakeUp;
    
    /** 补考成绩 */
    @Column(name = "MakeUpScore")
    @ExcelAnnotation(exportName="补考成绩", order=13)
    private Double makeUpScore;
    
    /** 描述 */
    @Column(name = "Remark")
    @ExcelAnnotation(exportName="备注", order=14)
    private String remark;
    
    /** 最后更新时间 */
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    /** 修改人 */
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    /** 过期标志 */
    @Column(name = "Expired")
    private String expired;
    
    /** 操作 */
    @Column(name = "ActionLog")
    private String actionLog;
    
    /** 错误信息 */
    @Transient
    @ExcelAnnotation(exportName="错误信息", order=15)
    private String error;
    
    /** 课程类型 */
    @Transient
    private String courseType;
    
    /** 最早开课时间 */
    @Transient
    private Date earliestBeginDate;
    
    /** 最晚开课时间 */
    @Transient
    private Date lastestBeginDate;
    
    /** 课程期数 */
    @Transient
    private Integer nums;
    
    /** 课程名称 */
    @Transient
    private String courseDescr;
    
    //toString
    @Override
    public String toString() {
        return "CourseScore [pk=" + pk + ", courseCode=" + courseCode
                + ", empCode=" + empCode + ", nameChi=" + nameChi + ", gender="
                + gender + ", department1Descr=" + department1Descr
                + ", department2Descr=" + department2Descr
                + ", department3Descr=" + department3Descr + ", positionName="
                + positionName + ", joinDate=" + joinDate + ", phone=" + phone
                + ", score=" + score + ", isPass=" + isPass + ", isMakeUp="
                + isMakeUp + ", makeUpScore=" + makeUpScore + ", remark="
                + remark + ", lastUpdate=" + lastUpdate + ", lastUpdatedBy="
                + lastUpdatedBy + ", expired=" + expired + ", actionLog="
                + actionLog + ", error=" + error + ", courseType=" + courseType
                + ", earliestBeginDate=" + earliestBeginDate + ", lastestBeginDate="
                + lastestBeginDate + ", nums=" + nums + ", courseDescr="
                + courseDescr + "]";
    }

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

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getIsMakeUp() {
        return isMakeUp;
    }

    public void setIsMakeUp(String isMakeUp) {
        this.isMakeUp = isMakeUp;
    }

    public Double getMakeUpScore() {
        return makeUpScore;
    }

    public void setMakeUpScore(Double makeUpScore) {
        this.makeUpScore = makeUpScore;
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

    public String getNameChi() {
        return nameChi;
    }

    public void setNameChi(String nameChi) {
        this.nameChi = nameChi;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment1Descr() {
        return department1Descr;
    }

    public void setDepartment1Descr(String department1Descr) {
        this.department1Descr = department1Descr;
    }

    public String getDepartment2Descr() {
        return department2Descr;
    }

    public void setDepartment2Descr(String department2Descr) {
        this.department2Descr = department2Descr;
    }

    public String getDepartment3Descr() {
        return department3Descr;
    }

    public void setDepartment3Descr(String department3Descr) {
        this.department3Descr = department3Descr;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
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
}
