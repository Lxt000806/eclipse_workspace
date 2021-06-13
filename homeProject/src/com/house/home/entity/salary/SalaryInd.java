package com.house.home.entity.salary;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tSalaryInd")
public class SalaryInd extends BaseEntity{

	private static final long serialVersionUID = 1L;
@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "IndLevel")
	private String indLevel;
	@Column(name = "ObjType")
	private String objType;
	@Column(name = "PeriodType")
	private String periodType;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Status")
	private String status;
	@Column(name = "IndUnit")
	private String indUnit;
	@Column(name = "CalcMode")
	private String calcMode;
	@Column(name = "Formula")
	private String formula;
	@Column(name = "FormulaShow")
	private String formulaShow;
	@Column(name = "FormulaTpl")
	private Integer formulaTpl;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IndType")
	private String indType;
	
	@Transient
	private String quertCondition;
	
	public String getQuertCondition() {
		return quertCondition;
	}
	public void setQuertCondition(String quertCondition) {
		this.quertCondition = quertCondition;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getIndLevel() {
		return indLevel;
	}
	public void setIndLevel(String indLevel) {
		this.indLevel = indLevel;
	}
	public String getObjType() {
		return objType;
	}
	public void setObjType(String objType) {
		this.objType = objType;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIndUnit() {
		return indUnit;
	}
	public void setIndUnit(String indUnit) {
		this.indUnit = indUnit;
	}
	public String getCalcMode() {
		return calcMode;
	}
	public void setCalcMode(String calcMode) {
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
	public String getIndType() {
		return indType;
	}
	public void setIndType(String indType) {
		this.indType = indType;
	}

}
