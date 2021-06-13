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
@Entity
@Table(name = "tSalaryData")
public class SalaryData extends BaseEntity{

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
	@Column(name = "DataValue")
	private Double dataValue;
	@Column(name = "Formula")
	private String formula;
	@Column(name = "CheckInfo")
	private String checkInfo;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String salarySchemeType;
	@Transient
	private String calcAll;
	@Transient
	private String empCodes;
	@Transient
	private String detailJson;
	@Transient
	private String dept1Code;
	@Transient
	private String empStatus;
	@Transient
	private Date joinDate;
	@Transient
	private String positionClass;
	@Transient
	private String calcDescr;
	@Transient
	private Integer paymentDef;
	@Transient
	private String empName;
	@Transient
	private String queryCondition;
	@Transient
	private String status;
	@Transient
	private Date firstCalcTime;
	@Transient
	private Integer compareMon;
	@Transient
	private String describe;
	@Transient
	private String salaryStatus;
	@Transient
	private String calcRptType;
	@Transient
	private Integer salaryMonTo;
	@Transient
	private String department1;
	@Transient
	private String department2;
	@Transient
	private String company;
	@Transient
	private String isRptShow;
	@Transient
	private String belongType;
	
	public String getBelongType() {
		return belongType;
	}
	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}
	public String getIsRptShow() {
		return isRptShow;
	}
	public void setIsRptShow(String isRptShow) {
		this.isRptShow = isRptShow;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDepartment1() {
		return department1;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public Integer getSalaryMonTo() {
		return salaryMonTo;
	}
	public void setSalaryMonTo(Integer salaryMonTo) {
		this.salaryMonTo = salaryMonTo;
	}
	public String getCalcRptType() {
		return calcRptType;
	}
	public void setCalcRptType(String calcRptType) {
		this.calcRptType = calcRptType;
	}
	public String getSalaryStatus() {
		return salaryStatus;
	}
	public void setSalaryStatus(String salaryStatus) {
		this.salaryStatus = salaryStatus;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Integer getCompareMon() {
		return compareMon;
	}
	public void setCompareMon(Integer compareMon) {
		this.compareMon = compareMon;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Integer getPaymentDef() {
		return paymentDef;
	}
	public void setPaymentDef(Integer paymentDef) {
		this.paymentDef = paymentDef;
	}
	public String getCalcDescr() {
		return calcDescr;
	}
	public void setCalcDescr(String calcDescr) {
		this.calcDescr = calcDescr;
	}
	public String getDept1Code() {
		return dept1Code;
	}
	public void setDept1Code(String dept1Code) {
		this.dept1Code = dept1Code;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public String getPositionClass() {
		return positionClass;
	}
	public void setPositionClass(String positionClass) {
		this.positionClass = positionClass;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getEmpCodes() {
		return empCodes;
	}
	public void setEmpCodes(String empCodes) {
		this.empCodes = empCodes;
	}
	public String getCalcAll() {
		return calcAll;
	}
	public void setCalcAll(String calcAll) {
		this.calcAll = calcAll;
	}
	public String getSalarySchemeType() {
		return salarySchemeType;
	}
	public void setSalarySchemeType(String salarySchemeType) {
		this.salarySchemeType = salarySchemeType;
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
	public Double getDataValue() {
		return dataValue;
	}
	public void setDataValue(Double dataValue) {
		this.dataValue = dataValue;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getCheckInfo() {
		return checkInfo;
	}
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
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
	public String getSalaryEmpXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public Date getFirstCalcTime() {
		return firstCalcTime;
	}
	public void setFirstCalcTime(Date firstCalcTime) {
		this.firstCalcTime = firstCalcTime;
	}

}
