package com.house.home.bean.design;

import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * SalaryEmpDeduction信息bean
 */
public class SalaryEmpAttendanceBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="工号", order=2)
	private String salaryEmp;
	@ExcelAnnotation(exportName="身份证号", order=3)
	private String idnum;
	@ExcelAnnotation(exportName="姓名", order=4)
	private String empName;
	@ExcelAnnotation(exportName = "迟到次数",order=5)
	private Integer lateTimes;
	@ExcelAnnotation(exportName = "严重迟到次数",order=6)
	private Integer seriousLateTimes;
	@ExcelAnnotation(exportName = "缺卡次数",order=7)
	private Integer absentTimes;
	@ExcelAnnotation(exportName = "早退次数",order=8)
	private Integer leaveEarlyTimes;
	@ExcelAnnotation(exportName = "事假天数",order=8)
	private Double leaveDays;
	@ExcelAnnotation(exportName = "旷工天数",order=9)
	private Double absentDays;
	@ExcelAnnotation(exportName = "迟到(1小时以上)",order=10)
	private Integer lateOverHourTimes;
	@ExcelAnnotation(exportName = "上班缺卡次数",order=11)
	private Integer goAbsentTimes;
	@ExcelAnnotation(exportName = "下班缺卡次数",order=12)
	private Integer outAbsentTimes;
	@ExcelAnnotation(exportName = "病假(小时)",order=13)
	private Double sickLeave;
	@ExcelAnnotation(exportName = "年假(天)",order=14)
	private Double yearLeave;
	@ExcelAnnotation(exportName = "事假(小时)",order=20)
	private Double eventLeave;
	@ExcelAnnotation(exportName = "调休(小时)",order=15)
	private Double compensatoryLeave;
	@ExcelAnnotation(exportName = "产假(天)",order=16)
	private Double maternityLeave;
	@ExcelAnnotation(exportName = "陪产假(天)",order=17)
	private Double accompanyMaternityLeave;
	@ExcelAnnotation(exportName = "婚假(天)",order=18)
	private Double marryLeave;
	@ExcelAnnotation(exportName = "丧假(天)",order=19)
	private Double bereavementLeave;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getSalaryEmp() {
		return salaryEmp;
	}
	public void setSalaryEmp(String salaryEmp) {
		this.salaryEmp = salaryEmp;
	}
	public String getIdnum() {
		return idnum;
	}
	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Integer getLateTimes() {
		return lateTimes;
	}
	public void setLateTimes(Integer lateTimes) {
		this.lateTimes = lateTimes;
	}
	public Integer getSeriousLateTimes() {
		return seriousLateTimes;
	}
	public void setSeriousLateTimes(Integer seriousLateTimes) {
		this.seriousLateTimes = seriousLateTimes;
	}
	public Integer getAbsentTimes() {
		return absentTimes;
	}
	public void setAbsentTimes(Integer absentTimes) {
		this.absentTimes = absentTimes;
	}
	public Integer getLeaveEarlyTimes() {
		return leaveEarlyTimes;
	}
	public void setLeaveEarlyTimes(Integer leaveEarlyTimes) {
		this.leaveEarlyTimes = leaveEarlyTimes;
	}
	public Double getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(Double leaveDays) {
		this.leaveDays = leaveDays;
	}
	public Double getAbsentDays() {
		return absentDays;
	}
	public void setAbsentDays(Double absentDays) {
		this.absentDays = absentDays;
	}
	public Integer getLateOverHourTimes() {
		return lateOverHourTimes;
	}
	public void setLateOverHourTimes(Integer lateOverHourTimes) {
		this.lateOverHourTimes = lateOverHourTimes;
	}
	public Integer getGoAbsentTimes() {
		return goAbsentTimes;
	}
	public void setGoAbsentTimes(Integer goAbsentTimes) {
		this.goAbsentTimes = goAbsentTimes;
	}
	public Integer getOutAbsentTimes() {
		return outAbsentTimes;
	}
	public void setOutAbsentTimes(Integer outAbsentTimes) {
		this.outAbsentTimes = outAbsentTimes;
	}
	public Double getSickLeave() {
		return sickLeave;
	}
	public void setSickLeave(Double sickLeave) {
		this.sickLeave = sickLeave;
	}
	public Double getYearLeave() {
		return yearLeave;
	}
	public void setYearLeave(Double yearLeave) {
		this.yearLeave = yearLeave;
	}
	public Double getEventLeave() {
		return eventLeave;
	}
	public void setEventLeave(Double eventLeave) {
		this.eventLeave = eventLeave;
	}
	public Double getCompensatoryLeave() {
		return compensatoryLeave;
	}
	public void setCompensatoryLeave(Double compensatoryLeave) {
		this.compensatoryLeave = compensatoryLeave;
	}
	public Double getMaternityLeave() {
		return maternityLeave;
	}
	public void setMaternityLeave(Double maternityLeave) {
		this.maternityLeave = maternityLeave;
	}
	public Double getAccompanyMaternityLeave() {
		return accompanyMaternityLeave;
	}
	public void setAccompanyMaternityLeave(Double accompanyMaternityLeave) {
		this.accompanyMaternityLeave = accompanyMaternityLeave;
	}
	public Double getMarryLeave() {
		return marryLeave;
	}
	public void setMarryLeave(Double marryLeave) {
		this.marryLeave = marryLeave;
	}
	public Double getBereavementLeave() {
		return bereavementLeave;
	}
	public void setBereavementLeave(Double bereavementLeave) {
		this.bereavementLeave = bereavementLeave;
	}
	
}
