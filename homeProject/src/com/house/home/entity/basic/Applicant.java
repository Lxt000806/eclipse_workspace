package com.house.home.entity.basic;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/** 应聘人员表 */
@Entity
@Table(name = "tApplicant")
public class Applicant {

    /** 主键 */
    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    
    /** 中文名 */
    @Column(name = "NameChi")
    private String nameChi;
    
    /** 性别 */
    @Column(name = "Gender")
    private String gender;
    
    /** 电话 */
    @Column(name = "Phone")
    private String phone;
    
    /** 应聘部门 */
    @Column(name = "DeptDescr")
    private String deptDescr;
    
    /** 应聘职位 */
    @Column(name = "PositionDescr")
    private String positionDescr;
    
    /** 面试时间 */
    @Column(name = "AppDate")
    private Date appDate;
    
    /** 最早应聘时间 */
    @Transient
    private Date dateTo;
    
    /** 最晚应聘时间 */
    @Transient
    private Date dateFrom;
    
    /** 预入时间 */
    @Column(name = "ComeInDate")
    private Date comeInDate;
    
    /** 最早预入时间 */
    @Transient
    private Date earliestComeInDate;
    
    /** 最晚预入时间 */
    @Transient
    private Date lastestComeInDate;
    
    /** 状态 */
    @Column(name = "Status")
    private String status;
    
    /** 籍贯 */
    @Column(name = "BirtPlace")
    private String birtPlace;
    
    /** 身份证号 */
    @Column(name = "IDNum")
    private String idNum;
    
    /** 标志：是否检查身份证号存在 */
    @Transient
    private boolean checkIdNumExist;
    
    /** 生日 */
    @Column(name = "birth")
    private Date birth;
    
    /** 年龄 */
    @Transient
    private Integer age;
    
    /** 家庭住址 */
    @Column(name = "Address")
    private String address;
    
    /** 文化程度 */
    @Column(name = "Edu")
    private String edu;
    
    /** 毕业院校 */
    @Column(name = "School")
    private String school;
    
    /** 专业 */
    @Column(name = "SchDept")
    private String schDept;
    
    /** 途径 */
    @Column(name = "Source")
    private String source;
    
    /** 备注 */
    @Column(name = "Remarks")
    private String remarks;
    
    /** 一级部门 */
    @Column(name = "Dept1Descr")
    private String dept1Descr;
    
    /** 过期标志 */
    @Column(name = "Expired")
    private String expired;
    
    /** 显示他人录入 */
    @Transient
    private String othersEntering;
    
    /** 最后更新时间 */
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    
    /** 修改人 */
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    
    /** 操作日志 */
    @Column(name = "ActionLog")
    private String actionLog;

    @Transient
    private String statusDescr;
    @Transient
    private String genderDescr;
    @Transient
    private String edudescr;
    @Transient
    private Date endAppDate;
    @Transient 
    private Date endComeInDate;
    
    //-- Getter、Setter --//
    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeptDescr() {
        return deptDescr;
    }

    public void setDeptDescr(String deptDescr) {
        this.deptDescr = deptDescr;
    }

    public String getPositionDescr() {
        return positionDescr;
    }

    public void setPositionDescr(String positionDescr) {
        this.positionDescr = positionDescr;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Date getComeInDate() {
        return comeInDate;
    }

    public void setComeInDate(Date comeInDate) {
        this.comeInDate = comeInDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirtPlace() {
        return birtPlace;
    }

    public void setBirtPlace(String birtPlace) {
        this.birtPlace = birtPlace;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchDept() {
        return schDept;
    }

    public void setSchDept(String schDept) {
        this.schDept = schDept;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDept1Descr() {
        return dept1Descr;
    }

    public void setDept1Descr(String dept1Descr) {
        this.dept1Descr = dept1Descr;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getEarliestComeInDate() {
        return earliestComeInDate;
    }

    public void setEarliestComeInDate(Date earliestComeInDate) {
        this.earliestComeInDate = earliestComeInDate;
    }

    public Date getLastestComeInDate() {
        return lastestComeInDate;
    }

    public void setLastestComeInDate(Date lastestComeInDate) {
        this.lastestComeInDate = lastestComeInDate;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
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

    public String getOthersEntering() {
        return othersEntering;
    }

    public void setOthersEntering(String othersEntering) {
        this.othersEntering = othersEntering;
    }

    public boolean isCheckIdNumExist() {
        return checkIdNumExist;
    }

    public void setCheckIdNumExist(boolean checkIdNumExist) {
        this.checkIdNumExist = checkIdNumExist;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getStatusDescr() {
        return statusDescr;
    }

    public void setStatusDescr(String statusDescr) {
        this.statusDescr = statusDescr;
    }

    public String getGenderDescr() {
        return genderDescr;
    }

    public void setGenderDescr(String genderDescr) {
        this.genderDescr = genderDescr;
    }

    public String getEdudescr() {
        return edudescr;
    }

    public void setEdudescr(String edudescr) {
        this.edudescr = edudescr;
    }

    public Date getEndAppDate() {
        return endAppDate;
    }

    public void setEndAppDate(Date endAppDate) {
        this.endAppDate = endAppDate;
    }

    public Date getEndComeInDate() {
        return endComeInDate;
    }

    public void setEndComeInDate(Date endComeInDate) {
        this.endComeInDate = endComeInDate;
    }

    /**
     * 由出生日期获得年龄  精确到天
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int calcAge(Date birthDay) throws Exception {  
        Calendar cal = Calendar.getInstance();  
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("出生日期不能晚于当前日期");  
        }  
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return age;  
    }
}
