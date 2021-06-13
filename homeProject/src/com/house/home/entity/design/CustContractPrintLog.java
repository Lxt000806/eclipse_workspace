package com.house.home.entity.design;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CustContractPrintLog信息
 */
@Entity
@Table(name = "tCustContractPrintLog")
public class CustContractPrintLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ConPK")
	private Integer conPk;
	@Column(name = "PrintCZY")
	private String printCzy;
	@Column(name = "PrintDate")
	private Date printDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	
	
	public CustContractPrintLog(Integer conPk, String printCzy, Date printDate,
			Date lastUpdate, String lastUpdatedBy, String expired,
			String actionLog) {
		super();
		this.conPk = conPk;
		this.printCzy = printCzy;
		this.printDate = printDate;
		this.lastUpdate = lastUpdate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.expired = expired;
		this.actionLog = actionLog;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setConPk(Integer conPk) {
		this.conPk = conPk;
	}
	
	public Integer getConPk() {
		return this.conPk;
	}
	public void setPrintCzy(String printCzy) {
		this.printCzy = printCzy;
	}
	
	public String getPrintCzy() {
		return this.printCzy;
	}
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}
	
	public Date getPrintDate() {
		return this.printDate;
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

}
