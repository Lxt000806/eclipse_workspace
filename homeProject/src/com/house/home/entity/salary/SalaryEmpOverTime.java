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
 * SalaryEmpOverTime信息
 */
@Entity
@Table(name = "tSalaryEmpOverTime")
public class SalaryEmpOverTime extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SalaryEmp")
	private String salaryEmp;
	@Column(name = "SalaryMon")
	private Integer salaryMon;
	@Column(name = "Times")
	private Integer times;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "RegisterDate")
	private Date registerDate;
	@Column(name = "RegisterCZY")
	private String registerCzy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
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
	public void setTimes(Integer times) {
		this.times = times;
	}
	
	public Integer getTimes() {
		return this.times;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	public Date getRegisterDate() {
		return this.registerDate;
	}
	public void setRegisterCzy(String registerCzy) {
		this.registerCzy = registerCzy;
	}
	
	public String getRegisterCzy() {
		return this.registerCzy;
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
}
