package com.house.home.entity.salary;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tSalaryDataAdjust")
public class SalaryDataAdjust extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SalaryItem")
	private String salaryItem;
	@Column(name = "SalaryMon")
	private Integer salaryMon;
	@Column(name = "SalaryScheme")
	private Integer salaryScheme;
	@Column(name = "SalaryEmp")
	private String salaryEmp;
	@Column(name = "AdjustMode")
	private String adjustMode;
	@Column(name = "AdjustValue")
	private Double adjustValue;
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
	public Integer getSalaryMon() {
		return salaryMon;
	}
	public void setSalaryMon(Integer salaryMon) {
		this.salaryMon = salaryMon;
	}
	public Integer getSalaryScheme() {
		return salaryScheme;
	}
	public void setSalaryScheme(Integer salaryScheme) {
		this.salaryScheme = salaryScheme;
	}
	public String getSalaryEmp() {
		return salaryEmp;
	}
	public void setSalaryEmp(String salaryEmp) {
		this.salaryEmp = salaryEmp;
	}
	public String getAdjustMode() {
		return adjustMode;
	}
	public void setAdjustMode(String adjustMode) {
		this.adjustMode = adjustMode;
	}
	public Double getAdjustValue() {
		return adjustValue;
	}
	public void setAdjustValue(Double adjustValue) {
		this.adjustValue = adjustValue;
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
