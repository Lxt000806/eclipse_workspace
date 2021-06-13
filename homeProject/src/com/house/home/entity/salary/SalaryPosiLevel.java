package com.house.home.entity.salary;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * 薪酬岗位级别
 */
@Entity
@Table(name = "tSalaryPosiLevel")
public class SalaryPosiLevel extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
    
    @Column(name = "PosiClass")
    private Integer posiClass;

    @Column(name = "Descr")
    private String descr;

    @Column(name = "Salary")
    private Double salary;
    
    @Column(name = "BasicSalary")
    private Double basicSalary;
    
    @Column(name = "MinPerfAmount")
    private Double minPerfAmount;
    
    @Column(name = "DispSeq")
    private Integer dispSeq;
    
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	
	@Column(name = "Expired")
	private String expired;
	
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private Integer salaryPosiClass;
	
	@Transient
	private String isFront;

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public Integer getPosiClass() {
        return posiClass;
    }

    public void setPosiClass(Integer posiClass) {
        this.posiClass = posiClass;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Double getMinPerfAmount() {
        return minPerfAmount;
    }

    public void setMinPerfAmount(Double minPerfAmount) {
        this.minPerfAmount = minPerfAmount;
    }

    public Integer getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
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

    public String getIsFront() {
        return isFront;
    }

    public void setIsFront(String isFront) {
        this.isFront = isFront;
    }

    public Integer getSalaryPosiClass() {
        return salaryPosiClass;
    }

    public void setSalaryPosiClass(Integer salaryPosiClass) {
        this.salaryPosiClass = salaryPosiClass;
    }
    
}
