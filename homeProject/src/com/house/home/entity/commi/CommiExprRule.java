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
@Table(name = "tCommiExprRule")
public class CommiExprRule extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Role")
	private String role;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "Department")
	private String department;
	@Column(name = "Prior")
	private Integer prior;
	@Column(name = "PreCommiExprPK")
	private Integer preCommiExprPK;
	@Column(name = "CheckCommiExprPK")
	private Integer checkCommiExprPK;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
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
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Integer getPrior() {
		return prior;
	}
	public void setPrior(Integer prior) {
		this.prior = prior;
	}
	public Integer getPreCommiExprPK() {
		return preCommiExprPK;
	}
	public void setPreCommiExprPK(Integer preCommiExprPK) {
		this.preCommiExprPK = preCommiExprPK;
	}
	public Integer getCheckCommiExprPK() {
		return checkCommiExprPK;
	}
	public void setCheckCommiExprPK(Integer checkCommiExprPK) {
		this.checkCommiExprPK = checkCommiExprPK;
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
