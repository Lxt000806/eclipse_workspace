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
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * SalaryEmpAttendance信息
 */
@Entity
@Table(name = "tSalaryEmpAttendance")
public class SalaryEmpAttendance extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SalaryEmp")
	private String salaryEmp;
	@Column(name = "SalaryMon")
	private Integer salaryMon;
	@Column(name = "LateTimes")
	private Integer lateTimes;
	@Column(name = "SeriousLateTimes")
	private Integer seriousLateTimes;
	@Column(name = "AbsentTimes")
	private Integer absentTimes;
	@Column(name = "LeaveEarlyTimes")
	private Integer leaveEarlyTimes;
	@Column(name = "LeaveDays")
	private Double leaveDays;
	@Column(name = "AbsentDays")
	private Double absentDays;
	@Column(name = "ImportDate")
	private Date importDate;
	@Column(name = "ImportCZY")
	private String importCzy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "LateOverHourTimes")
	private Integer lateOverHourTimes;
	
	@Transient
	private String queryCondition;
	@Transient
	private String conSignCmp;
	@Transient
	private String department1;
	@Transient
	private String detailJson;
	@Transient
	private String posiClass;
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setSalaryEmp(String salaryEmp) {
		this.salaryEmp = salaryEmp;
	}
	
	public String getSalaryEmp() {
		return this.salaryEmp;
	}
	public void setSalaryMon(Integer salaryMon) {
		this.salaryMon = salaryMon;
	}
	
	public Integer getSalaryMon() {
		return this.salaryMon;
	}
	public void setLateTimes(Integer lateTimes) {
		this.lateTimes = lateTimes;
	}
	
	public Integer getLateTimes() {
		return this.lateTimes;
	}
	public void setSeriousLateTimes(Integer seriousLateTimes) {
		this.seriousLateTimes = seriousLateTimes;
	}
	
	public Integer getSeriousLateTimes() {
		return this.seriousLateTimes;
	}
	public void setAbsentTimes(Integer absentTimes) {
		this.absentTimes = absentTimes;
	}
	
	public Integer getAbsentTimes() {
		return this.absentTimes;
	}
	public void setLeaveEarlyTimes(Integer leaveEarlyTimes) {
		this.leaveEarlyTimes = leaveEarlyTimes;
	}
	
	public Integer getLeaveEarlyTimes() {
		return this.leaveEarlyTimes;
	}
	public void setLeaveDays(Double leaveDays) {
		this.leaveDays = leaveDays;
	}
	
	public Double getLeaveDays() {
		return this.leaveDays;
	}
	public void setAbsentDays(Double absentDays) {
		this.absentDays = absentDays;
	}
	
	public Double getAbsentDays() {
		return this.absentDays;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	
	public Date getImportDate() {
		return this.importDate;
	}
	public void setImportCzy(String importCzy) {
		this.importCzy = importCzy;
	}
	
	public String getImportCzy() {
		return this.importCzy;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	public String getConSignCmp() {
		return conSignCmp;
	}

	public void setConSignCmp(String conSignCmp) {
		this.conSignCmp = conSignCmp;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getPosiClass() {
		return posiClass;
	}

	public void setPosiClass(String posiClass) {
		this.posiClass = posiClass;
	}

	public Integer getLateOverHourTimes() {
		return lateOverHourTimes;
	}

	public void setLateOverHourTimes(Integer lateOverHourTimes) {
		this.lateOverHourTimes = lateOverHourTimes;
	}

}
