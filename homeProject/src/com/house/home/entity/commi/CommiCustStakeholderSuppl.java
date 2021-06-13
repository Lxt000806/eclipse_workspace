package com.house.home.entity.commi;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * 商家返利提成干系人表
 */
@Entity
@Table(name = "tCommiCustStakeholder_Suppl")
public class CommiCustStakeholderSuppl extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	
	@Column(name = "CustCode")
	private String custCode;
	
	@Column(name = "Date")
	private Date date;
	
	@Column(name = "ItemType1")
	private String itemType1;
	
    @Column(name = "ItemDescr")
    private String itemDescr;
	
    @Column(name = "SupplCode")
    private String supplCode;
    
    @Column(name = "Amount")
    private Double amount;
    
    @Column(name = "EmpCode")
    private String empCode;
    
    @Column(name = "CommiAmount")
    private Double commiAmount;

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

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getItemType1() {
        return itemType1;
    }

    public void setItemType1(String itemType1) {
        this.itemType1 = itemType1;
    }

    public String getItemDescr() {
        return itemDescr;
    }

    public void setItemDescr(String itemDescr) {
        this.itemDescr = itemDescr;
    }

    public String getSupplCode() {
        return supplCode;
    }

    public void setSupplCode(String supplCode) {
        this.supplCode = supplCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Double getCommiAmount() {
        return commiAmount;
    }

    public void setCommiAmount(Double commiAmount) {
        this.commiAmount = commiAmount;
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
