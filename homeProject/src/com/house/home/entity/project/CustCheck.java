package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
/**
 * tCustCheck信息
 */
@Entity
@Table(name = "tCustCheck")
public class CustCheck extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "AppDate")
	private Date appDate;
	
	@Column(name = "MainRcvDate")
	private Date mainRcvDate;
	@Column(name = "MainRcvCzy")
	private String mainRcvCzy;
	@Column(name = "MainCplDate")
	private Date mainCplDate;
	@Column(name = "MainStatus")
	private String mainStatus;
	@Column(name = "MainRemark")
	private String mainRemark;
	
	@Column(name = "SoftRcvDate")
	private Date softRcvDate;
	@Column(name = "SoftRcvCzy")
	private String softRcvCzy;
	@Column(name = "SoftCplDate")
	private Date softCplDate;
	@Column(name = "SoftSatus")
	private String softSatus;
	@Column(name = "SoftRemark")
	private String softRemark;
	
	@Column(name = "IntRcvDate")
	private Date intRcvDate;
	@Column(name = "IntRcvCzy")
	private String intRcvCzy;
	@Column(name = "IntCplDate")
	private Date intCplDate;
	@Column(name = "IntStatus")
	private String intStatus;
	@Column(name = "IntRemark")
	private String intRemark;
	
	@Column(name = "FinRcvDate")
	private Date finRcvDate;
	@Column(name = "FinRcvCzy")
	private String finRcvCzy;
	@Column(name = "FinCplDate")
	private Date finCplDate;
	@Column(name = "FinCplCzy")
	private String finCplCzy;
	@Column(name = "FinStatus")
	private String finStatus;
	@Column(name = "FinRemark")
	private String finRemark;

	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IsSalaryConfirm")
	private String isSalaryConfirm;
	@Column(name = "SalaryConfirmCZY")
	private String salaryConfirmCZY;
	@Column(name = "salaryConfirmDate")
	private Date salaryConfirmDate;
	
	@Transient
	private String address;
	@Transient
	private String mainStatusDescr;
	@Transient
	private String mainCZY;
	@Transient
	private String softStatusDescr;
	@Transient
	private String softCZY;
	@Transient
	private String intStatusDescr;
	@Transient
	private String intCZY;
	@Transient
	private String confirmCZY;
	@Transient
	private Date confirmDate;
	@Transient
	private String finStatusDescr;
	@Transient
	private String finCZY;

	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	@Transient
	private String isConfirm;
	
	@Transient
	private String type;
	
	@Transient
	private String custType;
	@Transient
	private String constructType;
	@Transient
	private String salaryConfirmCZYDescr;
	@Transient
	private Date salaryConfirmDateFrom;
	@Transient
	private Date salaryConfirmDateTo;
	@Transient
	private String prjDepartment2;
	
	// 主材管家
	@Transient
	private String housekeeper;
	
	@Transient
	private Date custCheckDateFrom;
	
	@Transient
	private Date custCheckDateTo;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public Date getMainRcvDate() {
		return mainRcvDate;
	}
	public void setMainRcvDate(Date mainRcvDate) {
		this.mainRcvDate = mainRcvDate;
	}
	public String getMainRcvCzy() {
		return mainRcvCzy;
	}
	public void setMainRcvCzy(String mainRcvCzy) {
		this.mainRcvCzy = mainRcvCzy;
	}
	public Date getMainCplDate() {
		return mainCplDate;
	}
	public void setMainCplDate(Date mainCplDate) {
		this.mainCplDate = mainCplDate;
	}
	public String getMainStatus() {
		return mainStatus;
	}
	public void setMainStatus(String mainStatus) {
		this.mainStatus = mainStatus;
	}
	public String getMainRemark() {
		return mainRemark;
	}
	public void setMainRemark(String mainRemark) {
		this.mainRemark = mainRemark;
	}
	public Date getSoftRcvDate() {
		return softRcvDate;
	}
	public void setSoftRcvDate(Date softRcvDate) {
		this.softRcvDate = softRcvDate;
	}
	public String getSoftRcvCzy() {
		return softRcvCzy;
	}
	public void setSoftRcvCzy(String softRcvCzy) {
		this.softRcvCzy = softRcvCzy;
	}
	public Date getSoftCplDate() {
		return softCplDate;
	}
	public void setSoftCplDate(Date softCplDate) {
		this.softCplDate = softCplDate;
	}
	public String getSoftSatus() {
		return softSatus;
	}
	public void setSoftSatus(String softSatus) {
		this.softSatus = softSatus;
	}
	public String getSoftRemark() {
		return softRemark;
	}
	public void setSoftRemark(String softRemark) {
		this.softRemark = softRemark;
	}
	public Date getIntRcvDate() {
		return intRcvDate;
	}
	public void setIntRcvDate(Date intRcvDate) {
		this.intRcvDate = intRcvDate;
	}
	public String getIntRcvCzy() {
		return intRcvCzy;
	}
	public void setIntRcvCzy(String intRcvCzy) {
		this.intRcvCzy = intRcvCzy;
	}
	public Date getIntCplDate() {
		return intCplDate;
	}
	public void setIntCplDate(Date intCplDate) {
		this.intCplDate = intCplDate;
	}
	public String getIntStatus() {
		return intStatus;
	}
	public void setIntStatus(String intStatus) {
		this.intStatus = intStatus;
	}
	public String getIntRemark() {
		return intRemark;
	}
	public void setIntRemark(String intRemark) {
		this.intRemark = intRemark;
	}
	public Date getFinRcvDate() {
		return finRcvDate;
	}
	public void setFinRcvDate(Date finRcvDate) {
		this.finRcvDate = finRcvDate;
	}
	public String getFinRcvCzy() {
		return finRcvCzy;
	}
	public void setFinRcvCzy(String finRcvCzy) {
		this.finRcvCzy = finRcvCzy;
	}
	public Date getFinCplDate() {
		return finCplDate;
	}
	public void setFinCplDate(Date finCplDate) {
		this.finCplDate = finCplDate;
	}
	public String getFinCplCzy() {
		return finCplCzy;
	}
	public void setFinCplCzy(String finCplCzy) {
		this.finCplCzy = finCplCzy;
	}
	public String getFinStatus() {
		return finStatus;
	}
	public void setFinStatus(String finStatus) {
		this.finStatus = finStatus;
	}
	public String getFinRemark() {
		return finRemark;
	}
	public void setFinRemark(String finRemark) {
		this.finRemark = finRemark;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMainStatusDescr() {
		return mainStatusDescr;
	}
	public void setMainStatusDescr(String mainStatusDescr) {
		this.mainStatusDescr = mainStatusDescr;
	}
	public String getMainCZY() {
		return mainCZY;
	}
	public void setMainCZY(String mainCZY) {
		this.mainCZY = mainCZY;
	}
	public String getSoftStatusDescr() {
		return softStatusDescr;
	}
	public void setSoftStatusDescr(String softStatusDescr) {
		this.softStatusDescr = softStatusDescr;
	}
	public String getSoftCZY() {
		return softCZY;
	}
	public void setSoftCZY(String softCZY) {
		this.softCZY = softCZY;
	}
	public String getIntStatusDescr() {
		return intStatusDescr;
	}
	public void setIntStatusDescr(String intStatusDescr) {
		this.intStatusDescr = intStatusDescr;
	}
	public String getIntCZY() {
		return intCZY;
	}
	public void setIntCZY(String intCZY) {
		this.intCZY = intCZY;
	}
	public String getConfirmCZY() {
		return confirmCZY;
	}
	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getFinStatusDescr() {
		return finStatusDescr;
	}
	public void setFinStatusDescr(String finStatusDescr) {
		this.finStatusDescr = finStatusDescr;
	}
	public String getFinCZY() {
		return finCZY;
	}
	public void setFinCZY(String finCZY) {
		this.finCZY = finCZY;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getConstructType() {
		return constructType;
	}
	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}
	public String getIsSalaryConfirm() {
		return isSalaryConfirm;
	}
	public void setIsSalaryConfirm(String isSalaryConfirm) {
		this.isSalaryConfirm = isSalaryConfirm;
	}
	public String getSalaryConfirmCZY() {
		return salaryConfirmCZY;
	}
	public void setSalaryConfirmCZY(String salaryConfirmCZY) {
		this.salaryConfirmCZY = salaryConfirmCZY;
	}
	public Date getSalaryConfirmDate() {
		return salaryConfirmDate;
	}
	public void setSalaryConfirmDate(Date salaryConfirmDate) {
		this.salaryConfirmDate = salaryConfirmDate;
	}
	public String getSalaryConfirmCZYDescr() {
		return salaryConfirmCZYDescr;
	}
	public void setSalaryConfirmCZYDescr(String salaryConfirmCZYDescr) {
		this.salaryConfirmCZYDescr = salaryConfirmCZYDescr;
	}
	public Date getSalaryConfirmDateFrom() {
		return salaryConfirmDateFrom;
	}
	public void setSalaryConfirmDateFrom(Date salaryConfirmDateFrom) {
		this.salaryConfirmDateFrom = salaryConfirmDateFrom;
	}
	public Date getSalaryConfirmDateTo() {
		return salaryConfirmDateTo;
	}
	public void setSalaryConfirmDateTo(Date salaryConfirmDateTo) {
		this.salaryConfirmDateTo = salaryConfirmDateTo;
	}
	public String getPrjDepartment2() {
		return prjDepartment2;
	}
	public void setPrjDepartment2(String prjDepartment2) {
		this.prjDepartment2 = prjDepartment2;
	}
    public String getHousekeeper() {
        return housekeeper;
    }
    public void setHousekeeper(String housekeeper) {
        this.housekeeper = housekeeper;
    }
    public Date getCustCheckDateFrom() {
        return custCheckDateFrom;
    }
    public void setCustCheckDateFrom(Date custCheckDateFrom) {
        this.custCheckDateFrom = custCheckDateFrom;
    }
    public Date getCustCheckDateTo() {
        return custCheckDateTo;
    }
    public void setCustCheckDateTo(Date custCheckDateTo) {
        this.custCheckDateTo = custCheckDateTo;
    }
}
