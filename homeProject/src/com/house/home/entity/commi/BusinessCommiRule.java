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
@Table(name = "tBusinessCommiRule")
public class BusinessCommiRule extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Role")
	private String role;
	@Column(name = "PosiType")
	private String posiType;
	@Column(name = "Department")
	private String department;
	@Column(name = "EmpCode")
	private String empCode;
	@Column(name = "Prior")
	private Integer prior;
	@Column(name = "Type")
	private String type;
	@Column(name = "CommiPer")
	private Double commiPer;
	@Column(name = "SubsidyPer")
	private Double subsidyPer;
	@Column(name = "DesignAgainSubsidyPer")
	private Double designAgainSubsidyPer;
	@Column(name = "FloatRulePK")
	private Integer floatRulePK;
	@Column(name = "IsBearAgainCommi")
	private String isBearAgainCommi;
	@Column(name = "RecordCommiPer")
	private Double recordCommiPer;
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
	@Column(name = "RightCommiPer")
	private Double rightCommiPer;
	@Column(name = "RecordRightCommiPer")
	private Double recordRightCommiPer;
	
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
	public String getPosiType() {
		return posiType;
	}
	public void setPosiType(String posiType) {
		this.posiType = posiType;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getCommiPer() {
		return commiPer;
	}
	public void setCommiPer(Double commiPer) {
		this.commiPer = commiPer;
	}
	public Double getSubsidyPer() {
		return subsidyPer;
	}
	public void setSubsidyPer(Double subsidyPer) {
		this.subsidyPer = subsidyPer;
	}
	public Double getDesignAgainSubsidyPer() {
		return designAgainSubsidyPer;
	}
	public void setDesignAgainSubsidyPer(Double designAgainSubsidyPer) {
		this.designAgainSubsidyPer = designAgainSubsidyPer;
	}
	public Integer getFloatRulePK() {
		return floatRulePK;
	}
	public void setFloatRulePK(Integer floatRulePK) {
		this.floatRulePK = floatRulePK;
	}
	public String getIsBearAgainCommi() {
		return isBearAgainCommi;
	}
	public void setIsBearAgainCommi(String isBearAgainCommi) {
		this.isBearAgainCommi = isBearAgainCommi;
	}
	public Double getRecordCommiPer() {
		return recordCommiPer;
	}
	public void setRecordCommiPer(Double recordCommiPer) {
		this.recordCommiPer = recordCommiPer;
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
	public Double getRightCommiPer() {
		return rightCommiPer;
	}
	public void setRightCommiPer(Double rightCommiPer) {
		this.rightCommiPer = rightCommiPer;
	}
	public Double getRecordRightCommiPer() {
		return recordRightCommiPer;
	}
	public void setRecordRightCommiPer(Double recordRightCommiPer) {
		this.recordRightCommiPer = recordRightCommiPer;
	}


}
