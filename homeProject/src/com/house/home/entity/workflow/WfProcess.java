package com.house.home.entity.workflow;

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
 * tWfProcess信息
 */
@Entity
@Table(name = "tWfProcess")
public class WfProcess extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "No")
    private String no;
    @Column(name = "Descr")
    private String descr;
    @Column(name = "ProcKey")
    private String procKey;
    @Column(name = "Version")
    private int version;
    @Column(name = "TableCode")
    private String tableCode;
    @Column(name = "GroupCode")
    private String groupCode;
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    @Column(name = "Expired")
    private String expired;
    @Column(name = "ActionLog")
    private String actionLog;
    @Column(name="Remarks")
    private String remarks;
    
    
    
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getProcKey() {
		return procKey;
	}
	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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
