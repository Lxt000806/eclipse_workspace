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
 * SalaryEmpDeduction信息
 */
@Entity
@Table(name = "tSalaryEmpDeduction")
public class SalaryEmpDeduction extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SalaryEmp")
	private String salaryEmp;
	@Column(name = "SalaryMon")
	private Integer salaryMon;
	@Column(name = "DeductType1")
	private String deductType1;
	@Column(name = "DeductType2")
	private String deductType2;
	@Column(name = "DeductDate")
	private Date deductDate;
	@Column(name = "Amount")
	private Double amount;
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
	@Column(name = "SalarySchemeType")
	private String salarySchemeType;
	
	@Transient
	private String queryCondition;
	@Transient
	private String conSignCmp;
	@Transient
	private String department1;
	@Transient
	private String detailJson;
	
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
	public void setDeductType1(String deductType1) {
		this.deductType1 = deductType1;
	}
	
	public String getDeductType1() {
		return this.deductType1;
	}
	public void setDeductType2(String deductType2) {
		this.deductType2 = deductType2;
	}
	
	public String getDeductType2() {
		return this.deductType2;
	}
	public void setDeductDate(Date deductDate) {
		this.deductDate = deductDate;
	}
	
	public Date getDeductDate() {
		return this.deductDate;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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

	public String getSalarySchemeType() {
		return salarySchemeType;
	}

	public void setSalarySchemeType(String salarySchemeType) {
		this.salarySchemeType = salarySchemeType;
	}

	
	
}
