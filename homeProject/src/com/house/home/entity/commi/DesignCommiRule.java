package com.house.home.entity.commi;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tDesignCommiRule")
public class DesignCommiRule extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Role")
	private String role;
	@Column(name = "Department")
	private String department;
	@Column(name = "EmpCode")
	private String empCode;
	@Column(name = "Prior")
	private Integer prior;
	@Column(name = "PreCommiPer")
	private Double preCommiPer;
	@Column(name = "CheckCommiType")
	private String checkCommiType;
	@Column(name = "CheckCommiPer")
	private Double checkCommiPer;
	@Column(name = "FloatRulePK")
	private Integer floatRulePK;
	@Column(name = "SubsidyPer")
	private Double subsidyPer;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Remarks")
	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Integer getPrior() {
		return prior;
	}
	public void setPrior(Integer prior) {
		this.prior = prior;
	}
	public Double getPreCommiPer() {
		return preCommiPer;
	}
	public void setPreCommiPer(Double preCommiPer) {
		this.preCommiPer = preCommiPer;
	}
	public String getCheckCommiType() {
		return checkCommiType;
	}
	public void setCheckCommiType(String checkCommiType) {
		this.checkCommiType = checkCommiType;
	}
	public Double getCheckCommiPer() {
		return checkCommiPer;
	}
	public void setCheckCommiPer(Double checkCommiPer) {
		this.checkCommiPer = checkCommiPer;
	}
	public Integer getFloatRulePK() {
		return floatRulePK;
	}
	public void setFloatRulePK(Integer floatRulePK) {
		this.floatRulePK = floatRulePK;
	}
	public Double getSubsidyPer() {
		return subsidyPer;
	}
	public void setSubsidyPer(Double subsidyPer) {
		this.subsidyPer = subsidyPer;
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
