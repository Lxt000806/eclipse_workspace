package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * ExcelTask信息
 */
@Entity
@Table(name = "tExcelTask")
public class ExcelTask extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Url")
	private String url;
	@Column(name = "Type")
	private String type;
	@Column(name = "Status")
	private String status;
	@Column(name = "FileName")
	private String fileName;
	@Column(name = "ImportPlan")
	private String importPlan;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Result")
	private String result;
	@Column(name = "Emps")
	private String emps;
	
	@Transient
	private String resrCustPoolNo;
	
	
	public String getResrCustPoolNo() {
		return resrCustPoolNo;
	}

	public void setResrCustPoolNo(String resrCustPoolNo) {
		this.resrCustPoolNo = resrCustPoolNo;
	}

	public ExcelTask() {
		super();
	}

	public ExcelTask(String url,String type, String status, String fileName,
			String importPlan, Date lastUpdate, String lastUpdatedBy,
			String expired, String actionLog,String emps, String resrCustPoolNo) {
		super();
		this.url = url;
		this.type = type;
		this.status = status;
		this.fileName = fileName;
		this.importPlan = importPlan;
		this.lastUpdate = lastUpdate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.expired = expired;
		this.actionLog = actionLog;
		this.emps = emps;
		this.resrCustPoolNo = resrCustPoolNo;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	public void setImportPlan(String importPlan) {
		this.importPlan = importPlan;
	}
	
	public String getImportPlan() {
		return this.importPlan;
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
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResult() {
		return this.result;
	}

	public String getEmps() {
		return emps;
	}

	public void setEmps(String emps) {
		this.emps = emps;
	}
	
}
