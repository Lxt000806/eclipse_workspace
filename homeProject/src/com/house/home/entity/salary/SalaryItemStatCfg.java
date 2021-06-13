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

@Entity
@Table(name = "tSalaryItemStatCfg")
public class SalaryItemStatCfg extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SalaryItem")
	private String salaryItem;
	@Column(name = "FilterRemarks")
	private String filterRemarks;
	@Column(name = "FilterFormula")
	private String filterFormula;
	@Column(name = "FilterFormulaShow")
	private String filterFormulaShow;
	@Column(name = "FilterLevel")
	private Integer filterLevel;
	@Column(name = "BeginMon")
	private Integer beginMon;
	@Column(name = "EndMon")
	private Integer endMon;
	@Column(name = "CalcMode")
	private Integer calcMode;
	@Column(name = "Formula")
	private String formula;
	@Column(name = "FormulaShow")
	private String formulaShow;
	@Column(name = "FormulaTpl")
	private Integer formulaTpl;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String salaryItemDescr;
	@Transient
	private String empName;
	
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getSalaryItemDescr() {
		return salaryItemDescr;
	}
	public void setSalaryItemDescr(String salaryItemDescr) {
		this.salaryItemDescr = salaryItemDescr;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getSalaryItem() {
		return salaryItem;
	}
	public void setSalaryItem(String salaryItem) {
		this.salaryItem = salaryItem;
	}
	public String getFilterRemarks() {
		return filterRemarks;
	}
	public void setFilterRemarks(String filterRemarks) {
		this.filterRemarks = filterRemarks;
	}
	public String getFilterFormula() {
		return filterFormula;
	}
	public void setFilterFormula(String filterFormula) {
		this.filterFormula = filterFormula;
	}
	public String getFilterFormulaShow() {
		return filterFormulaShow;
	}
	public void setFilterFormulaShow(String filterFormulaShow) {
		this.filterFormulaShow = filterFormulaShow;
	}
	public Integer getFilterLevel() {
		return filterLevel;
	}
	public void setFilterLevel(Integer filterLevel) {
		this.filterLevel = filterLevel;
	}
	public Integer getBeginMon() {
		return beginMon;
	}
	public void setBeginMon(Integer beginMon) {
		this.beginMon = beginMon;
	}
	public Integer getEndMon() {
		return endMon;
	}
	public void setEndMon(Integer endMon) {
		this.endMon = endMon;
	}
	public Integer getCalcMode() {
		return calcMode;
	}
	public void setCalcMode(Integer calcMode) {
		this.calcMode = calcMode;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getFormulaShow() {
		return formulaShow;
	}
	public void setFormulaShow(String formulaShow) {
		this.formulaShow = formulaShow;
	}
	public Integer getFormulaTpl() {
		return formulaTpl;
	}
	public void setFormulaTpl(Integer formulaTpl) {
		this.formulaTpl = formulaTpl;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
