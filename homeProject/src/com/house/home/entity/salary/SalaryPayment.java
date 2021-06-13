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
@Table(name = "tSalaryPayment")
public class SalaryPayment extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "SalaryEmp")
	private String salaryEmp;
	@Column(name = "SalaryMon")
	private Integer salaryMon;
	@Column(name = "SalaryScheme")
	private Integer salaryScheme;
	@Column(name = "PaymentDef")
	private Integer paymentDef;
	@Column(name = "ActualAmount")
	private Double actualAmount;
	@Column(name = "AmountPaid")
	private Double amountPaid;
	@Column(name = "CardID")
	private String cardID;
	@Column(name = "ActName")
	private String actName;
	@Column(name = "BankType")
	private String bankType;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "CreateTime")
	private Date createTime;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String signCmpCode;
	@Transient
	private String department1;
	@Transient
	private String salaryStatus;
	@Transient
	private String deptType;
	@Transient
	private String belongType;
	
	public String getBelongType() {
		return belongType;
	}
	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}
	public String getDeptType() {
		return deptType;
	}
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	public String getSalaryStatus() {
		return salaryStatus;
	}
	public void setSalaryStatus(String salaryStatus) {
		this.salaryStatus = salaryStatus;
	}
	public String getDepartment1() {
		return department1;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	public String getSignCmpCode() {
		return signCmpCode;
	}
	public void setSignCmpCode(String signCmpCode) {
		this.signCmpCode = signCmpCode;
	}
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
	public Integer getPaymentDef() {
		return paymentDef;
	}
	public void setPaymentDef(Integer paymentDef) {
		this.paymentDef = paymentDef;
	}
	public Double getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}
	public Double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
